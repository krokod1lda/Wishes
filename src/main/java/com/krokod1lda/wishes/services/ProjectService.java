package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.models.PhoneNumber;
import com.krokod1lda.wishes.models.Project;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.PhoneNumberRepository;
import com.krokod1lda.wishes.repositories.ProjectRepository;
import com.krokod1lda.wishes.repositories.WantyRepository;
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
    @Autowired
    WantyRepository wantyRepository;
    @Autowired
    private WantyService wantyService;

    public void createProject(String name, String[] phones) {
        String[] phonesArray = deleteEmptyLines(phones); // Сделаем так, чтобы в массиве не было пустых строк

        Project project = new Project(name); // Добавили наименование проекта
        projectRepository.save(project);

        for (String el : phonesArray) { // Добавили номера телефонов
            PhoneNumber phoneNumber = new PhoneNumber(project.getId(), el);
            phoneNumberRepository.save(phoneNumber);
        }
    }

    public String getProjectName(long id) {
        Project project = projectRepository.findById(id).orElseThrow();

        return project.getName();
    }

    public Project getProject(long id) {

        return projectRepository.findById(id).orElseThrow();
    }

    public Iterable<Project> getAllTheProjects() {

        return projectRepository.findAll();
    }

    public static <E> List<E> iterableToList(Iterable<E> iterable) {
        List<E> list = new ArrayList<>();

        for (E element : iterable) {
            list.add(element);
        }

        return list;
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

        return arrStr.split(", ");
    }

    public List<String> getPhonesListByPNObject(long id) {
        List <String> phonesList = new ArrayList<String>();
        List<PhoneNumber> phonesListObjects = phoneNumberRepository.findByProjectId(id);

        for (PhoneNumber el : phonesListObjects) {
            phonesList.add(el.getPhoneNumber());
        }

        return phonesList;
    }

    public void archiveProject(long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.setArchived(true);
        projectRepository.save(project);
    }

    public void unarchiveProject(long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.setArchived(false);
        projectRepository.save(project);
    }

    public void deletePhoneNumber(long id) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(id).orElseThrow();
        phoneNumberRepository.delete(phoneNumber);
    }

    public List<Project> deleteCurrentProject(Iterable<Project> projects, Project curProject) {
        List<Project> projectsWithoutCur = iterableToList(projects);
        projectsWithoutCur.remove(curProject);

        return projectsWithoutCur;
    }

    public void deleteProject(long id) {
        ArrayList<Wanty> wanties = wantyRepository.findByProjectId(id);

        for (Wanty el : wanties) {
            wantyService.deleteWanty(el.getId());
        }

        phoneNumberRepository.deletePhoneNumbersByProjectId(id);
        projectRepository.deleteById(id);
    }
}
