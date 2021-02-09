package de.zettsystems.netzfilm.customer.values;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CustomerData {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate birthdate;
    @NotBlank
    private String username;
    private long version;

    public CustomerData() {
        super();
    }

    public CustomerData(String name, String lastName, LocalDate birthdate, String username) {
        this();
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.username = username;
    }

    public CustomerData(String name, String lastName, LocalDate birthdate, long version, String username) {
        this(name, lastName, birthdate, username);
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
