package com.example.Trader.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Trader.Model.Trader;
import com.example.Trader.Services.TraderService;
import com.example.Trader.Model.KYCStatus;

import java.util.Scanner;

@Component
public class TraderCLI implements CommandLineRunner {

    @Autowired
    private TraderService traderService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("Welcome to the Trader CLI. Choose an option:");
            System.out.println("1. Create Trader");
            System.out.println("2. List Traders");
            System.out.println("3. Update Trader");
            System.out.println("4. Delete Trader");
            System.out.println("5. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createTrader();
                    break;
                case 2:
                    listTraders();
                    break;
                case 3:
                    updateTrader();
                    break;
                case 4:
                    deleteTrader();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void createTrader() {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // Prompt for KYC status
        System.out.println("Choose KYC Status:");
        for (KYCStatus status : KYCStatus.values()) {
            System.out.printf("%d. %s%n", status.ordinal() + 1, status);
        }
        int kycChoice = Integer.parseInt(scanner.nextLine());
        KYCStatus kycStatus = KYCStatus.values()[kycChoice - 1];

        System.out.println("Enter digital certificate ID (or leave blank if not applicable):");
        String digitalCertificateIdInput = scanner.nextLine();
        Long digitalCertificateId = digitalCertificateIdInput.isEmpty() ? null
                : Long.parseLong(digitalCertificateIdInput);

        System.out.println("Enter proof of address (or leave blank if not applicable):");
        String proofOfAddress = scanner.nextLine();

        Trader trader = new Trader();
        trader.setEmail(email);
        trader.setPassword(password);
        trader.setKycStatus(kycStatus);
        trader.setDigitalCertificateId(digitalCertificateId);
        trader.setProofOfAddress(proofOfAddress);

        traderService.save(trader);
        System.out.println("Trader created.");
    }

    private void listTraders() {
        System.out.printf("%-10s %-30s %-20s %-20s %-20s %-30s%n", "Trader ID", "Email", "KYC Status",
                "Digital Certificate ID", "Proof of Address", "Password");
        System.out
                .println("------------------------------------------------------------------------------------------");

        traderService.findAll().forEach(trader -> {
            System.out.printf("%-10d %-30s %-20s %-20s %-20s %-30s%n",
                    trader.getTraderId(),
                    trader.getEmail(),
                    trader.getKycStatus(),
                    trader.getDigitalCertificateId() != null ? trader.getDigitalCertificateId() : "N/A",
                    trader.getProofOfAddress() != null ? trader.getProofOfAddress() : "N/A",
                    trader.getPassword());
        });
    }

    private void updateTrader() {
        System.out.println("Enter Trader ID:");
        Long id = Long.parseLong(scanner.nextLine());

        // Fetch the existing trader to show current values
        Trader existingTrader = traderService.findById(id);
        if (existingTrader == null) {
            System.out.println("Trader not found.");
            return;
        }

        System.out.println("Updating Trader ID: " + existingTrader.getTraderId());

        // Prompt for email
        System.out.println("Enter new email (leave blank to keep current: " + existingTrader.getEmail() + "):");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            email = existingTrader.getEmail(); // Keep current value if left blank
        }

        // Prompt for password
        System.out.println("Enter new password (leave blank to keep current):");
        String password = scanner.nextLine();
        if (password.isEmpty()) {
            password = existingTrader.getPassword(); // Keep current value if left blank
        }

        // Prompt for KYC Status
        System.out
                .println("Choose new KYC Status (leave blank to keep current: " + existingTrader.getKycStatus() + "):");
        for (KYCStatus status : KYCStatus.values()) {
            System.out.printf("%d. %s%n", status.ordinal() + 1, status);
        }
        String kycChoiceInput = scanner.nextLine();
        KYCStatus kycStatus = existingTrader.getKycStatus(); // Default to current status
        if (!kycChoiceInput.isEmpty()) {
            int kycChoice = Integer.parseInt(kycChoiceInput);
            kycStatus = KYCStatus.values()[kycChoice - 1]; // Update to new status
        }

        // Prompt for digital certificate ID
        System.out.println("Enter new digital certificate ID (leave blank to keep current: "
                + existingTrader.getDigitalCertificateId() + "):");
        String digitalCertificateIdInput = scanner.nextLine();
        Long digitalCertificateId = digitalCertificateIdInput.isEmpty() ? existingTrader.getDigitalCertificateId()
                : Long.parseLong(digitalCertificateIdInput);

        // Prompt for proof of address
        System.out.println("Enter new proof of address (leave blank to keep current):");
        String proofOfAddress = scanner.nextLine();
        if (proofOfAddress.isEmpty()) {
            proofOfAddress = existingTrader.getProofOfAddress(); // Keep current if left blank
        }

        // Create an updated trader object and call update method
        Trader updatedTrader = new Trader();
        updatedTrader.setEmail(email);
        updatedTrader.setPassword(password);
        updatedTrader.setKycStatus(kycStatus);
        updatedTrader.setDigitalCertificateId(digitalCertificateId);
        updatedTrader.setProofOfAddress(proofOfAddress);

        traderService.update(id, updatedTrader);
        System.out.println("Trader updated.");
    }

    private void deleteTrader() {
        System.out.println("Enter Trader ID:");
        Long id = Long.parseLong(scanner.nextLine());
        traderService.delete(id);
        System.out.println("Trader deleted.");
    }
}
