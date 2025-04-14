package com.transactions.consumer.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDAO extends JpaRepository<Transaction, Integer> {
}
