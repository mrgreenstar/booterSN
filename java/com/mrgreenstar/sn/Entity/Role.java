package com.mrgreenstar.sn.Entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long role_id;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @Column(unique = true)
    private String roleName;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
