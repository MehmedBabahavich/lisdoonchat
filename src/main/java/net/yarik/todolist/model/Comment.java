package net.yarik.todolist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "body", length = 1024)
    private String body;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "repliedTo")
    private Long repliedToCommentId;

    public Comment() {
    }

    public Comment(Long id, Long postId, String body, String imageName, String createdAt, Long repliedToCommentId) {
        this.id = id;
        this.postId = postId;
        this.body = body;
        this.imageName = imageName;
        this.createdAt = createdAt;
        this.repliedToCommentId = repliedToCommentId;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getPostId() { return postId; }

    public void setPostId(Long postId) { this.postId = postId; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public String getImageName() { return imageName; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public Long getRepliedToCommentId() { return repliedToCommentId; }

    public void setRepliedToCommentId(Long repliedToCommentId) { this.repliedToCommentId = repliedToCommentId; }
}
