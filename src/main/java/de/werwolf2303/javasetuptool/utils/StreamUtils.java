package de.werwolf2303.javasetuptool.utils;

import de.werwolf2303.javasetuptool.PublicValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class StreamUtils {
    public static String inputStreamToString(InputStream stream) {
        try {
            String newLine = System.getProperty("line.separator");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream));
            StringBuilder result = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append(newLine);
                }
                result.append(line);
            }
            return result.toString();
        }catch (IOException e) {
            PublicValues.logger.catching(e);
            return "";
        }
    }
}
