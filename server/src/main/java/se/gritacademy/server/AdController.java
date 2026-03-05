package se.gritacademy.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdRepository repo;

    public AdController(AdRepository repo) {
        this.repo = repo;
    }

    // 1.1 Lista alla annonser
    @GetMapping
    public List<Ad> getAll() {
        return repo.findAll();
    }

    // 1.2 Visa en annons
    @GetMapping("/{id}")
    public ResponseEntity<Ad> getOne(@PathVariable long id) {
        Ad ad = repo.findById(id);
        if (ad == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(ad);
    }

    // 1.3 Skapa annons (JSON -> Ad)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ad> create(@RequestBody Ad ad) {

        // enkel validering (G-nivå)
        if (ad.getSubject() == null || ad.getSubject().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (ad.getSellerName() == null || ad.getSellerName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (ad.getSellerContact() == null || ad.getSellerContact().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (ad.getDescription() == null) {
            ad.setDescription("");
        }
        if (ad.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Ad saved = repo.save(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 1.4 Ändra pris (body = "600" eller "600.5")
    @PutMapping(value = "/{id}/price", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ad> updatePrice(@PathVariable long id, @RequestBody String body) {

        double newPrice;
        try {
            newPrice = Double.parseDouble(body.trim());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Ad updated = repo.updatePrice(id, newPrice);
        if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(updated);
    }

    // 1.5 Radera annons
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = repo.delete(id);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build(); // 204
    }
}