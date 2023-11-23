package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.PhoneNumber;
import com.krokod1lda.wishes.models.Project;
import com.krokod1lda.wishes.repositories.PhoneNumberRepository;
import com.krokod1lda.wishes.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    PhoneNumberRepository phoneNumberRepository;
    public void createProject(String name, String[] phones) {


//        Сделаем так, чтобы в массиве не было пустых строк
        String[] phonesArray = deleteEmptyLines(phones);

        Project project = new Project(name); // Добавили наименование проекта
        projectRepository.save(project);

        for (String el : phonesArray) { // Добавили номера телефонов
            PhoneNumber phoneNumber = new PhoneNumber(project.getId(), el);
            phoneNumberRepository.save(phoneNumber);
        }

    }
    public Iterable<Project> getAllTheProjects() {

        Iterable<Project> projects = projectRepository.findAll();
        return projects;
    }
    public ArrayList<Project> updateProject(long id) {
        Optional<Project> project = projectRepository.findById(id);
        ArrayList<Project> res = new ArrayList<>();
        project.ifPresent(res::add);

        return res;
    }

    public void updateProject(long id, String name, String[] phones) {
        Project project = projectRepository.findById(id).orElseThrow();

        project.setName(name);
        String[] phonesArray = deleteEmptyLines(phones); // Массив с номерами, который подтягивается
        List <String> phonesList = getPhonesListByPNObject(id); // Список с номерами рассматриваемого объекта

        for (String el : phonesArray) {
            if (!phonesList.contains(el)) {
                PhoneNumber phoneNumber = new PhoneNumber(id, el);
                phoneNumberRepository.save(phoneNumber);
            }
        }
        projectRepository.save(project);
    }
    public List<PhoneNumber> getPhoneNumberByProjectId(long projectId) {

        return phoneNumberRepository.findByProjectId(projectId);
    }
    public String[] deleteEmptyLines(String[] array) {

        String arrStr = Arrays.stream(array)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(", "));
        String[] resArray = arrStr.split(", ");

        return resArray;
    }

    public List<String> getPhonesListByPNObject(long id) {
        List <String> phonesList = new ArrayList<String>();
        List<PhoneNumber> phonesListObjects = phoneNumberRepository.findByProjectId(id);
        for (PhoneNumber el : phonesListObjects) {
            phonesList.add(el.getPhoneNumber());
        }

        return phonesList;
    }
    public void deletePhoneNumber(long id) {

        PhoneNumber phoneNumber = phoneNumberRepository.findById(id).orElseThrow();
        phoneNumberRepository.delete(phoneNumber);
    }

    public void deleteProject(long id) {

        phoneNumberRepository.deletePhoneNumbersByProjectId(id);

        Project project = projectRepository.findById(id).orElseThrow();
        projectRepository.delete(project);
    }
}
