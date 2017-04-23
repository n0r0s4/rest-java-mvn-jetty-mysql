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


@Path("actors")
@Produces("application/json")
public class ActorsResource {

    ActorService actorService;
    MovieService movieService;

    public ActorsResource(@Context ServletContext context) {

        if (context.getAttribute("actorService") != null)
                actorService = (ActorService) context.getAttribute("actorService");
        else {
                actorService = new ActorService();
                context.setAttribute("actorService", actorService);
        }

        if (context.getAttribute("movieService") != null)
                movieService = (MovieService) context.getAttribute("movieService");
        else {
                movieService = new MovieService();
                context.setAttribute("movieService", movieService);
        }

    }

    @GET
    public Response actors() {
        Collection<Actor> actors = actorService.getActors();
        GenericEntity<Collection<Actor>> result = new GenericEntity<Collection<Actor>>(actors) {
        };
        return Response.ok().entity(result).build();
    }

    @Path("{id}")
    @GET
    public Response getActorById(@PathParam("id") int id) {
        Actor actor = actorService.getActorById(id);
        if (actor == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(actor).build();
    }

    @POST
    public Response addActor(@FormParam("name") String name, 
                             @FormParam("birthPlace") String birthPlace,
                             @FormParam("bio") String bio) {

        if (name == null || name.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter name is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        if (birthPlace == null || birthPlace.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter birthPlace is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        
        if (bio == null || bio.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter bio is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        Actor actor = actorService.addActor(new Actor(0, name, birthPlace, bio));
        return Response.ok(actor).build();
    }
    

    @Path("{id}")
    @PUT
    public Response updateActor(@FormParam("name") String name, 
                                @FormParam("birthPlace") String birthPlace,
                                @FormParam("bio") String bio,
                                @PathParam("id") int id) {

        if (name == null || name.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter name is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        if (birthPlace == null || birthPlace.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter birthPlace is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        
        if (bio == null || bio.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter bio is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        
        Actor actor = actorService.getActorById(id);

        if (actor == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        actor.setName(name);
        actor.setBirthPlace(birthPlace);
        actor.setBio(bio);
        actor = actorService.updateActor(actor);

        return Response.ok(actor).build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteActor(@PathParam("id") int id) {
        Actor actor = actorService.getActorById(id);
        if (actor == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else {
            actorService.deleteActor(actor);
            return Response.ok(actor).build();
        }
    }

    @Path("{id}/movies")
    @GET
    public Response getActorMovies(@PathParam("id") int id) {
        List<Movie> actorMovies = (List<Movie>) actorService.getActorMovies(id);

        if (actorMovies == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        else {

            GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(actorMovies) {
            };

            return Response.ok().entity(entity).build();
        }
    }


    @Path("{id_actor}/movies/{id_movie}")
    @POST
    public Response addActorToMovie(@PathParam("id_actor") int idActor, 
                                    @PathParam("id_movie") int idMovie) {

        Actor actor = actorService.getActorById(idActor);
        if (actor == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        Movie movie = movieService.getMovieById(idMovie);
        if (movie == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        actor.getMovies().add(movie);
        return Response.ok().build();
    }

}
