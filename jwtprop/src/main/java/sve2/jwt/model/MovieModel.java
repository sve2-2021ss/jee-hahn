package sve2.jwt.model;

import sve2.jwt.domain.Movie;

public class MovieModel {
    private Long id;
    private String name;
    private Integer releaseYear;
    private double rating;
    private String productionCountry;
    private Integer length;

    public MovieModel() {
    }

    public MovieModel(String name, Integer releaseYear, double rating, String productionCountry, Integer length) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.productionCountry = productionCountry;
        this.length = length;
    }

    public MovieModel(Movie movie) {
        id = movie.getId();
        name = movie.getName();
        releaseYear = movie.getReleaseYear();
        rating = movie.getRating();
        productionCountry = movie.getProductionCountry();
        length = movie.getLength();
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
