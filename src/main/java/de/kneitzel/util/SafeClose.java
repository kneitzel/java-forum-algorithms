package de.kneitzel.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Utilityclass to close a closable without risk of any exception.
 */
public class SafeClose {
    public static void close(Closeable closeable) {
        if (closeable == null) return;

        try {
            closeable.close();
        } catch (IOException ex) {
            // ignored!
        }
    }
}
