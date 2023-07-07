package net.yarik.todolist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "banned_users")
public class BannedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    public BannedUser() {
    }

    public BannedUser(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
