package com.transactions.producer.controller;

import com.transactions.producer.dto.TransactionRequest;
import com.transactions.producer.dto.TransactionResponse;
import com.transactions.producer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@Slf4j(topic = "Controller")
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    ResponseEntity<TransactionResponse> submitTransaction(@RequestBody @Valid TransactionRequest request) {
        log.debug("New Transaction is submitted User:{} Invoice:{}", request.getUserId(), request.getInvoiceId());
        return ResponseEntity.accepted().body(transactionService.submitTransaction(request));
    }


}
