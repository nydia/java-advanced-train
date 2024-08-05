package com.nydia.memorydb.h2;

import lombok.extern.slf4j.Slf4j;
import org.h2.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/13 21:28
 * @Description: H2Trace
 */
@Slf4j
public class H2Trace {

    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    protected long start;

    protected void traceMemory() {
        trace("mem=" + getMemoryUsed());
    }

    public void trace(String s) {
        println(s);
    }

    /**
     * Get the number of megabytes heap memory in use.
     *
     * @return the used megabytes
     */
    public static int getMemoryUsed() {
        return (int) (getMemoryUsedBytes() / 1024 / 1024);
    }

    /**
     * Get the number of bytes heap memory in use.
     *
     * @return the used bytes
     */
    public static long getMemoryUsedBytes() {
        Runtime rt = Runtime.getRuntime();
        long memory = Long.MAX_VALUE;
        for (int i = 0; i < 8; i++) {
            rt.gc();
            long memNow = rt.totalMemory() - rt.freeMemory();
            if (memNow >= memory) {
                break;
            }
            memory = memNow;
        }
        return memory;
    }

    public void println(String s) {
        long now = System.nanoTime();
        long time = TimeUnit.NANOSECONDS.toMillis(now - start);
        printlnWithTime(time, getClass().getName() + ' ' + s);
    }

    static synchronized void printlnWithTime(long millis, String s) {
        StringBuilder builder = new StringBuilder(s.length() + 19);
        timeFormat.formatTo(LocalTime.now(), builder);
        log.info(formatTime(builder.append(' '), millis).append(' ').append(s).toString());
    }

    /**
     * Format the time in the format mm:ss.123 or hh:mm:ss.123 where 123 is
     * milliseconds.
     *
     * @param builder the string builder to append to
     * @param millis the time in milliseconds, non-negative
     * @return the specified string builder
     */
    static StringBuilder formatTime(StringBuilder builder, long millis) {
        int s = (int) (millis / 1_000);
        int m = s / 60;
        s %= 60;
        int h = m / 60;
        if (h != 0) {
            builder.append(h).append(':');
            m %= 60;
        }
        StringUtils.appendTwoDigits(builder, m).append(':');
        StringUtils.appendTwoDigits(builder, s).append('.');
        StringUtils.appendZeroPadded(builder, 3, (int) (millis % 1_000));
        return builder;
    }



}
