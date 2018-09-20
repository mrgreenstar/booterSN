package com.mrgreenstar.sn.Entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "Post")
public class Post implements Comparable<Post>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "content")
    public String content;

    public Post() {}

    public Post(Date time, String content) {
        this.time = time;
        this.content = content;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTimeAsString() {
        SimpleDateFormat formatter;
        Date currentTime = new Date();
        Date date = getTime();
        long diff = currentTime.getTime() - date.getTime();
        long diffDays = diff / (1000 * 60 * 60 * 24);
        if (diffDays >= 7) {
            formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            return formatter.format(date);
        }
        formatter = new SimpleDateFormat("EEE, HH:mm");
        return formatter.format(date);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(Post b) {
        return b.getTime().compareTo(this.getTime());
    }
}
