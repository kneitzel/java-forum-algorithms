package de.kneitzel.so;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Base  implements Serializable {
    public String name;
}

class Derived extends Base {
    public String comment;
}
public class BinarySerializationFailure {

    @Test
    public void testBinarySerialization() {
        ArrayList<Base> objects = new ArrayList<>();
        Base base = new Base();
        base.name = "Name";
        objects.add(base);

        try ( FileOutputStream fileOut = new FileOutputStream("account.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(objects);
        } catch (Exception ex) {
            fail("Exception!", ex);
        }

        ArrayList<Derived> loadedList = null;
        try (
                FileInputStream fileIn = new FileInputStream("account.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            loadedList = (ArrayList<Derived>) in.readObject();
        } catch (Exception ex) {
            fail("Exception!", ex);
        }

        // Assert, that we was able to load the data.
        assertEquals(1, loadedList.size());

        // So whenever we try to access, we get an exception:
        ArrayList<Derived> finalLoadedList = loadedList;
        assertThrows(ClassCastException.class, () -> {
            Derived derived = finalLoadedList.get(0);
        });
    }
}
