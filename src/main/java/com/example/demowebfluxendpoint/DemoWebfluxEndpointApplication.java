package com.example.demowebfluxendpoint;

import com.example.demowebfluxendpoint.service.QuoteLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@SpringBootApplication
public class DemoWebfluxEndpointApplication {

    private static final Logger logger = LogManager.getLogger(DemoWebfluxEndpointApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoWebfluxEndpointApplication.class, args);
    }

    @Autowired
    public void loadQuotes(QuoteLoader loader) throws IOException {
        logger.info("Checking quote file path");
        File file = ResourceUtils.getFile("classpath:quote.csv");
        Path path = file.toPath();
        logger.info("Found quote file at: {}", path.toAbsolutePath());
        loader.setFilePath(path);
    }
}
