package com.example.Trader.Controller;

import com.example.Trader.Model.Trader;
import com.example.Trader.Services.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/traders")
public class TraderRestController {

    @Autowired
    private TraderService traderService;

    // Get all traders
    @GetMapping
    public ResponseEntity<List<Trader>> getAllTraders() {
        List<Trader> traders = traderService.findAll();
        return ResponseEntity.ok(traders); // Return the list of traders in JSON format
    }

    // Create a new trader
    @PostMapping
    public ResponseEntity<Trader> createTrader(@RequestBody Trader trader) {
        Trader newTrader = traderService.save(trader);
        return ResponseEntity.ok(newTrader);
    }

    // Update an existing trader by ID
    @PutMapping("/{id}")
    public ResponseEntity<Trader> updateTrader(@PathVariable Long id, @RequestBody Trader updatedTrader) {
        Trader existingTrader = traderService.findById(id);
        if (existingTrader != null) {
            existingTrader.setEmail(updatedTrader.getEmail());
            existingTrader.setPassword(updatedTrader.getPassword());
            existingTrader.setKycStatus(updatedTrader.getKycStatus());
            existingTrader.setDigitalCertificateId(updatedTrader.getDigitalCertificateId());
            existingTrader.setProofOfAddress(updatedTrader.getProofOfAddress());
            traderService.save(existingTrader);
            return ResponseEntity.ok(existingTrader);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a trader by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrader(@PathVariable Long id) {
        Trader existingTrader = traderService.findById(id);
        if (existingTrader != null) {
            traderService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
