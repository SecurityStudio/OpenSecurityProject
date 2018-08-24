package com.npci.shirotutorial.security.service;

import com.npci.shirotutorial.security.model.entity.User;
import com.npci.shirotutorial.security.service.exception.InvalidIdException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;

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

    public List<User> findAll() {
        return em.createNamedQuery(User.FIND_ALL, User.class).getResultList();
    }
}
