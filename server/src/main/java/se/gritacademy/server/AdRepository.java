package se.gritacademy.server;

import java.util.*;

public class AdRepository {

    private final Map<Long, Ad> ads = new LinkedHashMap<>();
    private long nextId = 1;

    // Hämta alla annonser
    public List<Ad> findAll() {
        return new ArrayList<>(ads.values());
    }

    // Hämta annons via id
    public Optional<Ad> findById(Long id) {
        return Optional.ofNullable(ads.get(id));
    }

    // Skapa ny annons
    public Ad create(Ad ad) {
        ad.setId(nextId++);
        ads.put(ad.getId(), ad);
        return ad;
    }

    // Uppdatera pris
    public Optional<Ad> updatePrice(Long id, double newPrice) {
        Ad existing = ads.get(id);
        if (existing == null) {
            return Optional.empty();
        }

        existing.setPrice(newPrice);
        return Optional.of(existing);
    }

    // Ta bort annons
    public boolean delete(Long id) {
        return ads.remove(id) != null;
    }
}