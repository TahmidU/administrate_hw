package com.tahmidu.address_book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organisation")
@Data
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organisation_id", nullable = false, unique = true)
    private Long organisationId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String number;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.REMOVE)
    private List<Person> people;

    public Organisation() { }

    public Organisation(Long organisationId, String name, String number, String email) {
        this.organisationId = organisationId;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public Organisation(Long organisationId, String name, String number, String email, List<Person> people) {
        this.organisationId = organisationId;
        this.name = name;
        this.number = number;
        this.email = email;
        this.people = people;
    }
}
