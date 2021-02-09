package de.zettsystems.netzfilm.movie.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;
    private String uuid;
    private String title;
    private LocalDate releaseDate;

    //needed for jpa
    protected Movie() {
        super();
    }

    public Movie(String title, LocalDate releaseDate) {
        this(UUID.randomUUID().toString(), title, releaseDate);
    }

    public Movie(String uuid, String title, LocalDate releaseDate) {
        this();
        this.uuid = uuid;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
