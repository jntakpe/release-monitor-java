package com.github.jntakpe.releasemonitorjava.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ConfigListener {

    private static final WireMockServer wireMockServer = new WireMockServer(8089);

    private static final Lock startLock = new ReentrantLock();

    private static final Lock stopLock = new ReentrantLock();

    @EventListener
    public void handleContextRefresh(ApplicationReadyEvent event) {
        lock(startLock, () -> {
            if (!isWiremockRunning()) {
                wireMockServer.start();
            }
        });
    }

    @EventListener
    public void handleContextRefresh(ContextClosedEvent event) {
        lock(stopLock, () -> {
            if (isWiremockRunning()) {
                wireMockServer.stop();
            }
        });
    }

    private boolean isWiremockRunning() {
        return wireMockServer.isRunning();
    }

    private void lock(Lock lock, Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
