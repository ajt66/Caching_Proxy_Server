
package com.example.caching_server.CLI;

import java.util.concurrent.Callable;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.caching_server.CachingServerApplication;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "PicoCLI")
public class PicoCLI implements Callable<Integer> {

    @Option(names = "--port", description = "Port number to run the server on", required = true)
    private int port;

    // @Option(names = "--origin", description = "url For Origin server", required =
    // true)
    // private String origin;

    @Override
    public Integer call() throws Exception {
        System.setProperty("server.port", String.valueOf(port));
        // System.setProperty("origin.url", origin);

        SpringApplication.run(CachingServerApplication.class, new String[0]);

        Thread.currentThread().join();

        return 0;
    }
}
