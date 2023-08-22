set @mean_u = (select avg(ratingScore) from ratings where userId = 15);
select @mean_u;