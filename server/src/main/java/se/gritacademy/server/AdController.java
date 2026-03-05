package se.gritacademy.server;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdRepository repo = new AdRepository();

    // 1. Lista annonser
    @GetMapping
    public List<Ad> getAllAds() {
        return repo.findAll();
    }

    // 2. Visa en annons
    @GetMapping("/{id}")
    public Ad getAd(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    // 3. Skapa annons
    @PostMapping
    public Ad createAd(@RequestBody Ad ad) {
        return repo.create(ad);
    }

    // 4. Ändra pris
    @PutMapping("/{id}")
    public Ad updatePrice(@PathVariable Long id, @RequestBody Ad ad) {
        return repo.updatePrice(id, ad.getPrice()).orElse(null);
    }

    // 5. Radera annons
    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable Long id) {
        repo.delete(id);
    }
}