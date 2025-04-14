package com.transactions.consumer.task;

import com.transactions.consumer.transaction.Transaction;
import com.transactions.consumer.transaction.TransactionsService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j(topic = "TaskExecutor")
@Service
@RequiredArgsConstructor
public class Task {

    private final TaskDAO taskDao;
    private final TransactionsService transactionsService;

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService fetchTaskExecutor = Executors.newSingleThreadExecutor();
    private final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    private final Semaphore threadAvailability = new Semaphore(MAX_THREADS);
    private final ExecutorService threadsPoolExecutor = Executors.newFixedThreadPool(MAX_THREADS - 2);

    @PostConstruct
    public void task() {
        log.info("Starting Task process");

        fetchTaskExecutor.submit(() -> {
            while (running.get()) {
                try {
                    log.debug("Waiting for an available Thread");
                    threadAvailability.acquire();
                    threadsPoolExecutor.submit(this::processNextTask);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void processNextTask() {
        try {
            String taskString = taskDao.popTask();
            if (taskString == null) {
                log.debug("No task found in Redis.");
                return;
            }

            log.debug("Task retrieved from Redis: {}", taskString);

            Transaction transaction = parseTask(taskString);
            if (transaction != null) {
                transactionsService.processTransaction(transaction);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            threadAvailability.release();
        }
    }

    private Transaction parseTask(String taskString) {
        String[] parts = taskString.split(":");
        if (parts.length != 3) {
            log.warn("Invalid task format: {}", taskString);
            return null;
        }
        try {
            String userId = parts[0];
            String invoiceId = parts[1];
            String transactionHash = parts[2];
            return Transaction.builder().userId(userId).invoiceId(invoiceId).transactionHash(transactionHash).build();
        } catch (Exception e) {
            log.error("Error parsing task string: {}", taskString, e);
            return null;
        }
    }


    @PreDestroy
    public void stopALl() {
        running.set(false);
        fetchTaskExecutor.shutdownNow();
        threadsPoolExecutor.shutdownNow();
    }

}
