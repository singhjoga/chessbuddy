package com.technovator.api.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Utility function for handling exception message.
 */
public class ExceptionUtils {

    /**
     * Print the top 5 lines of the error message.
     *
     * @param e
     * @return
     */
    public static String getStackTrace(Exception e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        StringBuilder sb = new StringBuilder();
        e.printStackTrace(printStream);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String stackTrace;
        int count = 0;
        sb.append(e.getMessage());
        try {
			while ((stackTrace = reader.readLine()) != null) {
			    sb.append(System.lineSeparator());
			    sb.append(stackTrace);

			    if (count++ >= 5) {
			        break;
			    }
			}
		} catch (IOException e1) {
			try {
				reader.close();
			} catch (IOException e2) {
			}
		}

        return sb.toString();
    }
}
