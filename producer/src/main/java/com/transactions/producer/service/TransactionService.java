package com.transactions.producer.service;

import com.transactions.producer.dao.TransactionDAO;
import com.transactions.producer.dto.TransactionRequest;
import com.transactions.producer.dto.TransactionResponse;
import com.transactions.producer.dto.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "Service")
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionDAO transactionDAO;

    public TransactionResponse submitTransaction(TransactionRequest request) {
        String hash = request.calculateHash();
        if(hash.isBlank()){
            log.debug("Transaction hash is blank for User:{} Invoice:{}", request.getUserId(), request.getInvoiceId());
            return TransactionResponse.builder().status(TransactionStatus.failed).build();
        }
        String transactionStr = String.join(":",request.getUserId(),request.getInvoiceId(),hash);
        log.debug("Push to Redis: {}", transactionStr);
        transactionDAO.pushTask(transactionStr);
        return TransactionResponse.builder().status(TransactionStatus.processing).build();
    }
}
