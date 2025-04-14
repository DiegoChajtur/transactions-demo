package com.transactions.consumer.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "TransactionsService")
@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionDAO transactionDAO;

    public boolean processTransaction(Transaction transaction) {
        if (!transaction.isValid()) {
            log.debug("Transaction invalid {}", transaction.getTransactionHash());
            return false;
        }
        transactionDAO.save(transaction);
        log.debug("Transaction processed successfully {}", transaction.getTransactionHash());
        return true;
    }


}
