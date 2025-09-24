package com.mpt.journal.domain.repository;

import com.mpt.journal.domain.entity.UserEntity;

import java.util.List;

public interface UsersRepository {
    List<UserEntity> getUsers();
    UserEntity getUserById(String userID);
    UserEntity getUserByLogin(String login);
    UserEntity addUser(UserEntity user);
    UserEntity editUser(UserEntity user);
    void deleteUser(String userID);
    void deleteUsers(List<String> userIDs);
    void confirmDeleteUser(String userID);
    void confirmDeleteUsers(List<String> userIDs);
}
