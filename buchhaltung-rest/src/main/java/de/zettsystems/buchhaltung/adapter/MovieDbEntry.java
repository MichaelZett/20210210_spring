package de.zettsystems.buchhaltung.adapter;

import java.time.LocalDate;

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
}
