package com.example.Trader.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Trader {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long traderId;
    
        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email cannot be empty")
        private String email;
    
        @NotEmpty(message = "Password cannot be empty")
        private String password;
    
        @Enumerated(EnumType.STRING)
        private KYCStatus kycStatus;
    
        private Long digitalCertificateId;
    
        private String proofOfAddress;

        public Long getTraderId() {
            return traderId;
        }

        public void setTraderId(Long traderId) {
            this.traderId = traderId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public KYCStatus getKycStatus() {
            return kycStatus;
        }

        public void setKycStatus(KYCStatus kycStatus) {
            this.kycStatus = kycStatus;
        }

        public Long getDigitalCertificateId() {
            return digitalCertificateId;
        }

        public void setDigitalCertificateId(Long digitalCertificateId) {
            this.digitalCertificateId = digitalCertificateId;
        }

        public String getProofOfAddress() {
            return proofOfAddress;
        }

        public void setProofOfAddress(String proofOfAddress) {
            this.proofOfAddress = proofOfAddress;
        }
    
    
}

