package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Wanty;
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

    public List<Person> getPersonAsList(long id) {
        Optional<Person> person = personRepository.findById(id);
        List<Person> personList = new ArrayList<>();
        person.ifPresent(personList::add);

        return personList;
    }

    public Map<String, List<Person>> getPeopleMapForAllPersonsPage() {
        List<Person> sellers = personRepository.findByType("seller");
        List<Person> buyers = personRepository.findByType("buyer");
        List<Person> clients = personRepository.findByType("client");

        Map<String, List<Person>> map = new LinkedHashMap<>();

        map.put("Продавцы", sellers);
        map.put("Байеры", buyers);
        map.put("Клиенты", clients);

        if (sellers.isEmpty()) {
            map.remove("Продавцы");
        }
        if (buyers.isEmpty()) {
            map.remove("Байеры");
        }
        if (clients.isEmpty()) {
            map.remove("Клиенты");
        }

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
        List<Person> sellers = personRepository.findByType("seller");
        List<Person> buyers = personRepository.findByType("buyer");
        List<Person> clients = personRepository.findByType("client");

        Map<String, List<Person>> people = new HashMap<>();

        people.put("sellers", sellers);
        people.put("buyers", buyers);
        people.put("clients", clients);

        return people;
    }

    // returns map with people who have certain state of isArchived variable
    public static Map<String, List<Person>> getAllThePeopleWithArchived(PersonRepository personRepository, boolean archived) {
        List<Person> sellers = personRepository.findByTypeAndArchived("seller", archived);
        List<Person> buyers = personRepository.findByTypeAndArchived("buyer", archived);
        List<Person> clients = personRepository.findByTypeAndArchived("client", archived);

        Map<String, List<Person>> people = new HashMap<>();

        people.put("sellers", sellers);
        people.put("buyers", buyers);
        people.put("clients", clients);

        return people;
    }

    public Map<String, List<Person>> removeCurrents (Person curSeller, Person curBuyer, Person curClient) {
        Map<String, List<Person>> people = getAllThePeople();
        people.get("sellers").remove(curSeller);
        people.get("buyers").remove(curBuyer);
        people.get("clients").remove(curClient);

        return people;
    }
}