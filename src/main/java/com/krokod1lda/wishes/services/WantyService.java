package com.krokod1lda.wishes.services;

import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WantyService {
    @Autowired
    private WantyRepository wantyRepository;

    public Iterable<Wanty> getAllTheWanties () {
        Iterable<Wanty> wanties = wantyRepository.findAll();

        return wanties;
    }

    public void addWanty (String name, String date, String size, long sellerId,
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
        ArrayList<Wanty> wishes = wantyRepository.findByWantyName(wantyName);

        return wishes;
    }

    public List<Wanty> getWishesByBuyer (long personId) {
        List<Wanty> wishes = wantyRepository.findByBuyerId(personId);

        return wishes;
    }

    public List<Wanty> getWishesBySeller (long personId) {
        List<Wanty> wishes = wantyRepository.findBySellerId(personId);

        return wishes;
    }

    public List<Wanty> getWishesByClient (long personId) {
        List<Wanty> wishes = wantyRepository.findByClientId(personId);

        return wishes;
    }

    public void updateWanty (long wantyId, String name, String date, String size, long sellerId,
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
}
