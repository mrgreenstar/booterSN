package com.mrgreenstar.sn.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "UserInfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "country")
    private String country;

    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "password")
    private String password;

    @Column(name = "isActive")
    private byte isActive;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    // Список пользователей, на которых подписан данный пользователь
    @OneToMany(mappedBy = "user")
    private Set<Subscriptions> subscriptions;

    public User() { }

    public User(String firstName, String lastName, String country,
                String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.isActive = 1;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(byte active) {
        isActive = active;
    }

    public byte isActive() {
        return isActive;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setSubscriptions(Set<Subscriptions> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<Subscriptions> getSubscriptions() {
        return subscriptions;
    }
}
