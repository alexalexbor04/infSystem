package org.example.infsystem.dto;

public class PasswordDataDTO {
    private Long id;
    private String username;
    private String login;
    private String address_name;
    private String url;
    private String userPassword;

    public PasswordDataDTO(Long id, String username, String login,
                           String address_name, String url, String userPassword) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.address_name = address_name;
        this.url = url;
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAddressName() {
        return address_name;
    }

    public void setAddressName(String address_name) {
        this.address_name = address_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
