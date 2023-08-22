with meanRatings(userId, mean_rating) as
	(
		select userId, avg(ratingScore) as mean_rating
		from ratings
		group by userId
	), PCC as 
   (
	select u2.userId as userId, sum((u1.ratingScore - m1.mean_rating) * (u2.ratingScore - m2.mean_rating)) / (sqrt(sum(power(u1.ratingScore - m1.mean_rating, 2))) * sqrt(sum(power(u2.ratingScore - m2.mean_rating, 2)))) AS pcc
    from ratings u1 join ratings u2 on u1.movieId = u2.movieId join meanRatings m1 on u1.userId = m1.userId join meanRatings m2 on u2.userId = m2.userId
	where u1.userId = 15 and u1.userId != u2.userId
	group by u2.userId
    )
select movieTitle, predicted_rating
from 
(
	select movieid, sum(pcc*(ratingScore-mean_rating))/sum(pcc) as predicted_rating
	from
    (
		select *, row_number() over(partition by movieId ORDER BY pcc  desc) as row_num
		from ratings natural join PCC natural join meanRatings
	) A
	where A.row_num<=40
	group by movieid
) B natural join movie
order by predicted_rating desc
      limit 10;
