package com.projectlibrary.Library.dao;

import com.projectlibrary.Library.models.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Person getPersonById(int id){
        return entityManager.find(Person.class, id);
    }
}
