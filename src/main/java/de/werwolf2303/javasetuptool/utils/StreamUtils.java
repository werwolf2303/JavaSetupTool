package de.werwolf2303.javasetuptool.utils;

import de.werwolf2303.javasetuptool.PublicValues;

import java.io.*;

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

    public static byte[] inputStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
