package com.mrgreenstar.sn.Entity;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Столбец, содержащий  Id пользователя, который оформил подписки
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Столбец, содержащий id пользователей, на которые подписан пользователь
    @Column(name = "subId")
    private Long subId;

    public Subscriptions() { }

    public Subscriptions(User user, Long subId) {
        setUser(user);
        this.subId = subId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }
}
