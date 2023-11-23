package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.EntityAttributes.ProjectAttributes;
import com.krokod1lda.wishes.models.Project;
import com.krokod1lda.wishes.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/add-project")
    public String addProject(Model model) {

        model.addAttribute(ProjectAttributes.TITLE.getValue(), ProjectAttributes.ADDING_PROJECT.getValue());
        return "add-project";
    }
    @PostMapping("/addProject")
    public String addProject(@RequestParam("name") String name, @RequestParam("phones") String[] phones) {

        projectService.createProject(name, phones);
        return "redirect:/";
    }
    @GetMapping("/all-projects")
    public String allProjects(Model model) {

        model.addAttribute(ProjectAttributes.PROJECTS.getValue(), projectService.getAllTheProjects());
        model.addAttribute(ProjectAttributes.TITLE.getValue(), ProjectAttributes.ALL_PROJECTS.getValue());
        return "all-projects";
    }

    @GetMapping("/project/{id}")
    public String updateProject(@PathVariable("id") long id, Model model) {

        model.addAttribute(ProjectAttributes.PROJECT.getValue(), projectService.updateProject(id));
        model.addAttribute(ProjectAttributes.PHONE_NUMBERS.getValue(), projectService.getPhoneNumberByProjectId(id));
        model.addAttribute(ProjectAttributes.TITLE.getValue(), ProjectAttributes.EDIT_PROJECT.getValue());
        return "edit-project";
    }
    @PostMapping("/project/{id}")
    public String updateProject(@PathVariable("id") long id, @RequestParam("name") String name,
                                @RequestParam("phones") String[] phones) {

        projectService.updateProject(id, name, phones);
        return "redirect:/";
    }

    @PostMapping("/deletePhoneNumber{id}")
    public String deletePhoneNumber(@RequestParam("phoneNumberId") long phoneNumberId) {

        projectService.deletePhoneNumber(phoneNumberId);
        return "redirect:/all-projects";
    }
    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("id") long id) {

        projectService.deleteProject(id);
        return "redirect:/all-projects";
    }
}
