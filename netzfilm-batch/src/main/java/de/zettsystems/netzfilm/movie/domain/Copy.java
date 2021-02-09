package de.zettsystems.netzfilm.movie.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Copy {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;
    @NotBlank
    private String uuid;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CopyType type;
    @NotNull
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

    @Override
    public String toString() {
        return "Copy{" +
                "id=" + id +
                ", version=" + version +
                ", uuid='" + uuid + '\'' +
                ", type=" + type +
                ", movie=" + movie +
                ", lent=" + lent +
                '}';
    }
}
