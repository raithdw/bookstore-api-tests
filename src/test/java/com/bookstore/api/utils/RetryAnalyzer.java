package com.bookstore.api.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int maxRetry = 1; // recommended between 1-2 max
    private final ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);

    @Override
    public boolean retry(ITestResult result) {
        int count = retryCount.get();
        if (count < maxRetry) {
            retryCount.set(count + 1);
            System.out.println("[RETRY] Retrying " + result.getName()
                    + " | Attempt " + (count + 1));
            return true;
        }
        return false;
    }
}