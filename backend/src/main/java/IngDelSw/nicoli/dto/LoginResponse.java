
package IngDelSw.nicoli.dto;

public class LoginResponse {
    private String token;
    private Integer user;

    public LoginResponse(String token, Integer user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return user;
    }

    public void setUserId(Integer user) {
        this.user = user;
    }
}

