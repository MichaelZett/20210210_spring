package de.zettsystems.netzfilm.movie.values;

import java.time.LocalDate;
import java.util.Objects;

public class MovieDbEntry {
    private String title;
    private LocalDate releaseDate;

    public MovieDbEntry() {
        super();
    }

    public MovieDbEntry(String title, LocalDate releaseDate) {
        this();
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDbEntry that = (MovieDbEntry) o;
        return title.equals(that.title) && releaseDate.equals(that.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseDate);
    }

    @Override
    public String toString() {
        return "MovieDbEntry{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
