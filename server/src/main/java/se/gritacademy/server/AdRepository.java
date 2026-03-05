package se.gritacademy.server;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AdRepository {

    private final Map<Long, Ad> ads = new HashMap<>();
    private long currentId = 1;

    public List<Ad> findAll() {
        return new ArrayList<>(ads.values());
    }

    public Ad findById(long id) {
        return ads.get(id);
    }

    public Ad save(Ad ad) {
        ad.setId(currentId++);
        ads.put(ad.getId(), ad);
        return ad;
    }

    public Ad updatePrice(long id, double price) {
        Ad ad = ads.get(id);
        if (ad != null) {
            ad.setPrice(price);
        }
        return ad;
    }

    public boolean delete(long id) {
        return ads.remove(id) != null;
    }
}