package com.example.Trader.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Trader.Model.Trader;
import com.example.Trader.Repository.TraderRepository;

import java.util.List;

@Service
public class TraderService {

    @Autowired
    private TraderRepository traderRepository;

    public List<Trader> findAll() {
        return traderRepository.findAll();
    }

    public Trader save(Trader trader) {
        return traderRepository.save(trader);
    }

    public void delete(Long id) {
        traderRepository.deleteById(id);
    }

    public Trader update(Long id, Trader updatedTrader) {
        return traderRepository.findById(id)
                .map(trader -> {
                    trader.setEmail(updatedTrader.getEmail());
                    trader.setPassword(updatedTrader.getPassword());
                    trader.setKycStatus(updatedTrader.getKycStatus());
                    trader.setDigitalCertificateId(updatedTrader.getDigitalCertificateId());
                    trader.setProofOfAddress(updatedTrader.getProofOfAddress());
                    return traderRepository.save(trader);
                })
                .orElseThrow(() -> new RuntimeException("Trader not found"));
    }

    public Trader findById(Long id) {
        return traderRepository.findById(id)
                .orElse(null); // Return null if trader not found
    }

}
