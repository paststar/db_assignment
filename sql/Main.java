package test;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/project_movie";
        String user = "postgres";
        String password = "cse3207"; //password 입력
        Connection connect = null;
        try
        {
            connect = DriverManager.getConnection(url, user, password);
            if(connect != null) {
                System.out.println("Connection successful!!");
            }
            else {
                throw new SQLException("no connection...");
            }
            Statement stmt=connect.createStatement();
            String sql;
            ResultSet rs;
            
            // ### 1번 ###
            String create_director="create table director"
                    + " (directorID       int,"
                    + " directorName      text,"
                    + " dateOfBirth      text,"
                    + " dateOfDeath       text,"
                    + " primary key (directorID)"
                    + " )";
            String insert_director =""
            		+"insert into director values (1, 'Lee Isaac Chung', '1978.10.19',null);"
            		+"insert into director values (2, 'Tim Burton', '1958.8.25',null);"
            		+"insert into director values (3, 'David Fincher', '1962.8.28',null);"
            		+"insert into director values (4, 'Christopher Nolan', '1970.7.30',null);";
            
            String create_actor="create table actor"
                    + "(actorID      int,"
                    + "actorName      text,"
                    + "dateOfBirth   text,"
                    + "dateOfDeath    text,"
                    + "gender       varchar(6),"
                    + "primary key (actorID)"
                    + ")";
            String insert_actor=""
            		+ "insert into actor values (1, 'Steven Yeun', '1983.12.21',null,'Male');"
            		+ "insert into actor values (2, 'Youn Yuhjung', '1947.6.19',null,'Female');"
            		+ "insert into actor values (3, 'Johnny Depp', '1963.6.9',null,'Male');"
            		+ "insert into actor values (4, 'Winona Ryder', '1971.10.29',null,'Female');"
            		+ "insert into actor values (5, 'Anne Hathaway', '1982.11.12',null,'Female');"
            		+ "insert into actor values (6, 'Christian Bale', '1974.1.30',null,'Male');"
            		+ "insert into actor values (7, 'Heath Ledger', '1979.4.4','2008.1.22','Male');"
            		+ "insert into actor values (8, 'Jesse Eisenberg', '1983.10.5',null,'Male');"
            		+ "insert into actor values (9, 'Andrew Garfield', '1983.8.20',null,'Male');";
            
            String create_movie = "CREATE TABLE movie("
            		+ "movieID int primary key,"
            		+ "movieName varchar(100),"
            		+ "releaseYear char(10),"
            		+ "releaseMonth char(10),"
            		+ "releaseDate char(10),"
            		+ "publisherName varchar(100),"
            		+ "avgRate real)";
            String insert_movie = ""
            		+"insert into movie values (1, 'Minari', '2020','12','11','A24',null);"
            		+"insert into movie values (2, 'Edward Scissorhands', '1991','06','29','20th Century Fox Presents',null);"
            		+"insert into movie values (3, 'Alice In Wonderland', '2010','03','04','Korea Sony Pictures',null);"
            		+"insert into movie values (4, 'The Social Network', '2010','11','18','Korea Sony Pictures',null);"
            		+"insert into movie values (5, 'The Dark Knight', '2008','08','06','Warner Brothers Korea',null);";
            
            String create_award = "create table award"
                    + " (awardID       int,"
                    + " awardName      text,"
                    + " primary key (awardID)"
                    + " )";
            
            String create_customer = "CREATE TABLE customer("
            		+ "customerID int primary key, "
            		+ "customerName text,"
            		+ "dateOfBirth text,"
            		+ "gender text"
            		+ ")";
            
            String insert_customer = "" +
                    "insert into customer values (1, 'Bob', '1997.11.14', 'Male');" +
                    "insert into customer values (2, 'John', '1978.01.23', 'Male');" +
                    "insert into customer values (3, 'Jack', '1980.05.04', 'Male');" +
                    "insert into customer values (4, 'Jill', '1981.04.17', 'Female');" +
                    "insert into customer values (5, 'Bell', '1990.05.14', 'Female');";
           
            String create_genre = "create table genre"
                    + " (genreName    text,"
                    + " primary key (genreName)"
                    + " )";
            
            String insert_genre = "" +
                    "insert into genre values ('Drama');" +
                    "insert into genre values ('Fantasy');" +
                    "insert into genre values ('Romance');" +
                    "insert into genre values ('Adventure');" +
                    "insert into genre values ('Mystery');" +
                    "insert into genre values ('Thriler');" +
                    "insert into genre values ('Action');" +
                    "insert into genre values ('Family');";
            
            String create_movieGenre = "create table movieGenre"
                    + " (movieID    int,"
                    + " genreName    text,"   
                    + " primary key (movieID, genreName),"
                    + " foreign key (movieID) references movie on delete cascade,"
                    + " foreign key (genreName) references genre"
                    + " )";
            
            String insert_movieGenre = "" +
                    "insert into movieGenre values (1,'Drama');" +
                    "insert into movieGenre values (2,'Fantasy');" +
                    "insert into movieGenre values (2,'Romance');" +
                    "insert into movieGenre values (3,'Fantasy');" +
                    "insert into movieGenre values (3,'Adventure');" +
                    "insert into movieGenre values (3,'Family');" +
                    "insert into movieGenre values (4,'Drama');" +
                    "insert into movieGenre values (5,'Action');" +
                    "insert into movieGenre values (5,'Drama');" +
                    "insert into movieGenre values (5,'Mystery');" +
                    "insert into movieGenre values (5,'Thriler');";
            
            String create_movieObtain = "CREATE table movieObtain("
            		+ "movieID int ,"
            		+ "awardID int ,"
            		+ "year text,"
            		+ "primary key (movieID, awardID),"
            		+ "foreign key (movieID) references movie on delete cascade,"
            		+ "foreign key (awardID) references award"
            		+ ")"; 
            
            String create_actorObtain = "CREATE table actorObtain("
            		+ "actorID int ,"
            		+ "awardID int ,"
            		+ "year text,"
            		+ "primary key (actorID, awardID),"
            		+ "foreign key (actorID) references actor,"
            		+ "foreign key (awardID) references award"
            		+ ")"; 
            
            String create_directorObtain = "CREATE table directorObtain("
            		+ "directorID int ,"
            		+ "awardID int ,"
            		+ "year text,"
            		+ "primary key (directorID, awardID),"
            		+ "foreign key (directorID) references director,"
            		+ "foreign key (awardID) references award"
            		+ ")"; 
            
            String create_casting = "CREATE table casting("
            		+ "movieID int ,"
            		+ "actorID int,"
            		+ "role text,"
            		+ "primary key (movieID, actorID),"
            		+ "foreign key (movieID) references movie on delete cascade,"
            		+ "foreign key (actorID) references actor"
            		+ ")";  
            
            String insert_casting =  "" +
                    "insert into casting values (1,1,'Main actor');" +
                    "insert into casting values (1,2,'Supporting Actor');" +
                    "insert into casting values (2,3,'Main actor');" +
                    "insert into casting values (2,4,'Main actor');" +
                    "insert into casting values (3,3,'Main actor');" +
                    "insert into casting values (3,5,'Main actor');" +
                    "insert into casting values (4,8,'Main actor');" +
                    "insert into casting values (4,9,'Supporting Actor');" +
                    "insert into casting values (5,6,'Main actor');" +
                    "insert into casting values (5,7,'Main actor');";

       
            String create_make = "CREATE table make("
            		+ "movieID int,"
            		+ "directorID int,"
            		+ "primary key (movieID, directorID),"
            		+ "foreign key (movieID) references movie on delete cascade,"
            		+ "foreign key (directorID) references director"
            		+ ")";  
            
            String insert_make = "" +
                    "insert into make values (1,1);" +
                    "insert into make values (2,2);" +
                    "insert into make values (3,2);" +
                    "insert into make values (4,3);" +
                    "insert into make values (5,4);";
   
            String create_customerRate = "CREATE table customerRate("
            		+ "customerID int,"
            		+ "movieID int ,"
            		+ "rate real,"
            		+ "primary key (customerID, movieID),"
            		+ "foreign key (customerID) references customer on delete cascade,"
            		+ "foreign key (movieID) references movie on delete cascade"
            		+ ")";
            
            String delete_all_data = ""       		
            		+ "delete from movieObtain;"
            		+ "delete from actorObtain;"
            		+ "delete from directorObtain;"
            		+ "delete from casting;"
            		+ "delete from make;"
            		+ "delete from customerRate;"
            		+ "delete from movieGenre;"
            		+ "delete from award;"
            		+ "delete from movie;"
            		+ "delete from genre;"
            		+ "delete from actor;"
            		+ "delete from director;"
            		+ "delete from customer;";

            // table 생성
            stmt.execute(create_director);
            stmt.execute(create_actor);
            stmt.execute(create_movie);
            stmt.execute(create_customer);
            
            stmt.execute(create_award);
            stmt.execute(create_genre);

            stmt.execute(create_movieGenre);
            stmt.execute(create_movieObtain);
            stmt.execute(create_actorObtain);
			stmt.execute(create_directorObtain);
			stmt.execute(create_casting);
            stmt.execute(create_make);
            stmt.execute(create_customerRate);
            
            stmt.executeUpdate(delete_all_data); // 모든 data 삭제
            
            stmt.executeUpdate(insert_director);
            stmt.executeUpdate(insert_actor);
            stmt.executeUpdate(insert_movie);            
            stmt.executeUpdate(insert_customer);	  
            
            // award는 넣을거 X
            stmt.executeUpdate(insert_genre);
            stmt.executeUpdate(insert_movieGenre);
            // award 관련 넣을거 X 
            stmt.executeUpdate(insert_casting);
            stmt.executeUpdate(insert_make);
            // customerRate관련 data없음
            System.out.println("Initial data inserted done!!\n");

            // ### 2번 ###        
            System.out.println("(2.1 Statement)SWinona Ryder won the “Best supporting actor” award in 1994");
            sql = "insert into award values (1,'Best supporting actor') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL)"+sql);
            stmt.executeUpdate(sql);
            sql = "insert into actorObtain values (1,1,'1994');";
            System.out.println("(SQL)"+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from actorObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(actorObtain table)");
            System.out.println(String.format("%10s %30s %30s","actorID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            System.out.println("(2.2 Statement) Andrew Garfield won the “Best supporting actor” award in 2011");
            sql = "insert into award values (1,'Best supporting actor') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into actorObtain values (9,1,'2011');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from actorObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(actorObtain table)");
            System.out.println(String.format("%10s %30s %30s","actorID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            System.out.println("(2.3 Statement) Jesse Eisenberg won the 'Best main actor' award in 2011");
            sql = "insert into award values (2,'Best main actor') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into actorObtain values (8,2,'2011');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from actorObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(actorObtain table)");
            System.out.println(String.format("%10s %30s %30s","actorID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            System.out.println("(2.4 Statement) Johnny Depp won the 'Best villain actor' award in 2011");
            sql = "insert into award values (3,'Best villain actor') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into actorObtain values (3,3,'2011');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from actorObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(actorObtain table)");
            System.out.println(String.format("%10s %30s %30s","actorID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();

            System.out.println("(2.5 Statement) Edward Scissorhands won the 'Best fantasy movie' award in 1991");
            sql = "insert into award values (4,'Best fantasy movie') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into movieObtain values (2,4,'1991');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from movieObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(movieObtain table)");
            System.out.println(String.format("%10s %30s %30s","movieID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            System.out.println("(2.6 Statement) Alice In Wonderland won the 'Best fantasy movie' award in 2011");
            sql = "insert into award values (4,'Best fantasy movie') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into movieObtain values (3,4,'2011');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from movieObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(movieObtain table)");
            System.out.println(String.format("%10s %30s %30s","movieID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            System.out.println("(2.7 Statement) Youn Yuhjung won the 'Best supporting actor' award in 2021");
            sql = "insert into award values (1,'Best supporting actor') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into actorObtain values (2,1,'2021');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
          
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from actorObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(actorObtain table)");
            System.out.println(String.format("%10s %30s %30s","actorID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            System.out.println("(2.8 Statement) Minari won the 'Best Foreign Language Film' award in 2021");
            sql = "insert into award values (5,'Best Foreign Language Film') on conflict (awardID) do NOTHING;";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
     
            sql = "insert into movieObtain values (1,5,'2021');";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
            
            sql = "select * from award";
            rs = stmt.executeQuery(sql);
            System.out.println("(award table)");
            System.out.println(String.format("%10s %30s", "awardID","awardName"));
            while(rs.next()) {
                System.out.println(String.format("%10d %30s",rs.getInt(1),rs.getString(2)));
            }
            sql = "select * from movieObtain";
            rs = stmt.executeQuery(sql);
            System.out.println("(movieObtain table)");
            System.out.println(String.format("%10s %30s %30s","movieID","awardID","year"));
            while(rs.next()) {
                System.out.println(String.format("%10s %30s %30s",rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            System.out.println();
            
            // ### 3번 ###
            
            System.out.println("(3.1 Statement) Bob rates 3 to 'The Dark Knight'");
            sql = "insert into customerRate values (1, 5, 5)";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
            
            sql = "update movie set avgRate = A.ar from (select customerRate.movieID, avg(rate) as ar from customerRate group by customerRate.movieID) as A where movie.movieID = A.movieID";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
                        
			sql = "select * from customerRate";
			rs = stmt.executeQuery(sql);
			System.out.println("(customerRate table)");
			System.out.println(String.format("%10s %30s %30s", "customerID", "movieID","rate"));
			
			while (rs.next()) {
				System.out.println(String.format("%10d %30d %30d", rs.getInt(1), rs.getInt(2),rs.getInt(3)));
			}
			
			sql = "select * from movie";
			rs = stmt.executeQuery(sql);
			System.out.println("(movie table)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
			System.out.println();
            
            System.out.println("(3.2 Statement) Bell rates 5 to the movies whose director is 'Tim Burton'.");
            sql = "insert into customerRate select 5,movieID,5 from make where directorID = (select directorID from director where directorName = 'Tim Burton')";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
                        
            sql = "update movie set avgRate = A.ar from (select customerRate.movieID, avg(rate) as ar from customerRate group by customerRate.movieID) as A where movie.movieID = A.movieID";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
                        
			sql = "select * from customerRate";
			rs = stmt.executeQuery(sql);
			System.out.println("(customerRate table)");
			System.out.println(String.format("%10s %30s %30s", "customerID", "movieID","rate"));
			
			while (rs.next()) {
				System.out.println(String.format("%10d %30d %30d", rs.getInt(1), rs.getInt(2),rs.getInt(3)));
			}
			
			sql = "select * from movie";
			rs = stmt.executeQuery(sql);
			System.out.println("(movie table)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
			
            System.out.println("(3.3 Statement) Jill rates 4 to the movies whose main actor is female.");
            sql = "insert into customerRate select 4,movieID,4 from casting natural join actor where casting.actorID = actor.actorID and role = 'Main actor' and gender = 'Female'";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
                        
            sql = "update movie set avgRate = A.ar from (select customerRate.movieID, avg(rate) as ar from customerRate group by customerRate.movieID) as A where movie.movieID = A.movieID";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
                        
			sql = "select * from customerRate";
			rs = stmt.executeQuery(sql);
			System.out.println("(customerRate table)");
			System.out.println(String.format("%10s %30s %30s", "customerID", "movieID","rate"));
			
			while (rs.next()) {
				System.out.println(String.format("%10d %30d %30d", rs.getInt(1), rs.getInt(2),rs.getInt(3)));
			}
			
			sql = "select * from movie";
			rs = stmt.executeQuery(sql);
			System.out.println("(movie table)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
            
            System.out.println("(3.4 Statement) Jack rates 4 to the fantasy movies");
            sql = "insert into customerRate select 3,movieID,4 from movie natural join movieGenre where genreName = 'Fantasy'";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
                        
            sql = "update movie set avgRate = A.ar from (select customerRate.movieID, avg(rate) as ar from customerRate group by customerRate.movieID) as A where movie.movieID = A.movieID";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
                        
			sql = "select * from customerRate";
			rs = stmt.executeQuery(sql);
			System.out.println("(customerRate table)");
			System.out.println(String.format("%10s %30s %30s", "customerID", "movieID","rate"));
			
			while (rs.next()) {
				System.out.println(String.format("%10d %30d %30d", rs.getInt(1), rs.getInt(2),rs.getInt(3)));
			}
			
			sql = "select * from movie";
			rs = stmt.executeQuery(sql);
			System.out.println("(movie table)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
            
            System.out.println("(3.5 Statement) John rates 5 to the movies whose actor won the 'Best supporting actor' award");
            sql = "insert into customerRate select distinct 2,movieID,5 from casting natural join "
            		+ "actorObtain natural join award where awardName = 'Best supporting actor'";
            System.out.println("(SQL) "+sql);
            stmt.executeUpdate(sql);
                        
            sql = "update movie set avgRate = A.ar from (select customerRate.movieID, avg(rate) as ar from customerRate group by customerRate.movieID) as A where movie.movieID = A.movieID";
            System.out.println("(SQL) "+sql);
            System.out.println();
            stmt.executeUpdate(sql);
                        
			sql = "select * from customerRate";
			rs = stmt.executeQuery(sql);
			System.out.println("(customerRate table)");
			System.out.println(String.format("%10s %30s %30s", "customerID", "movieID","rate"));
			
			while (rs.next()) {
				System.out.println(String.format("%10d %30d %30d", rs.getInt(1), rs.getInt(2),rs.getInt(3)));
			}
			
			sql = "select * from movie";
			rs = stmt.executeQuery(sql);
			System.out.println("(movie table)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
			
			// ### 4번 ###
            System.out.println("(4 Statement) Select the names of the movies whose actor are dead.");
            sql = "select actorName from actor where now() >= to_timestamp(dateOfDeath,'YYYY.MM.DD')::Date";
			rs = stmt.executeQuery(sql);
            System.out.println("(SQL) "+sql);
            System.out.println();
            System.out.println("(list of dead actor)");
            while (rs.next()) {
            	 System.out.println(rs.getString(1) + " is dead..");
			}
            System.out.println();

            
            // ### 5번 ###
            System.out.println("(5 Statement) Select the names of the directors who cast the same actor more than once.");
            sql = "select i.directorName from director as i where 1<some(select count(movieID) from (casting natural join make natural join director) as j"
            		+ "\n     where j.directorID = i.directorID group by actorID)";
			rs = stmt.executeQuery(sql);
            System.out.println("(SQL) "+sql);
            System.out.println();
			
			System.out.println("(answer)");
			while (rs.next()) {
				System.out.println(rs.getString(1) + " cast the same actor more than once");
			}
            System.out.println();

			
			// ### 6번 ###
            System.out.println("(6 Statement) Select the names of the movies and the genres, where movies have the common genre.");
            sql = "select movieName, genreName from (movie natural join movieGenre) as i "
            		+ "where 1 < (select count(distinct movieID) from movieGenre where i.genreName = movieGenre.genreName)";
			rs = stmt.executeQuery(sql);
            System.out.println("(SQL) "+sql);
            System.out.println();
			
			System.out.println("(answer)");
			System.out.println(String.format("%30s %30s", "movieName","genreName"));
			while (rs.next()) {
				System.out.println(String.format("%30s %30s", rs.getString(1),rs.getString(2)));
			}
			
			// ### 7번 ###
            System.out.println("(7 Statement) Delete the movies whose director or actor did not get any award and delete data from related tables.");
            sql = "delete from movie where movieID not in("
					+ "(select movieID from actorObtain natural join casting) union"
					+ "(select movieID from directorObtain natural join make)"
					+ ")";
            System.out.println("(SQL) "+sql);
            System.out.println();
            
            sql = "select * from movie";
			rs = stmt.executeQuery(sql);
            System.out.println("(movie table : before removed)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
			System.out.println();
			
			sql = "delete from movie where movieID not in("
					+ "(select movieID from actorObtain natural join casting) union"
					+ "(select movieID from directorObtain natural join make)"
					+ ")";
			stmt.executeUpdate(sql);
      
			sql = "select * from movie";
			rs = stmt.executeQuery(sql);
			System.out.println("(movie table : after removed)");
			System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25s", 
					"movieID", "movieName", "releaseYear", "releaseMonth", "releaseDate", "publisherName", "avgRate"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s %25s %25s %25f", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getFloat(7)));
			}
			System.out.println();

			// ### 8번 ##
			System.out.println("(8 Statement) Delete all customers and delete data from related tables.");
            sql = "delete from customer";
            System.out.println("(SQL) "+sql);
            System.out.println();
			stmt.executeUpdate(sql);
			
			sql = "select * from customer";
  			rs = stmt.executeQuery(sql);

			System.out.println("(customer table : after removed)");
			System.out.println(String.format("%10s %25s %25s %25s", 
					"customerID", "customerName", "dateOfBirth", "gender"));
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s %25s", 
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			System.out.println();

			
			sql = "select * from customerRate";
  			rs = stmt.executeQuery(sql);

			System.out.println("(cutstomerRate table : after removed)");
			System.out.println(String.format("%10s %25s %25s", 
					"customerID", "movieID", "rate"));
			
			while (rs.next()) {
				System.out.println(String.format("%10s %25s %25s", 
						rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			
			// ### 9번 ###
			delete_all_data = ""       		
            		+ "delete from movieObtain;"
            		+ "delete from actorObtain;"
            		+ "delete from directorObtain;"
            		+ "delete from casting;"
            		+ "delete from make;"
            		+ "delete from customerRate;"
            		+ "delete from movieGenre;"
            		+ "delete from award;"
            		+ "delete from movie;"
            		+ "delete from genre;"
            		+ "delete from actor;"
            		+ "delete from director;"
            		+ "delete from customer;";
            stmt.executeUpdate(delete_all_data); // 모든 data 삭제
            System.out.println("\ndelete all table..");
        } catch (SQLException ex) {
            throw ex;
        }
            
    }
}