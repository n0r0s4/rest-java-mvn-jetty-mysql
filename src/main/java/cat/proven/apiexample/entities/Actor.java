package cat.proven.apiexample.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Actor {

	private Integer id;
	private String name;
	private String birthPlace;
        private String bio;
	private List<Movie> movies;

	public Actor() {
		this.movies = new ArrayList<Movie>();
	}

	public Actor(int id, String name, String lastname, String bio) {
		// Call to default constructor, in this case to fill the courses list
		this();
		this.id = id;
		this.name = name;
		this.birthPlace = lastname;
                this.bio= bio;
	}

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

}
