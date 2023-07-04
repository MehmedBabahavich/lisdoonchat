package net.yarik.todolist.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body", length = 1024)
    private String body;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "lastBump")
    private LocalDateTime lastBump;


    public Post() {
    }

    public Post(Long id, String title, String body, String imageName, String createdAt, LocalDateTime lastBump) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageName = imageName;
        this.createdAt = createdAt;
        this.lastBump = lastBump;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastBump() {
        return lastBump;
    }

    public void setLastBump(LocalDateTime lastBump) {
        this.lastBump = lastBump;
    }
}
