package org.nekobehemoth.hw06.server;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ServerStatistics {
    private final long startUpTime = System.currentTimeMillis();
    @Getter
    private AtomicInteger handledRequestsCount = new AtomicInteger();
    @Getter
    private AtomicInteger tcpConnectionsCounter = new AtomicInteger();;


    public void incrementHandledRequestsCount() {
        handledRequestsCount.incrementAndGet();
    }

    public void incrementTcpConnectionsCounter() {
        tcpConnectionsCounter.incrementAndGet();
    }

    public void decrementTcpConnectionsCounter() {
        tcpConnectionsCounter.decrementAndGet();
    }

    public String getStartUpTime() {
        Instant instant = Instant.ofEpochMilli(startUpTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(customFormatter);
    }

    public String getUpTime() {
        long upTimeInSeconds = Math.abs((startUpTime - System.currentTimeMillis()) / 1000);
        return secondsConverter(upTimeInSeconds);
    }


    private String secondsConverter(long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days);
        long restSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days);

        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, restSeconds);
    }
}

