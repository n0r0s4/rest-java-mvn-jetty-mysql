package cat.proven.apiexample.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMS
 */
@XmlRootElement
public class Movie {
    private int id;
    private int year;
    private String name;
    private List<Actor> actors;

    public Movie() {
        this.actors = new ArrayList<Actor>();
    }

    public Movie(int id, String name, int year) {
        this();
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Actor> getActors() {
            return actors;
    }

    public void setMovies(List<Actor> actors) {
            this.actors = actors;
    }
    
    public static boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
}
