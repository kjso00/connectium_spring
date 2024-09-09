package com.ohgiraffers.crud_back.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "posts")

public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String author;

    @Column
    private String imagePath;

    public PostEntity() {
    }

    public PostEntity(Long id, String title, String content, String author, String imagePath) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() { return imagePath;}

    public void setImagePath(String imagePath) { this.imagePath = imagePath;}



}
