package de.zettsystems.netzfilm.movie.values;

public class OrderableMovie {
    private long id;
    private String title;

    public OrderableMovie() {
        super();
    }

    public OrderableMovie(Long id, String title) {
        this();
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "OrderableMovie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
