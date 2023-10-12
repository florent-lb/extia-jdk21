package dev.flb;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class VirtualThreadCallExamples {

    @Test
    public void testWithVirtual() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
    }

    @Test
    public void testWithCachedThreadPool() {
        try (var executor = Executors.newCachedThreadPool()) {
            IntStream.range(0, 100_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
    }

    @Test
    public void testWithVirtualWithChrono() throws ExecutionException, InterruptedException {
        LocalDateTime time = LocalDateTime.now();
        Runnable r = () -> {
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        List<Future<?>> futures = new ArrayList<>(100_000);
        ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 100_000; i++){
            futures.add(virtualThreadExecutor.submit(r));
        }
        for(Future<?> f : futures){
            f.get();
        }
        System.out.println(ChronoUnit.MILLIS.between(time, LocalDateTime.now()));
    }

  @Test
    public void testWithCachedWithChrono() throws ExecutionException, InterruptedException {
        LocalDateTime time = LocalDateTime.now();
        Runnable r = () -> {
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        List<Future<?>> futures = new ArrayList<>(100_000);
        ExecutorService virtualThreadExecutor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100_000; i++){
            futures.add(virtualThreadExecutor.submit(r));
        }
        for(Future<?> f : futures){
            f.get();
        }
        System.out.println(ChronoUnit.MILLIS.between(time, LocalDateTime.now()));
    }


}