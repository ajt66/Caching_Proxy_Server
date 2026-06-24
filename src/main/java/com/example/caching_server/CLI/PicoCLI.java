/*
 * package com.example.caching_server.CLI;
 * 
 * import java.util.concurrent.Callable;
 * 
 * import org.springframework.stereotype.Component;
 * 
 * import picocli.CommandLine;
 * import picocli.CommandLine.Command;
 * import picocli.CommandLine.Option;
 * 
 * @Component
 * 
 * @Command(name = "PicoCLI")
 * public class PicoCLI implements Callable<Integer> {
 * 
 * @Option(names = "--port", description = "Port number to run the server on",
 * required = true)
 * private int port;
 * 
 * @Override
 * public Integer call() throws Exception {
 * System.setProperty("server.port", String.valueOf(port));
 * return 0;
 * }
 * }
 */