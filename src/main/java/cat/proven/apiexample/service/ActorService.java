/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.apiexample.service;

import cat.proven.apiexample.dao.DAOFactory;
import cat.proven.apiexample.dao.ActorDAO;
import cat.proven.apiexample.entities.Movie;
import cat.proven.apiexample.entities.Actor;

import java.util.Collection;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

public class ActorService {
    
    public ActorDAO actorDAO;
    
    public ActorService () {
        actorDAO = DAOFactory.getInstance().createActorDAO();
    }
    
    public Actor getActorById(int idActor) {
        Actor s = actorDAO.findById(idActor);
        return s;
    }
    
    public Actor addActor(Actor actor) {
        Actor s = actorDAO.add(actor);
        return s;
    }
    
    public Actor updateActor(Actor actor) {
        Actor s = actorDAO.update(actor);
        return s;
    }
    
    public int deleteActor(Actor actor) {
        int result = actorDAO.delete(actor);
        return result;
    }
    
    public Collection<Actor> getActors() { 
        Collection<Actor> actors = actorDAO.findAll();
        return actors;
    }
    
    public Collection<Actor> getActorsByIdMovie(int idMovie) {
        Collection<Actor> actors = actorDAO.findByIdMovie(idMovie);
        return actors;
    }
    
    public Collection<Movie> getActorMovies(int idActor) {
        MovieService movieService = new MovieService();
        Collection<Movie> movies = movieService.getMoviesByIdActor(idActor);
        return movies;
    }
    
}
