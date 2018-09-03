package com.npci.shirotutorial.security.service;

import com.npci.shirotutorial.security.model.entity.User;
import com.npci.shirotutorial.security.service.exception.InvalidIdException;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class UserService {

    @Inject
    EntityManager em;

    @Inject
    private Logger logger;

    public void save(User user) {
        em.persist(user);
    }

    public User edit(User user) {
        if (user.getId() == null)
            throw new InvalidIdException(user.getClass().getName() + " not contain id when merge operation occurred");

        if (em.contains(user)) {
            return user;
        } else {
            return em.merge(user);
        }
    }

    public void delete(User user) {
        try {
            em.remove(user);
        } catch (EntityNotFoundException e) {
            // It doesn't exist already
        }
    }

    public void delete(Long id) {
        User user = em.getReference(User.class, id);
        delete(user);
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        User result = null;
        try {
            result = this.em.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            logger.info("UserService : No valid User was found for [" + username + "] : " + e);
        } finally {
            return result;
        }
    }

    public List<User> findAll() {
        return em.createNamedQuery(User.FIND_ALL, User.class).getResultList();
    }
}
