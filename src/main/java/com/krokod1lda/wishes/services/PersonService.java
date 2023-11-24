package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.EntityAttributes.PersonAttributes;
import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.repositories.PersonRepository;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WantyRepository wantyRepository;

    public void addPerson(String type, String name, String surname) {
        Person person = new Person(type, name, surname);
        personRepository.save(person);
    }
    public Person getPerson(long id) {

        return personRepository.findById(id).orElseThrow();
    }

    public Map<String, List<Person>> getPeopleMapForAllPersonsPage() {
        List<Person> sellers = personRepository.findByType(PersonAttributes.TYPE_SELLER.getValue());
        List<Person> buyers = personRepository.findByType(PersonAttributes.TYPE_BUYER.getValue());
        List<Person> clients = personRepository.findByType(PersonAttributes.TYPE_CLIENT.getValue());

        Map<String, List<Person>> map = new LinkedHashMap<>();

        map.put(PersonAttributes.KEY_SELLERS_RU.getValue(), sellers);
        map.put(PersonAttributes.KEY_BUYERS_RU.getValue(), buyers);
        map.put(PersonAttributes.KEY_CLIENTS_RU.getValue(), clients);

        if (sellers.isEmpty())
            map.remove(PersonAttributes.KEY_SELLERS_RU.getValue());

        if (buyers.isEmpty())
            map.remove(PersonAttributes.KEY_BUYERS_RU.getValue());

        if (clients.isEmpty())
            map.remove(PersonAttributes.KEY_CLIENTS_RU.getValue());

        return map;
    }

    // used in @GetMapping while editing personal info
    public ArrayList<Person> updatePerson (long id) {
        Optional<Person> person = personRepository.findById(id);
        ArrayList<Person> res = new ArrayList<>();
        person.ifPresent(res::add);

        return res;
    }

    // used in @PostMapping while edited personal info is being saved to the DB
    public void updatePerson (long id, String name, String surname) {
        Person person = personRepository.findById(id).orElseThrow();

        person.setName(name);
        person.setSurname(surname);

        personRepository.save(person);
    }

    public void archivePerson (long id) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setArchived(true);
        personRepository.save(person);
    }

    public void unarchivePerson (long id) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setArchived(false);
        personRepository.save(person);
    }

    public void deletePerson (long id) {
        wantyRepository.deleteAllTheWantiesByPersonId(id);
        personRepository.deleteById(id);
    }

    public String getPersonFullName (long personId) {
        Person person = personRepository.findById(personId).orElseThrow();

        return person.getSurname() + " " + person.getName();
    }

    // returns map with all the people
    public Map<String, List<Person>> getAllThePeople() {
        List<Person> sellers = personRepository.findByType(PersonAttributes.TYPE_SELLER.getValue());
        List<Person> buyers = personRepository.findByType(PersonAttributes.TYPE_BUYER.getValue());
        List<Person> clients = personRepository.findByType(PersonAttributes.TYPE_CLIENT.getValue());

        Map<String, List<Person>> people = new HashMap<>();

        people.put(PersonAttributes.KEY_SELLERS_EN.getValue(), sellers);
        people.put(PersonAttributes.KEY_BUYERS_EN.getValue(), buyers);
        people.put(PersonAttributes.KEY_CLIENTS_EN.getValue(), clients);

        return people;
    }

    public Map<String, List<Person>> removeCurrents (Person curSeller, Person curBuyer, Person curClient) {
        Map<String, List<Person>> people = getAllThePeople();
        people.get(PersonAttributes.KEY_SELLERS_EN.getValue()).remove(curSeller);
        people.get(PersonAttributes.KEY_BUYERS_EN.getValue()).remove(curBuyer);
        people.get(PersonAttributes.KEY_CLIENTS_EN.getValue()).remove(curClient);

        return people;
    }
}