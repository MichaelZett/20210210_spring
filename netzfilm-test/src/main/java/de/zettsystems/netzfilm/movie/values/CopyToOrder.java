package de.zettsystems.netzfilm.movie.values;

public class CopyToOrder {
    private long movieId;
    private String type;

    public CopyToOrder() {
        super();
    }

    public CopyToOrder(long movieId, String type) {
        this.movieId = movieId;
        this.type = type;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
