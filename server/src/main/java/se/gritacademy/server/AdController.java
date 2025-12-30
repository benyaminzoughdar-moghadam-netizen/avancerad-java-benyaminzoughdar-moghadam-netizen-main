//commit 4
package se.gritacademy.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdRepository repository = new AdRepository();

    @GetMapping
    public List<Ad> getAllAds() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ad> getAd(@PathVariable long id) {
        Ad ad = repository.findById(id);
        if (ad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ad);
    }

    @PostMapping
    public Ad createAd(@RequestBody Ad ad) {
        return repository.save(ad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrice(
            @PathVariable long id,
            @RequestParam String pin,
            @RequestParam double price
    ) {
        Ad ad = repository.findById(id);
        if (ad == null) return ResponseEntity.notFound().build();
        if (!ad.getPinCode().equals(pin)) return ResponseEntity.status(403).build();

        ad.setPrice(price);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(
            @PathVariable long id,
            @RequestParam String pin
    ) {
        Ad ad = repository.findById(id);
        if (ad == null) return ResponseEntity.notFound().build();
        if (!ad.getPinCode().equals(pin)) return ResponseEntity.status(403).build();

        repository.delete(id);
        return ResponseEntity.ok().build();
    }
}
//commit 9