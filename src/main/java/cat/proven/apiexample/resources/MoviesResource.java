package cat.proven.apiexample.resources;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import cat.proven.apiexample.dao.DAOFactory;
import cat.proven.apiexample.dao.ActorDAO;
import cat.proven.apiexample.entities.Movie;
import cat.proven.apiexample.entities.Actor;
import cat.proven.apiexample.exception.ApplicationException;
import cat.proven.apiexample.service.MovieService;
import cat.proven.apiexample.service.ActorService;
import javax.ws.rs.PUT;


@Path("movies")
@Produces("application/json")
public class MoviesResource {

    ActorService myActorService;
    MovieService myMovieService;

    public MoviesResource(@Context ServletContext context) {

        if (context.getAttribute("actorService") != null)
                myActorService = (ActorService) context.getAttribute("actorService");
        else {
                myActorService = new ActorService();
                context.setAttribute("actorService", myActorService);
        }

        if (context.getAttribute("movieService") != null)
                myMovieService = (MovieService) context.getAttribute("movieService");
        else {
                myMovieService = new MovieService();
                context.setAttribute("movieService", myMovieService);
        }
    }

    @GET
    public Response movies() {
        Collection<Movie> movies = myMovieService.getMovies();
        GenericEntity<Collection<Movie>> result = new GenericEntity<Collection<Movie>>(movies) {
        };
        return Response.ok().entity(result).build();
    }

    @Path("{id}")
    @GET
    public Response getMovieById(@PathParam("id") int id) {
        Movie movie = myMovieService.getMovieById(id);
        if (movie == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(movie).build();
    }

    @POST
    public Response addMovie(@FormParam("name") String name,
                              @FormParam("year") String year) {

        if (name == null || name.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter name is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        
        if (!Movie.isNumeric(year)) {
                ApplicationException ex = new ApplicationException("Parameter year must be number");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        
        Movie movie = myMovieService.addMovie(new Movie(0, name, Integer.parseInt(year)));
        return Response.ok(movie).build();
    }

    @Path("{id}")
    @PUT
    public Response updateMovie(@FormParam("name") String name,
                                 @FormParam("year") String year,
                                 @PathParam("id") int id) {

        if (name == null || name.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter name is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        if (!Movie.isNumeric(year)) {
                ApplicationException ex = new ApplicationException("Parameter year must be number");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        Movie movie = myMovieService.getMovieById(id);

        if (movie == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        movie.setName(name);
        movie.setYear(Integer.parseInt(year));
        movie = myMovieService.updateMovie(movie);

        return Response.ok(movie).build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteMovie(@PathParam("id") int id) {
        Movie movie = myMovieService.getMovieById(id);
        if (movie == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else {
            myMovieService.deleteMovie(movie);
            return Response.ok(movie).build();
        }
    }

    @Path("{id}/actors")
    @GET
    public Response getMovieActors(@PathParam("id") int id) {
        List<Actor> movieActors = (List<Actor>) myMovieService.getMovieActors(id);

        if (movieActors == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        else {

            GenericEntity<List<Actor>> entity = new GenericEntity<List<Actor>>(movieActors) {
            };

            return Response.ok().entity(entity).build();
        }
    }


    @Path("{id_actor}/movies/{id_movie}")
    @POST
    public Response addActorToMovie(@PathParam("id_actor") int idActor, 
                                    @PathParam("id_movie") int idMovie) {

        Actor actor = myActorService.getActorById(idActor);
        if (actor == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        Movie movie = myMovieService.getMovieById(idMovie);
        if (movie == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        actor.getMovies().add(movie);
        return Response.ok().build();
    }

}
