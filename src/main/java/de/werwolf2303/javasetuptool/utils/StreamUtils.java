package de.werwolf2303.javasetuptool.utils;

import de.werwolf2303.javasetuptool.Setup;

import java.io.*;
import java.net.URL;
import java.util.Locale;

/**
 * Internal utils used in the setup
 */
public class StreamUtils {
    /**
     * Converts an inputstream to a string
     * @param stream the inputstream to convert
     * @return the string representation of the stream
     */
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
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Converts an inputstream to a byte array
     * @param stream the inputstream to convert
     * @return the byte array representation of the stream
     * @throws IOException
     */
    public static byte[] inputStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    /**
     * Converts an object to an inputstream
     * @param image the object to convert
     * @return the inputstream representation of the object
     * @throws IOException
     */
    public static InputStream imageObjectToInputStream(Object image) throws IOException {
        InputStream imageStream;
        if(image instanceof String) {
            if(((String) image).toLowerCase(Locale.ROOT).startsWith("http") || ((String) image).toLowerCase(Locale.ROOT).startsWith("https")) {
                imageStream = new URL((String) image).openStream();
            }else {
                if(Setup.class.getResourceAsStream((String) image) != null) {
                    imageStream = Setup.class.getResourceAsStream((String) image);
                }else {
                    throw new IllegalArgumentException("image of type String is neither a url or a valid resource path");
                }
            }
        }else if(image instanceof InputStream) {
            imageStream = (InputStream) image;
        }else {
            throw new IllegalArgumentException("Object image must be one of the following types: InputStream, String");
        }
        return imageStream;
    }
}
