package br.com.portalCliente.entity.user;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.enumeration.Profile;
import br.com.portalCliente.exception.PortalClienteException;
import br.com.portalCliente.security.Crypt;
import br.com.portalCliente.util.JpaUtils;
import br.com.portalCliente.util.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.logging.Logger;

@Configurable
public abstract class UserAR extends JpaUtils {

    @PersistenceContext
    transient EntityManager entityManager;

    @Autowired

    public static final EntityManager entityManager() {
        EntityManager em = new User().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        }
        return em;
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.persist(this);
    }

    /**
     * Este método altera os dados de um Usuário
     *
     * @return
     */
    @Transactional
    public User edit() {
        User user = (User) this;
        user.setUpdateAt(new Date());
        return user.merge();
    }

    /**
     * Este método elimina a relação entre usuário e Corretora
     *
     * @return
     */
    public static void deleteUser(int id) {
        User user = User.find(id);
        user.remove();
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            User attached = (User) this;
            attached = find(attached.getId());
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.clear();
    }

    @Transactional
    public User merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        User merged = (User) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static User find(int id) {
        return entityManager().find(User.class, id);
    }

    /**
     * Este método recupera o Usuário a partir do e-mail enviado
     * para que seja realizada uma autenticação
     *
     * @param login
     * @return
     */
    public static User findByLogin(String login) {
        String sql = "Select u From User u WHERE u.login = :login";

        TypedQuery<User> query = entityManager()
                .createQuery(sql, User.class)
                .setParameter("login", login);
        try {
            return query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(UserAR.class.getName()).warning("Erro" + e.getMessage());
            return null;
        }
    }

    /**
     * Este método lista os Usuários de acordo com os valores
     * passados por parâmetro
     *
     * @param name
     * @param login
     * @param profile
     * @param firstResult
     * @param maxResults
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<User> search(String name, String login, Profile profile, int brokerage, int firstResult, int maxResults) {

        StringBuilder sql = new StringBuilder("SELECT u FROM User u");
        Map<String, Object> params = new HashMap<String, Object>();

        if (name != null && !name.isEmpty()) {
            validateSql(sql, "name LIKE :name");
            params.put("name", "%"+name+"%");
        }

        if (login != null && !login.isEmpty()) {
            validateSql(sql, "login LIKE :login ");
            params.put("login", "%"+login+"%");
        }

        if (profile != null) {
            validateSql(sql, "profile = :profile");
            params.put("profile", profile.getValue());
        }

        sql.append(" ORDER BY u.name");

        try {

            TypedQuery<User> query = entityManager().createQuery(sql.toString(), User.class)
                    .setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                    .setMaxResults(maxResults);
            setParams(params, query);

            return query.getResultList();

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).warning("Erro" + e.getMessage());
            return null;
        }
    }

    /**
     * Este método retorna a quantidade de dados existentes no BD
     * de acordo com o parâmetro informado
     *
     * @param name
     * @param login
     * @param profile
     * @return
     */
    public static long count(String name, String login, Profile profile, int brokerage) {

        try {

            StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT u) FROM User u");
            Map<String, Object> params = new HashMap<String, Object>();

            if (name != null && !name.isEmpty()) {
                validateSql(sql, "name LIKE :name");
                params.put("name", "%"+name+"%");
            }

            if (login != null && !login.isEmpty()) {
                validateSql(sql, "login LIKE :login ");
                params.put("login", "%"+login+"%");
            }

            if (profile != null) {
                validateSql(sql, "profile = :profile");
                params.put("profile", profile.getValue());
            }

            TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
            setParams(params, query);

            return query.getSingleResult();

        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).warning("Erro aqui: " + e.getMessage());
            return 0;
        }

    }

    @SuppressWarnings("unchecked")
    public static List<User> autoComplete(String value, Profile profile, int firstResult, int maxResults, Integer... brokerage) {

        StringBuilder sql = new StringBuilder("SELECT u FROM User u");
        params = new HashMap<String, Object>();

        if (brokerage != null) {
            validateSql(sql, "u.id IN (SELECT ub.user.id FROM UserBrokerage ub WHERE ub.brokerage.id IN :brokerage)");
            List<Integer> ids = Arrays.asList(brokerage);
            params.put("brokerage", ids);
        }

        if (profile != null) {
            validateSql(sql, "profile = :profile ");
            params.put("profile", profile.getValue());
        } else {
            validateSql(sql, "profile <> :profile ");
            params.put("profile", Profile.ROLE_ADMIN.getValue());
        }


        if (value != null && !value.isEmpty()) {
            validateSql(sql, "(u.name LIKE :value OR u.login LIKE :value)");
            params.put("value", "%" + value + "%");
        }

        sql.append(" ORDER BY u.name");
        List<User> result = new ArrayList<User>();
        try {

            TypedQuery<User> query = entityManager().createQuery(sql.toString(), User.class);
            setParams(params, query);
            query.setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                    .setMaxResults(maxResults);
            result = query.getResultList();

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).warning("Erro" + e.getMessage());
            return null;
        }

        return result;
    }

    /**
     * Este método é responsável por validar se o usuário já existe nos sistema.
     * Se não existe, cria um novo;
     * Se já existe, lançamos uma exceção
     * s
     *
     * @param json
     * @throws PortalClienteException
     */
    public static User save(String json) throws PortalClienteException {

        User user = User.fromJson(json);
        User userValidate = User.findByLogin(user.getLogin());

        if (userValidate != null) {
            throw new PortalClienteException("Usuário já cadastrado com este e-mail");
        }

        // Senão, ele cadastra
        else {
            // Instancia-se esta estidade, pois a mesma usa o envio de e-mails

            user.setPassword(Crypt.encrypt(user.getPassword()));
            user.setCreatedAt(new Date());
            user.persist();
        }

        return user;
    }
}