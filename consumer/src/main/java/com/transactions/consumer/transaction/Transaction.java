package com.transactions.consumer.transaction;


import com.google.common.hash.Hashing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @Column(unique = true, updatable = false)
    private String transactionHash;

    @Column(unique = true, updatable = false)
    private String invoiceId;

    @Column(updatable = false)
    private String userId;

    @Column(updatable = false)
    private LocalDateTime create_at;

    @PrePersist
    private void createAtTime() {
        if (create_at == null) {
            this.create_at = LocalDateTime.now();
        }
    }

    public boolean isValid() {
        // A valid transactions have a correct transactionHash
        String payload = this.userId.concat(this.invoiceId);
        String hash = Hashing.murmur3_32_fixed().hashString(payload, StandardCharsets.UTF_8).toString();

        if (!this.transactionHash.equals(hash)) {
            return false;
        }
        return true;
    }
    

}
