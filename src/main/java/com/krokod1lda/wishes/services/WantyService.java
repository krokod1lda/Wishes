package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.EntityAttributes.PersonAttributes;
import com.krokod1lda.wishes.structures.SoldInfo;
import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Project;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.PersonRepository;
import com.krokod1lda.wishes.repositories.ProjectRepository;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.sql.Date;

@Service
public class WantyService {
    @Autowired
    private WantyRepository wantyRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public LinkedHashMap<Wanty, String> getAllTheWanties() {
        ArrayList<Wanty> wanties = wantyRepository.findAllDesc();
        LinkedHashMap<Wanty, String> map = new LinkedHashMap<>();

        for(Wanty wanty : wanties)
            map.put(wanty, getPhotoBase64(wanty.getWantyPhoto()));

        return map;
    }

    public void addWanty(String name, Date date, String size, long sellerId, long buyerId,
                         long clientId, long projectId, boolean isPurchased, String description, MultipartFile wantyPhoto) {

        Wanty wanty = new Wanty(name, date, size, sellerId, buyerId, clientId, projectId,
                isPurchased, description, wantyPhoto);

        wantyRepository.save(wanty);
        increaseWithStatistics(sellerId, buyerId, clientId, projectId, isPurchased);
    }

    public ArrayList<Wanty> getWanty(long wantyId) {
        Optional<Wanty> wanty = wantyRepository.findById(wantyId);
        ArrayList<Wanty> res = new ArrayList<>();
        wanty.ifPresent(res::add);

        return res;
    }

    public LinkedHashMap<Wanty, String> getWanty(String wantyName) {
        ArrayList<Wanty> wanties;
        wanties = wantyRepository.findByWantyName(wantyName);

        LinkedHashMap<Wanty, String> map = new LinkedHashMap<>();

        for(Wanty wanty : wanties)
            map.put(wanty, getPhotoBase64(wanty.getWantyPhoto()));

        return map;
    }

    public LinkedHashMap<Wanty, String> getWishesByBuyer(long personId) {
        ArrayList<Wanty> wanties;
        wanties = wantyRepository.findByBuyerId(personId);

        LinkedHashMap<Wanty, String> map = new LinkedHashMap<>();

        for(Wanty wanty : wanties)
            map.put(wanty, getPhotoBase64(wanty.getWantyPhoto()));

        return map;
    }

    public LinkedHashMap<Wanty, String> getWishesBySeller(long personId) {
        ArrayList<Wanty> wanties;
        wanties = wantyRepository.findBySellerId(personId);

        LinkedHashMap<Wanty, String> map = new LinkedHashMap<>();

        for(Wanty wanty : wanties)
            map.put(wanty, getPhotoBase64(wanty.getWantyPhoto()));

        return map;
    }

    public LinkedHashMap<Wanty, String> getWishesByClient(long personId) {
        ArrayList<Wanty> wanties;
        wanties = wantyRepository.findByClientId(personId);

        LinkedHashMap<Wanty, String> map = new LinkedHashMap<>();

        for(Wanty wanty : wanties)
            map.put(wanty, getPhotoBase64(wanty.getWantyPhoto()));

        return map;
    }

    public void updateWanty(long wantyId, String name, Date date, String size, long sellerId,
                              long buyerId, long clientId, long projectId, boolean isPurchased, String description, MultipartFile wantyPhoto) {

        Wanty wanty = wantyRepository.findById(wantyId).orElseThrow();

        boolean isSellerChanged = false;
        boolean isBuyerChanged = false;
        boolean isClientChanged = false;
        boolean isProjectChanged = false;

        // if seller is changed
        if (wanty.getSellerId() != sellerId) {
            decrease(wanty.getSellerId(), wanty.isPurchased());
            increase(sellerId, isPurchased);
            isSellerChanged = true;
        }

        // if buyer is changed
        if (wanty.getBuyerId() != buyerId) {
            decrease(wanty.getBuyerId(), wanty.isPurchased());
            increase(buyerId, isPurchased);
            isBuyerChanged = true;
        }

        // if client is changed
        if (wanty.getClientId() != clientId) {
            decrease(wanty.getClientId(), wanty.isPurchased());
            increase(clientId, isPurchased);
            isClientChanged = true;
        }

        // if project is changed
        if (wanty.getProjectId() != projectId) {
            decreaseProject(wanty.getProjectId(), wanty.isPurchased());
            increaseProject(projectId, isPurchased);
            isProjectChanged = true;
        }

        // if seller isn't changed but purchase status is
        if (!isSellerChanged && wanty.isPurchased() != isPurchased)
            changePurchased(sellerId, isPurchased);

        // if buyer isn't changed but purchase status is
        if (!isBuyerChanged && wanty.isPurchased() != isPurchased)
            changePurchased(buyerId, isPurchased);

        // if client isn't changed but purchase status is
        if (!isClientChanged && wanty.isPurchased() != isPurchased)
            changePurchased(clientId, isPurchased);

        // if project isn't changed but purchase status is
        if (!isProjectChanged && wanty.isPurchased() != isPurchased)
            changePurchasedProject(projectId, isPurchased);

        wanty.update(name, date, size, sellerId, buyerId,
                clientId, projectId, isPurchased, description, wantyPhoto);

        wantyRepository.save(wanty);
    }

    public void deleteWanty(long wantyId) {
        Wanty wanty = wantyRepository.findById(wantyId).orElseThrow();
        Person person;
        Project project;

        person = personRepository.findById(wanty.getSellerId()).orElseThrow();
        person.decrease(wanty.isPurchased());

        person = personRepository.findById(wanty.getBuyerId()).orElseThrow();
        person.decrease(wanty.isPurchased());

        person = personRepository.findById(wanty.getClientId()).orElseThrow();
        person.decrease(wanty.isPurchased());

        project = projectRepository.findById(wanty.getProjectId()).orElseThrow();
        project.decrease(wanty.isPurchased());

        wantyRepository.delete(wanty);
    }

    public ArrayList<HashMap<String, SoldInfo>> getStatistics(Date date1, Date date2) {
        ArrayList<Wanty> wanties = wantyRepository.getEntriesByDates(date1, date2);

        HashMap<Long, SoldInfo> sellerSoldInfoHashMap = new HashMap<>();
        HashMap<Long, SoldInfo> buyerSoldInfoHashMap = new HashMap<>();
        HashMap<Long, SoldInfo> clientSoldInfoHashMap = new HashMap<>();
        HashMap<Long, SoldInfo> projectSoldInfoHashMap = new HashMap<>();

        SoldInfo soldInfo;

        for (Wanty wanty : wanties) {
            // for sellers
            soldInfo = sellerSoldInfoHashMap.get(wanty.getSellerId());

            if (soldInfo != null)
                soldInfo.update(1, wanty.isPurchased() ? 1 : 0);
            else
                soldInfo = new SoldInfo(1, wanty.isPurchased() ? 1 : 0);
            sellerSoldInfoHashMap.put(wanty.getSellerId(), soldInfo);

            // for buyers
            soldInfo = buyerSoldInfoHashMap.get(wanty.getBuyerId());

            if (soldInfo != null)
                soldInfo.update(1, wanty.isPurchased() ? 1 : 0);
            else
                soldInfo = new SoldInfo(1, wanty.isPurchased() ? 1 : 0);
            buyerSoldInfoHashMap.put(wanty.getBuyerId(), soldInfo);

            // for clients
            soldInfo = clientSoldInfoHashMap.get(wanty.getClientId());

            if (soldInfo != null)
                soldInfo.update(1, wanty.isPurchased() ? 1 : 0);
            else
                soldInfo = new SoldInfo(1, wanty.isPurchased() ? 1 : 0);
            clientSoldInfoHashMap.put(wanty.getClientId(), soldInfo);

            // for projects
            soldInfo = projectSoldInfoHashMap.get(wanty.getProjectId());

            if (soldInfo != null)
                soldInfo.update(1, wanty.isPurchased() ? 1 : 0);
            else
                soldInfo = new SoldInfo(1, wanty.isPurchased() ? 1 : 0);
            projectSoldInfoHashMap.put(wanty.getProjectId(), soldInfo);
        }

        ArrayList<HashMap<String, SoldInfo>> result = new ArrayList<>();

        result.add(getResult(sellerSoldInfoHashMap));
        result.add(getResult(buyerSoldInfoHashMap));
        result.add(getResult(clientSoldInfoHashMap));
        result.add(getResultProject(projectSoldInfoHashMap));

        return result;
    }

    public ArrayList<HashMap<String, SoldInfo>> getStartStatistics() {
        Iterable<Person> people = personRepository.findAll();
        Iterable<Project> projects = projectRepository.findAll();

        ArrayList<HashMap<String, SoldInfo>> result = new ArrayList<>();

        HashMap<String, SoldInfo> sellerSoldInfoHashMap = new HashMap<>();
        HashMap<String, SoldInfo> buyerSoldInfoHashMap = new HashMap<>();
        HashMap<String, SoldInfo> clientSoldInfoHashMap = new HashMap<>();
        HashMap<String, SoldInfo> projectSoldInfoHashMap = new HashMap<>();

        SoldInfo soldInfo;

        for (Person person : people) {
            soldInfo = new SoldInfo(person.getTotally(), person.getPurchased());

            if(person.getType().equals(PersonAttributes.TYPE_SELLER.getValue()))
                sellerSoldInfoHashMap.put(person.getName() + " " + person.getSurname(), soldInfo);
            else if(person.getType().equals(PersonAttributes.TYPE_BUYER.getValue()))
                buyerSoldInfoHashMap.put(person.getName() + " " + person.getSurname(), soldInfo);
            else
                clientSoldInfoHashMap.put(person.getName() + " " + person.getSurname(), soldInfo);
        }

        for (Project project : projects) {
            soldInfo = new SoldInfo(project.getTotally(), project.getPurchased());
            projectSoldInfoHashMap.put(project.getName(), soldInfo);
        }

        result.add(sellerSoldInfoHashMap);
        result.add(buyerSoldInfoHashMap);
        result.add(clientSoldInfoHashMap);
        result.add(projectSoldInfoHashMap);

        return result;
    }

    private HashMap<String, SoldInfo> getResult(HashMap<Long, SoldInfo> soldInfoHashMap) {
        HashMap<String, SoldInfo> result = new HashMap<>();

        for (Long id : soldInfoHashMap.keySet())
            result.put(personRepository.findById(id).orElseThrow().getName() +
                    " " + personRepository.findById(id).orElseThrow().getSurname(),
                    soldInfoHashMap.get(id));

        return result;
    }

    private HashMap<String, SoldInfo> getResultProject(HashMap<Long, SoldInfo> soldInfoHashMap) {
        HashMap<String, SoldInfo> result = new HashMap<>();

        for (Long id : soldInfoHashMap.keySet())
            result.put(projectRepository.findById(id).orElseThrow().getName(),
                    soldInfoHashMap.get(id));

        return result;
    }

    public String getPhotoBase64(byte[] bytePhoto) {

        return photoByteToBase64(bytePhoto);
    }

    private String photoByteToBase64(byte[] bytePhoto) {
        String wantyPhoto = null;

        if (bytePhoto != null) {
            byte[] encodeBase64 = Base64.getEncoder().encode(bytePhoto);
            wantyPhoto = new String(encodeBase64, StandardCharsets.UTF_8);
        }

        return wantyPhoto;
    }

    private void increaseWithStatistics(long sellerId, long buyerId, long clientId, long projectId, boolean isPurchased) {
        increase(sellerId, isPurchased);
        increase(buyerId, isPurchased);
        increase(clientId, isPurchased);
        increaseProject(projectId, isPurchased);
    }

    private void increase(long id, boolean isPurchased) {
        Person person = personRepository.findById(id).orElseThrow();
        person.increase(isPurchased);
        personRepository.save(person);
    }

    private void increaseProject(long id, boolean isPurchased) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.increase(isPurchased);
        projectRepository.save(project);
    }

    private void decrease(long id, boolean isPurchased) {
        Person person = personRepository.findById(id).orElseThrow();
        person.decrease(isPurchased);
        personRepository.save(person);
    }

    private void decreaseProject(long id, boolean isPurchased) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.decrease(isPurchased);
        projectRepository.save(project);
    }

    private void changePurchased(long id, boolean isPurchased) {
        Person person;
        person = personRepository.findById(id).orElseThrow();

        if (isPurchased)
            person.increasePurchased();
        else
            person.decreasePurchased();

        personRepository.save(person);
    }

    private void changePurchasedProject(long id, boolean isPurchased) {
        Project project;
        project = projectRepository.findById(id).orElseThrow();

        if (isPurchased)
            project.increasePurchased();
        else
            project.decreasePurchased();

        projectRepository.save(project);
    }
}