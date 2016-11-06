package com.psv.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogParser {

    private static Pattern pattern = Pattern.compile("^(\\d{2}:\\d{2}) .+ - \\[(started|stopped)\\] (.*)");
    private static LocalTime lastTime = null;
    private static String lastTask = null;
    private static String lastTaskStatus = null;

    public static Map<String, Long> parse(String file) throws IOException {
        Map<String, Long> result = new HashMap<>();

        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach(line -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    LocalTime time = LocalTime.parse(matcher.group(1));
                    String status = matcher.group(2);
                    String task = matcher.group(3);
                    if (lastTask != null && lastTaskStatus != null && lastTask.equals(task) && !lastTaskStatus.equals(status)) {
                        long timeDiff = lastTime.until(time, ChronoUnit.MINUTES);
                        result.merge(task, timeDiff, (v1, v2) -> v1 + v2);
                    }
                    lastTime = time;
                    lastTaskStatus = status;
                    lastTask = task;
                }
            });
        }
        return result;
    }
}
