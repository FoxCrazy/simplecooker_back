package ru.foxcrazy.simplecooker.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name="full_name")
    private String fullName;

    @Column(name = "pw_hash")
    private String pwHash;

    @Column
    private String email;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    private Boolean isAdmin;

    public User() {
    }

    public User(String fullName, String pwHash, String email, Date createdAt) {
        this.fullName = fullName;
        this.pwHash = pwHash;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Integer getUserId(){return userId;}
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User [ID= " + userId + ", name=" + fullName + ", created at=" + createdAt + ", email= "+ email + "]";
    }
}