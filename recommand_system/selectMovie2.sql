with movieRating(movieTitle,ratingScore) as
	   (
		 select movieTitle, avg(ratingScore)
		 from user natural join movie natural join ratings
-- 		 where age in(
-- 					  ( (select age from user where userIdmovie = 15)/10) * 10  ,
-- 					  ( (select age from user where userId = 15)  /10 + 1) * 10 -1
-- 		   )
		where floor(age/10) = (
					  select floor(age/10) from user where userId =1
		   )
		 group by movieTitle
	   )
select movieTitle, ratingScore
from movieRating
order by ratingScore desc