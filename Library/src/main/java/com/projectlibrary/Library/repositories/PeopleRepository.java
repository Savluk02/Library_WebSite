package com.projectlibrary.Library.repositories;

import com.projectlibrary.Library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, String> {

    Optional<Person> findByUsername(String username);
    Optional<Person> getReferenceById(int id);
    
    
}
