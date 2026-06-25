package com.example.caching_server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.example.caching_server.CLI.PicoCLI;

import picocli.CommandLine;

@SpringBootApplication
@EnableCaching
public class CachingServerApplication {

	public static void main(String[] args) {
		CommandLine commandLine = new CommandLine(new PicoCLI());
		System.exit(commandLine.execute(args));
	}

}
