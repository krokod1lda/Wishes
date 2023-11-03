package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.SoldInfo;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.PersonRepository;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class WantyService {
    @Autowired
    private WantyRepository wantyRepository;
    @Autowired
    private PersonRepository personRepository;

    public Iterable<Wanty> getAllTheWanties () {

        return wantyRepository.findAll();
    }

    public void addWanty (String name, Date date, String size, long sellerId,
                          long buyerId, long clientId, boolean isPurchased, String description) {

        Wanty wanty = new Wanty(name, date, size, sellerId, buyerId, clientId,
                isPurchased, description, null);

        wantyRepository.save(wanty);
    }

    public ArrayList<Wanty> getWanty (long wantyId) {
        Optional<Wanty> wanty = wantyRepository.findById(wantyId);
        ArrayList<Wanty> res = new ArrayList<>();
        wanty.ifPresent(res::add);

        return res;
    }

    public ArrayList<Wanty> getWanty (String wantyName) {

        return wantyRepository.findByWantyName(wantyName);
    }

    public List<Wanty> getWishesByBuyer (long personId) {

        return wantyRepository.findByBuyerId(personId);
    }

    public List<Wanty> getWishesBySeller (long personId) {

        return wantyRepository.findBySellerId(personId);
    }

    public List<Wanty> getWishesByClient (long personId) {

        return wantyRepository.findByClientId(personId);
    }

    public void updateWanty (long wantyId, String name, Date date, String size, long sellerId,
                              long buyerId, long clientId, boolean isPurchased, String description) {

        Wanty wanty = wantyRepository.findById(wantyId).orElseThrow();

        wanty.update(name, date, size, sellerId, buyerId,
                clientId, isPurchased, description, null);

        wantyRepository.save(wanty);
    }

    public void deleteWanty (long wantyId) {
        Wanty wanty = wantyRepository.findById(wantyId).orElseThrow();

        wantyRepository.delete(wanty);
    }

    public ArrayList<HashMap<String, SoldInfo>> getStatistics(Date date1, Date date2) {
        ArrayList<Wanty> wanties = wantyRepository.getEntriesByDates(date1, date2);

        HashMap<Long, SoldInfo> sellerSoldInfoHashMap = new HashMap<>();
        HashMap<Long, SoldInfo> clientSoldInfoHashMap = new HashMap<>();
        SoldInfo soldInfo;

        for (Wanty wanty : wanties) {
            // for sellers
            soldInfo = sellerSoldInfoHashMap.get(wanty.getSellerId());

            if (soldInfo != null)
                soldInfo.update(1, wanty.isPurchased() ? 1 : 0);
            else
                soldInfo = new SoldInfo(1, wanty.isPurchased() ? 1 : 0);

            sellerSoldInfoHashMap.put(wanty.getSellerId(), soldInfo);

            // for clients
            soldInfo = clientSoldInfoHashMap.get(wanty.getClientId());

            if (soldInfo != null)
                soldInfo.update(1, wanty.isPurchased() ? 1 : 0);
            else
                soldInfo = new SoldInfo(1, wanty.isPurchased() ? 1 : 0);

            clientSoldInfoHashMap.put(wanty.getClientId(), soldInfo);
        }

        ArrayList<HashMap<String, SoldInfo>> result = new ArrayList<>();

        result.add(getResult(sellerSoldInfoHashMap));
        result.add(getResult(clientSoldInfoHashMap));

        return result;
    }

    HashMap<String, SoldInfo> getResult(HashMap<Long, SoldInfo> soldInfoHashMap) {
        HashMap<String, SoldInfo> result = new HashMap<>();

        for (Long id : soldInfoHashMap.keySet())
            result.put(personRepository.findById(id).orElseThrow().getName() +
                    " " + personRepository.findById(id).orElseThrow().getSurname(),
                    soldInfoHashMap.get(id));

        return result;
    }
}