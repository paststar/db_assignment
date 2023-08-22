with distinctGenre(distinct_genre) as
	(select distinct(genre)
	from user natural join ratings  natural join movie_genres
	where userId = 15
	), 
    movieRating(movieTitle,ratingScore) as
		(
             select movieTitle, avg(ratingScore)
             from movie natural join ratings natural join movie_genres
            where genre in (select distinct_genre from distinctGenre)
             group by movieTitle
           )
select movieTitle, ratingScore
from movieRating
order by ratingScore desc
limit 10;