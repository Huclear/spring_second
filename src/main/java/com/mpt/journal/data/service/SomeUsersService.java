package com.mpt.journal.data.service;

import com.mpt.journal.data.Paginator;
import com.mpt.journal.domain.entity.UserEntity;
import com.mpt.journal.domain.model.PagedResult;
import com.mpt.journal.domain.model.UserModel;
import com.mpt.journal.domain.repository.RecipesRepository;
import com.mpt.journal.domain.repository.UsersRepository;
import com.mpt.journal.domain.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SomeUsersService implements UsersService {
    private final UsersRepository _users;
    private final RecipesRepository _recipes;

    public SomeUsersService(UsersRepository users, RecipesRepository recipes) {
        this._users = users;
        this._recipes = recipes;
    }

    @Override
    public PagedResult<UserModel> getUsers(
            int page,
            int pageSize,
            String nickName,
            Integer minRecipesCount,
            Integer maxRecipesCount,
            Boolean showDeleted
    ) {
        var users = _users.getUsers()
                .stream()
                .filter(u -> {
                            var recipesCount = _recipes.getRecipesByUser(u.getId()).size();
                            return
                                    (minRecipesCount == null || recipesCount >= minRecipesCount)
                                            && (maxRecipesCount == null || recipesCount <= maxRecipesCount)
                                            && (nickName == null || u.getNickname().contains(nickName))
                                            && u.getDeleted() == (showDeleted == null ? false : showDeleted);
                        }
                ).toList();

        return Paginator.paginate(users, page, pageSize)
                .map(this::getModelFromEntity);
    }

    @Override
    public UserModel getUserByLogin(String login) {
        return getModelFromEntity(_users.getUserByLogin(login));
    }

    @Override
    public UserModel getUserById(String userID) {
        return getModelFromEntity(_users.getUserById(userID));
    }

    @Override
    public UserModel addUser(UserModel user) {
        return getModelFromEntity(_users.addUser(convertModelToEntity(user)));
    }

    @Override
    public UserModel editUser(UserModel user) {
        return getModelFromEntity(_users.editUser(convertModelToEntity(user)));
    }

    @Override
    public void deleteUser(String userID) {
        _users.deleteUser(userID);
    }

    @Override
    public void deleteUsers(List<String> userIDs) {
        _users.deleteUsers(userIDs);
    }

    @Override
    public void confirmDeleteUser(String userID) {
        _users.confirmDeleteUser(userID);
    }

    @Override
    public void confirmDeleteUsers(List<String> userIDs) {
        _users.confirmDeleteUsers(userIDs);
    }

    private UserModel getModelFromEntity(UserEntity entity) {
        return entity == null ? null : new UserModel(
                entity.getId(),
                entity.getLogin(),
                entity.getNickname(),
                entity.getAboutMe(),
                entity.getPassword(),
                entity.getSalt()
        );
    }

    private UserEntity convertModelToEntity(UserModel model) {
        return new UserEntity(
                model.getId(),
                model.getLogin(),
                model.getNickname(),
                model.getAboutMe(),
                model.getPassword(),
                model.getSalt()
        );
    }
}
