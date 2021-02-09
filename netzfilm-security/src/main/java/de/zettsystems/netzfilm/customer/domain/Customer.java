package de.zettsystems.netzfilm.customer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;
    @Version
    @NotNull
    private long version;
    @NotBlank
    private String uuid;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    private String username;

    //for jpa
    protected Customer() {
        super();
    }

    public Customer(String name, String lastName, LocalDate birthdate, String username) {
        this(UUID.randomUUID().toString(), name, lastName, birthdate, username);
    }

    public Customer(String uuid, String name, String lastName, LocalDate birthdate, String username) {
        this();
        this.uuid = uuid;
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.username = username;
    }

    public void updateData(String name, String lastName, LocalDate birthdate) {
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
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

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
