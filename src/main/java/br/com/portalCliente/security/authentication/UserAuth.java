package br.com.portalCliente.security.authentication;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import br.com.portalCliente.entity.user.User;
import br.com.portalCliente.enumeration.Profile;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class UserAuth implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -6930642501496811192L;

    private Integer id;
    private String name;
    private String login;
    private String password;
    private Integer profile;
    private boolean status;
    private Date lastLogin;

    public UserAuth() {}

    public UserAuth(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.profile = user.getProfile().getValue();
        this.status = user.isStatus();
        this.password = user.getPassword();
        this.lastLogin = user.getLastLogin();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Profile getProfile() {
        return Profile.getName(profile);
    }

    public void setProfile(Profile profile) {
        this.profile = profile.getValue();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String getAuthority() {
        return Profile.getName(profile).toString();
    }

    public String toJson(String[] includedParam, String[] excludedParam) {
        return new JSONSerializer().exclude("*.class").include(includedParam).
                exclude(excludedParam).serialize(this);
    }

    public static UserAuth fromJson(String json) {
        return new JSONDeserializer<UserAuth>().use(null, UserAuth.class).deserialize(json);
    }

    public static String toJsonArray(Collection<UserAuth> collection, String[] includedParam, String[] excludedParam) {
        return new JSONSerializer().exclude("*.class").include(includedParam)
                .exclude(excludedParam).serialize(collection);
    }
}