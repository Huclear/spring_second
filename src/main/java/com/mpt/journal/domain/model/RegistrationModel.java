package com.mpt.journal.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationModel {
    @NotBlank
    @Size(min = 5)
    @Pattern(regexp = "[ !\"'@#$%^(){}*-_=+<>.,/:;|?~`]*")
    @Pattern(regexp = "[A-Za-z]+")
    @Pattern(regexp = "[0-9]*")
    private String login;

    @NotBlank
    @Size(min = 5)
    @Pattern(regexp = "[ !\"'@#$%^(){}*-_=+<>.,/:;|?~`]*")
    @Pattern(regexp = "[A-Za-z]+")
    private String nickname;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "[ !\"'@#$%^(){}*-_=+<>.,/:;|?~`]+")
    @Pattern(regexp = "[A-Za-z]+")
    @Pattern(regexp = "[0-9]+")
    private String password;

    private String aboutMe;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
