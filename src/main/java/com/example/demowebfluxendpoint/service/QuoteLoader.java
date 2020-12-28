package com.example.demowebfluxendpoint.service;

import com.example.demowebfluxendpoint.model.Quote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class QuoteLoader {

    private static final Logger logger = LogManager.getLogger(QuoteLoader.class);

    private Path filePath;

    public List<Quote> load() throws IOException {
        return Files.lines(filePath)
                .filter(s -> !s.isEmpty())
                .map(this::parseQuote)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Quote parseQuote(String line) {
        Pattern pattern = Pattern.compile("(\\d+),(.*),([^,]+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return new Quote(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3));
        } else {
            logger.error("Skip parsing invalid quote line: {}", line);
            return null;
        }
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
