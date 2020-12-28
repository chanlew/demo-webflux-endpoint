package com.example.demowebfluxendpoint.controller;

import com.example.demowebfluxendpoint.model.Quote;
import com.example.demowebfluxendpoint.service.QuoteLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@CrossOrigin
@RestController
public class QuoteController {

    private static final Logger logger = LogManager.getLogger(QuoteController.class);

    @Autowired
    private QuoteLoader loader;

    @RequestMapping("/quotes")
    public Flux<Quote> getQuotes(@RequestParam(value = "number", defaultValue = "5") Integer numQuotes) throws IOException {
        logger.info("Loading {} quotes", numQuotes);
        return Flux.just(loader.load().toArray(Quote[]::new))
                .limitRequest(numQuotes)
                .delayElements(Duration.ofSeconds(5));
    }
}
