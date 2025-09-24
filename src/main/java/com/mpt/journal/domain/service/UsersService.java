package com.mpt.journal.domain.service;


import com.mpt.journal.domain.model.PagedResult;
import com.mpt.journal.domain.model.UserModel;

import java.util.List;

public interface UsersService {
    PagedResult<UserModel> getUsers(
            int page,
            int pageSize,
            String nickName,
            Integer minRecipesCount,
            Integer maxRecipesCount,
            Boolean showDeleted
    );

    UserModel getUserByLogin(String login);

    UserModel getUserById(String userID);

    UserModel addUser(UserModel user);

    UserModel editUser(UserModel user);

    void deleteUser(String userID);

    void deleteUsers(List<String> userIDs);

    void confirmDeleteUser(String userID);

    void confirmDeleteUsers(List<String> userIDs);
}
