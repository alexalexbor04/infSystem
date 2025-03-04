package org.example.infsystem.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "password_data")
public class PasswordData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address_id;

    @Column(name = "user_login", nullable = false)
    private String user_login;

    @Column(name = "user_password", nullable = false)
    private String user_password;

    public PasswordData() {}

    public PasswordData(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Address getAddressId() {
        return address_id;
    }

    public void setAddressId(Address address_id) {
        this.address_id = address_id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
