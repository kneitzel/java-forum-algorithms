package de.kneitzel.so;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySerializationFailure {

    @Test
    public void testBinarySerialization() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("Some String");

        try ( FileOutputStream fileOut = new FileOutputStream("account.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(objects);
        } catch (Exception ex) {
            fail("Exception!", ex);
        }

        ArrayList<Point> loadedList = null;
        try (
                FileInputStream fileIn = new FileInputStream("account.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            loadedList = (ArrayList<Point>) in.readObject();
        } catch (Exception ex) {
            fail("Exception!", ex);
        }

        // Assert, that we was able to load the data.
        assertEquals(1, loadedList.size());

        // So whenever we try to access, we get an exception:
        ArrayList<Point> finalLoadedList = loadedList;
        assertThrows(ClassCastException.class, () -> {
            Point derived = finalLoadedList.get(0);
        });
    }
}
