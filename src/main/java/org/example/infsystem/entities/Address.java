package org.example.infsystem.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_name", nullable = false, unique = true)
    private String address_name;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "address_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PasswordData> passwordData;

    public Address() {}

    public Address(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
