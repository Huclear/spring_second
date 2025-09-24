package com.mpt.journal.domain.model;

import com.mpt.journal.domain.entity.UserEntity;

public class UserModel extends UserEntity {
    public UserModel(String id, String login, String nickname, String aboutMe, String password, String salt) {
        super(id, login, nickname, aboutMe, password, salt);
    }

    public UserModel(String login, String nickname, String aboutMe, String password, String salt) {
        super(login, nickname, aboutMe, password, salt);
    }
}
