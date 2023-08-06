package de.werwolf2303.javasetuptool.utils;

import java.io.*;
public class StreamUtils {
    public static String inputStreamToString(InputStream stream) {
        try {
            String newLine = System.getProperty("line.separator");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream));
            StringBuffer result = new StringBuffer();
            for (String line; (line = reader.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append(newLine);
                }
                result.append(line);
            }
            return result.toString();
        }catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
