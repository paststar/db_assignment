with recentlyRated as 
	(
		select movieId
		from ratings
		where userId = 15
		order by
		timestamp desc
		limit 1
	),
	meanRatings as
	(
		select movieId, avg(ratingScore) as mean_rating
		from ratings
		group by movieId
	),
	PCC as 
   (
		select j.movieId, sum((i.ratingScore - m1.mean_rating) * (j.ratingScore - m2.mean_rating)) / (sqrt(sum(power(i.ratingScore - m1.mean_rating, 2))) * sqrt(sum(power(j.ratingScore - m2.mean_rating, 2)))) AS pcc
		from ratings i join ratings j on i.userId= j.userId join meanRatings m1 on i.movieId = m1.movieId join meanRatings m2 on j.movieId = m2.movieId
		where i.movieId in (select movieId from recentlyRated) and i.movieId != j.movieId
		group by j.movieId
    )
select movieTitle, pcc as similarity
from PCC natural join movie
order by pcc desc
limit 10
