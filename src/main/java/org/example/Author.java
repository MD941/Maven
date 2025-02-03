package org.example;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @OneToMany(mappedBy = "author")
    private Set<Book> bookSet;

    @ManyToMany
    @JoinTable(
            name = "author_publisher",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<Publisher> publishers;

    public Author() {
    }

    public Author(int id, String name, Set<Book> bookSet, Set<Publisher> publishers) {
        this.id = id;
        this.name = name;
        this.bookSet = bookSet;
        this.publishers = publishers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBookSet() {
        return bookSet;
    }

    public void setBookSet(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublisherSet(Set<Publisher> publishers) {
        this.publishers = publishers;
    }
}
