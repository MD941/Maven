package org.example;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "publishers")
    private Set<Author> authors;

}
