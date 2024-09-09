package com.ohgiraffers.crud_back.model.dto;

public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String imagePath;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String content, String author, String imagePath) {
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}