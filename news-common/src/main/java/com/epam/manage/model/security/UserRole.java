package com.epam.manage.model.security;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole{

    @Id
    @Column(name = "ROLE_ID")
    @SequenceGenerator(name="ROLES_SEQ", sequenceName="ROLES_SEQ")
    @GeneratedValue(generator="ROLES_SEQ")
    private Integer roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ROLE_NAME")
    private String role;

    public UserRole() {
    }

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }


    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer userRoleId) {
        this.roleId = userRoleId;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}