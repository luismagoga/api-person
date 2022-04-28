package com.company.apiperson.security.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class AuthorizationRequest {

    @NotNull(message="The user is mandatory")
    @JsonProperty("user")
    private String userName;
    @NotNull(message="The password is mandatory")
    private String password;

    @NotNull(message="The roles is mandatory")
    @JsonProperty("roles")
    private Set<String> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}