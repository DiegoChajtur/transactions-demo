package com.transactions.producer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {
    TransactionStatus status;
}
