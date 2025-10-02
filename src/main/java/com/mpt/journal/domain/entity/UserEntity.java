package com.mpt.journal.domain.entity;

import com.mpt.journal.domain.model.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login", "email_conf_id"}))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(min = 10)
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "[A-Za-z _]{5,}")
    private String nickname;

    private String aboutMe;

    @NotNull
    private Boolean deleted = false;

    @OneToOne(mappedBy = "user")
    private EmailConfirmationEntity emailConf;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<RecipeEntity> recipes;

    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roles;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public EmailConfirmationEntity getEmailConf() {
        return emailConf;
    }

    public void setEmailConf(EmailConfirmationEntity emailConf) {
        this.emailConf = emailConf;
    }

    public Collection<RecipeEntity> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<RecipeEntity> recipes) {
        this.recipes = recipes;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }
}
