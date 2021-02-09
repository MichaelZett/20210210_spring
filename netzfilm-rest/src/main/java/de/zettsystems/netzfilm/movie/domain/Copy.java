package de.zettsystems.netzfilm.movie.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Copy {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;
    private String uuid;
    @Enumerated(EnumType.STRING)
    private CopyType type;
    @ManyToOne
    private Movie movie;
    private boolean lent;

    //needed for jpa
    protected Copy() {
        super();
    }

    public Copy(CopyType type, Movie movie) {
        this();
        this.lent = false;
        this.uuid = UUID.randomUUID().toString();
        this.type = type;
        this.movie = movie;
    }

    public void lend() {
        this.lent = true;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getUuid() {
        return uuid;
    }

    public CopyType getType() {
        return type;
    }

    public Movie getMovie() {
        return movie;
    }
}
