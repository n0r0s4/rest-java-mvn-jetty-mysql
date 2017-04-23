package cat.proven.apiexample.dao;

import cat.proven.apiexample.entities.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cat.proven.apiexample.entities.Actor;
import java.util.List;

public class ActorDAO {

    private Connection connection;

    public ActorDAO(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public Collection<Actor> findAll() {
        String sql = "SELECT * FROM actors";
        ArrayList<Actor> list = new ArrayList<Actor>();

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Actor actor = new Actor(rs.getInt("id"), rs.getString("name"), rs.getString("birthPlace"), rs.getString("bio"));
                MovieDAO movieDAO = new MovieDAO(connection);
                actor.setMovies((List<Movie>) movieDAO.findByIdActor(actor.getId()));
                
                list.add(actor);
            }

        } catch (Exception e) {
                e.printStackTrace();
        }
        return list;

    }
    
     public Collection<Actor> findByIdMovie(int idMovie) {
            String sql = "SELECT id, name, birthPlace, bio FROM actorsinmovies sc INNER JOIN actors s ON sc.idActor = s.id WHERE idMovie= ?";

            ArrayList<Actor> list = new ArrayList<Actor>();

            try {
                    PreparedStatement statement = getConnection().prepareStatement(sql);
                    statement.setInt(1, idMovie);
                    ResultSet rs = statement.executeQuery();

                    while (rs.next()) {
                        Actor actor = new Actor(rs.getInt("id"), rs.getString("name"), rs.getString("birthPlace"), rs.getString("bio"));
                        list.add(actor);
                    }

            } catch (Exception e) {
                    e.printStackTrace();
            }
            return list;

	}

    public Actor findById(int id) {
        String sql = "SELECT * FROM actors WHERE id= ?";
        Connection conn = null;

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Actor actor = new Actor(rs.getInt("id"), rs.getString("name"), rs.getString("birthPlace"), rs.getString("bio"));
                MovieDAO movieDAO = new MovieDAO(connection);
                actor.setMovies((List<Movie>) movieDAO.findByIdActor(actor.getId()));
                
                return actor;
            }

        } catch (SQLException e) {
                e.printStackTrace();
        }
        return null;

    }

    public Actor add(Actor actor) {
        String sql = "INSERT INTO actors VALUES (NULL,?,?,?)";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getBirthPlace());
            statement.setString(3, actor.getBio());
            int rs = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actor;

    }

    public Actor update(Actor actor) {
        String sql = "UPDATE actors SET name = ?, birthPlace = ?, bio = ? WHERE id = ?";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getBirthPlace());
            statement.setString(3, actor.getBio());
            statement.setInt(4, actor.getId());
            int rs = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actor;

    }

    public int delete(Actor actor) {
        String sql = "DELETE FROM actors WHERE id = ?";
        int rs = 0;

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setInt(1, actor.getId());
            rs = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }


}
