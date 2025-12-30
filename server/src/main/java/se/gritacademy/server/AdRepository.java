//commit 7
package se.gritacademy.server;

import java.util.*;

public class AdRepository {

    private final Map<Long, Ad> ads = new HashMap<>();
    private long idCounter = 1;

    public List<Ad> findAll() {
        return new ArrayList<>(ads.values());
    }

    public Ad findById(long id) {
        return ads.get(id);
    }

    public Ad save(Ad ad) {
        Ad newAd = new Ad(
                idCounter++,
                ad.getTitle(),
                ad.getSeller(),
                ad.getDescription(),
                ad.getPrice(),
                ad.getPinCode()
        );
        ads.put(newAd.getId(), newAd);
        return newAd;
    }

    public boolean delete(long id) {
        return ads.remove(id) != null;
    }
}
