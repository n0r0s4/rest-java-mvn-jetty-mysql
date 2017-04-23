/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.apiexample.service;

import cat.proven.apiexample.dao.MovieDAO;
import cat.proven.apiexample.dao.DAOFactory;

import cat.proven.apiexample.entities.Movie;
import cat.proven.apiexample.entities.Actor;

import java.util.Collection;

public class MovieService {
    
    public MovieDAO movieDAO;
    
    public MovieService () {
        movieDAO = DAOFactory.getInstance().createMovieDAO();
    }
    
    public Movie getMovieById(int idMovie) {
        Movie c = movieDAO.findById(idMovie);
        return c;
    }
   
    public Movie addMovie(Movie movie) {
        Movie c = movieDAO.add(movie);
        return c;
    }
    
    public Movie updateMovie(Movie movie) {
        Movie c = movieDAO.update(movie);
        return c;
    }
    
    public int deleteMovie(Movie movie) {
        int result = movieDAO.delete(movie);
        return result;
    }
    
    public Collection<Movie> getMovies() { 
        Collection<Movie> movies = movieDAO.findAll();
        return movies;
    }

    public Collection<Movie> getMoviesByIdActor(int idActor) {
        Collection<Movie> movies = movieDAO.findByIdActor(idActor);
        return movies;
    }
    
    public Collection<Actor> getMovieActors(int idMovie) {
        ActorService actorService = new ActorService();
        Collection<Actor> actors = actorService.getActorsByIdMovie(idMovie);
        return actors;
    }
    
}
