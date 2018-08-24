package com.npci.shirotutorial.security.service;

import com.npci.shirotutorial.security.model.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RoleService {

    @PersistenceContext
    EntityManager em;

    public void save(Role role) {
        em.persist(role);
    }

    public void delete(Role role) {
        try {
            em.remove(role);
        } catch (EntityNotFoundException e) {
            // It doesn't exist already
        }
    }

    public void delete(Long id) {
        Role role = em.getReference(Role.class, id);
        delete(role);
    }

    public Role find(Long id) {
        return em.find(Role.class, id);
    }

    public List<Role> findAll() {
        return em.createNamedQuery(Role.FIND_ALL, Role.class).getResultList();
    }
}
