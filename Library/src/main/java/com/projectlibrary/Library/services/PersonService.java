package com.projectlibrary.Library.services;

import com.projectlibrary.Library.dao.PersonDao;
import com.projectlibrary.Library.models.Person;
import com.projectlibrary.Library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PeopleRepository peopleRepository;
    private final PersonDao personDao;

    @Autowired
    public PersonService(PeopleRepository peopleRepository, PersonDao personDao) {
        this.peopleRepository = peopleRepository;
        this.personDao = personDao;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }


    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.getReferenceById(id);
        return foundPerson.orElse(null);
    }
}
