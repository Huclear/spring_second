package com.mpt.journal.domain.entity;

import java.util.UUID;

public class UserEntity {
    private final String id;
    private String login;
    private String password;
    private String salt;
    private String nickname;
    private String aboutMe;
    private Boolean deleted = false;

    public UserEntity(String id, String login, String nickname, String aboutMe, String password, String salt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.nickname = nickname;
        this.aboutMe = aboutMe;

    }

    public UserEntity(String login, String nickname, String aboutMe, String password, String salt) {
        this(UUID.randomUUID().toString(), login, nickname, aboutMe, password, salt);
    }

    public String getId() {
        return id;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
