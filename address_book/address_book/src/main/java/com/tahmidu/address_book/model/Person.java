package com.tahmidu.address_book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNum;

    @JsonIgnore
    @ManyToOne
    private Organisation organisation;

    public Person() {
    }

    public Person(Long personId, String firstName, String lastName, String phoneNum) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
    }
}
