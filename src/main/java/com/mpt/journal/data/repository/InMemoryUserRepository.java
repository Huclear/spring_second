package com.mpt.journal.data.repository;

import com.mpt.journal.domain.entity.UserEntity;
import com.mpt.journal.domain.entity.UserEntity;
import com.mpt.journal.domain.repository.UsersRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryUserRepository implements UsersRepository {

    private List<UserEntity> users = new ArrayList<>();

    @Override
    public List<UserEntity> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public UserEntity getUserById(String userID) {
        return users
                .stream()
                .filter(user -> user.getId().equals(userID))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserEntity getUserByLogin(String login) {
        return users
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserEntity addUser(UserEntity user) {
        if (users
                .stream()
                .anyMatch(u ->
                        user.getId().equals(u.getId())
                                || user.getLogin().equals(u.getLogin())
                )
        )
            return null;

        users.add(user);
        return user;
    }

    @Override
    public UserEntity editUser(UserEntity user) {
        UserEntity stored = getUserById(user.getId());
        if (stored == null)
            return null;

        int index = users.indexOf(stored);
        users.set(index, user);
        return user;
    }

    @Override
    public void deleteUser(String userID) {
        UserEntity stored = getUserById(userID);
        if (stored.getDeleted())
            confirmDeleteUser(userID);
        else {
            stored.setDeleted(true);
            editUser(stored);
        }
    }

    @Override
    public void deleteUsers(List<String> userIDs) {
        userIDs.forEach(this::deleteUser);
    }

    @Override
    public void confirmDeleteUser(String userID) {
        users.removeIf(user -> user.getId().equals(userID) && user.getDeleted());
    }

    @Override
    public void confirmDeleteUsers(List<String> userIDs) {
        userIDs.forEach(this::confirmDeleteUser);
    }
}
