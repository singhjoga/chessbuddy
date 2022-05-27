package com.technovator.game;

import java.io.File;
import java.net.URI;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import com.technovator.api.StreamGobbler;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FlutterClientTest {

    @TestHTTPResource("/game/white")
    URI uriWhite;
    @TestHTTPResource("/game/black")
    URI uriBlack;
    @Test
    public void testFlutterClient() throws Exception {
    	String cmd =String.format("flutter test test/protocol-test.dart");;
    	String appDir = System.getProperty("user.home")+"/src/chessbuddy/app";

    	System.out.println("URI: "+uriWhite.toString());
    	
    	ProcessBuilder builder = new ProcessBuilder();
    	builder.command("flutter","test","test/protocol-test.dart");
    	builder.directory(new File(appDir));
    	Process process = builder.start();
    	StreamGobbler streamGobbler = 
    	    	  new StreamGobbler(process.getInputStream(), System.out::println);
    	    	StreamGobbler errorGobbler = 
    	    	    	  new StreamGobbler(process.getErrorStream(), System.out::println);
    	    	Executors.newSingleThreadExecutor().submit(streamGobbler);
    	    	Executors.newSingleThreadExecutor().submit(errorGobbler);
    	int exitCode = process.waitFor();
    	assert exitCode == 0;
    }
   
}