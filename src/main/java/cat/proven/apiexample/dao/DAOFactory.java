package cat.proven.apiexample.dao;

import java.sql.Connection;

public class DAOFactory {
    private static DAOFactory instance;

    public static DAOFactory getInstance() {
        if (instance == null) {
                instance = new DAOFactory();
        }
        return instance;
    }

    private DAOFactory() {
        super();
    }

    public ActorDAO createActorDAO() {
        Connection connection = ConnectionFactory.getInstance().connect();
        return new ActorDAO(connection);
    }

    public MovieDAO createMovieDAO() {
        Connection connection = ConnectionFactory.getInstance().connect();
        return new MovieDAO(connection);
    }
}
