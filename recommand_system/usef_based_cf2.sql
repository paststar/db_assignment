with meanRatings(userId, mean_rating) as
	(
		select userId, avg(ratingScore) as mean_rating
		from ratings
		group by userId
	), COS as 
   (
	select u2.userId as userId, sum(u1.ratingScore*u2.ratingScore) / (sqrt(sum(power(u1.ratingScore , 2))) * sqrt(sum(power(u2.ratingScore, 2)))) as cos
    from ratings u1 join ratings u2 on u1.movieId = u2.movieId
	where u1.userId = 15 and u1.userId != u2.userId
	group by u2.userId
    )

select movieTitle, predicted_rating
from 
(
	select movieid, sum(cos*(ratingScore-mean_rating))/sum(cos) as predicted_rating
	from
    (
		select *, row_number() over(partition by movieId ORDER BY cos  desc) as row_num
		from ratings natural join COS natural join meanRatings
	) A
	where A.row_num<=40
	group by movieid
) B natural join movie
order by predicted_rating desc
      limit 10;
