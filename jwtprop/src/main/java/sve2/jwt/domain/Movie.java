package sve2.jwt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer releaseYear;
    private double rating;
    private String productionCountry;
    private Integer length;

    public Movie() {
    }

    public Movie(String name, Integer releaseYear, double rating, String productionCountry, Integer length) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.productionCountry = productionCountry;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getProductionCountry() {
        return productionCountry;
    }

    public void setProductionCountry(String productionCountry) {
        this.productionCountry = productionCountry;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
