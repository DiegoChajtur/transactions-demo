package com.transactions.producer.dto;

import com.google.common.hash.Hashing;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
@Builder
public class TransactionRequest {
    @NotNull
    @Positive
    String userId;
    @NotNull
    @Positive
    String invoiceId;

    String transactionHash;

    public String calculateHash() {
        if (this.userId == null) return null;
        if (this.invoiceId == null) return null;
        if (this.userId.isBlank()) return null;
        if (this.invoiceId.isBlank()) return null;
        String payload = this.userId.concat(this.invoiceId);
        return Hashing.murmur3_32_fixed().hashString(payload, StandardCharsets.UTF_8).toString();
    }

}
