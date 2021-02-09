package de.zettsystems.netzfilm.movie.values;

// Das model
public class RentableCopy {
    private long id;
    private String title;

    public RentableCopy() {
        super();
    }

    public RentableCopy(long id, String title) {
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
        return "RentableCopy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
