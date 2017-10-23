package com.github.jntakpe.releasemonitorjava.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigListener {

    private static final WireMockServer wireMockServer = new WireMockServer(8089);

    @EventListener
    public void handleContextRefresh(ApplicationReadyEvent event) {
        if (!isWiremockRunning()) {
            wireMockServer.start();
        }
    }

    @EventListener
    public void handleContextRefresh(ContextClosedEvent event) {
        if (isWiremockRunning()) {
            wireMockServer.stop();
        }
    }

    private boolean isWiremockRunning() {
        return wireMockServer.isRunning();
    }

}
