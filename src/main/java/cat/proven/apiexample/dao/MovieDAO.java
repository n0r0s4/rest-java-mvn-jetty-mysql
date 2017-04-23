/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.apiexample.dao;

import cat.proven.apiexample.entities.Movie;
import cat.proven.apiexample.entities.Actor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rusben
 */
public class MovieDAO {
    private Connection connection;

	public MovieDAO(Connection connection) {
		this.connection = connection;
	}
        
        public Connection getConnection() {
		return connection;
	}

	public Collection<Movie> findAll() {
            String sql = "SELECT * FROM movies";
            ArrayList<Movie> list = new ArrayList<Movie>();

            try {
                    PreparedStatement statement = getConnection().prepareStatement(sql);
                    ResultSet rs = statement.executeQuery();

                    while (rs.next()) {
                        Movie movie = new Movie(rs.getInt("id"), rs.getString("name"), rs.getInt("year"));
                        list.add(movie);
                    }

            } catch (Exception e) {
                    e.printStackTrace();
            }
            return list;

	}
        
        public Collection<Movie> findByIdActor(int idActor) {
            String sql = "SELECT id, name, year FROM actorsinmovies sc INNER JOIN movies c ON sc.idMovie = c.id WHERE idActor= ?";

            ArrayList<Movie> list = new ArrayList<Movie>();

            try {
                    PreparedStatement statement = getConnection().prepareStatement(sql);
                    statement.setInt(1, idActor);
                    ResultSet rs = statement.executeQuery();

                    while (rs.next()) {
                            Movie movie = new Movie(rs.getInt("id"), rs.getString("name"), rs.getInt("year"));
                            list.add(movie);
                    }

            } catch (Exception e) {
                    e.printStackTrace();
            }
            return list;

	}
        
        public Movie findById(int id) {
            String sql = "SELECT * FROM movies WHERE id= ?";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);

                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Movie movie = new Movie(rs.getInt("id"), rs.getString("name"), rs.getInt("year"));
                    return movie;
                }

            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;

	}
        
        public Movie add(Movie movie) {
            String sql = "INSERT INTO movies VALUES (NULL,?,?)";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setInt(1, movie.getYear());
                statement.setString(2,movie.getName());
                int rs = statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return movie;
                
	}
        
        public Movie update(Movie movie) {
            String sql = "UPDATE movies SET name = ?, year = ? WHERE id = ?";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setString(1, movie.getName());
                statement.setInt(2, movie.getYear());
                statement.setInt(3, movie.getId());
                int rs = statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return movie;

	}

	public int delete(Movie movie) {
            String sql = "DELETE FROM movies WHERE id = ?";
            int rs = 0;

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setInt(1, movie.getId());
                rs = statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rs;

	}

}
