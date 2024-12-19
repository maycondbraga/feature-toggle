package com.md.featuretoggle.controllers;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unleash")
public class UnleashController {
    @Value("${io.getunleash.api-url}")
    private String unleashApiUrl;

    @Value("${io.getunleash.api-token}")
    private String unleashApiToken;

    @Value("${io.getunleash.app-name}")
    private String unleashAppName;

    private final String flagName = "testFeatureFlag";

    @GetMapping("/demo-flag")
    public ResponseEntity<String> getDemoFlag() {
        UnleashConfig unleashConfig = UnleashConfig.builder()
                .appName(unleashAppName)
                .unleashAPI(unleashApiUrl)
                .apiKey(unleashApiToken)
                .sendMetricsInterval(5)
                .build();

        Unleash unleash = new DefaultUnleash(unleashConfig);

        boolean featureEnabled = unleash.isEnabled(flagName);

        return ResponseEntity.ok(String.format("flag %s is:: %s", flagName, featureEnabled));
    }
}
