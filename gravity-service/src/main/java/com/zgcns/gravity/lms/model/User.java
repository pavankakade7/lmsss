package com.zgcns.gravity.lms.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zgcns.gravity.authmanager.entity.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isVerified;

    private String jwtToken;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Column(name = "role")
    private Set<Role> roles;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;


    @Column(name = "password")
    private String password;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Employee employee;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LeaveData leaveData;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveData getLeaveData() {
        return leaveData;
    }

    public void setLeaveData(LeaveData leaveData) {
        this.leaveData = leaveData;
    }

    public User(Long userId, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt,
            LocalDateTime lastLogin, boolean isActive, boolean isVerified, String jwtToken, Set<Role> roles,
            String firstName, String lastName, String password, Employee employee, LeaveData leaveData) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.jwtToken = jwtToken;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.employee = employee;
        this.leaveData = leaveData;
    }

    // Getters and Setters
     public User(){
        super();
     }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", lastLogin=" + lastLogin + ", isActive=" + isActive + ", isVerified="
                + isVerified + ", jwtToken=" + jwtToken + ", roles=" + roles + ", firstName=" + firstName
                + ", lastName=" + lastName + ", password=" + password + ", employee=" + employee + ", leaveData="
                + leaveData + "]";
    }
     

    
    
}
