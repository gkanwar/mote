package io.github.gkanwar.mote;

import java.io.*;

/**
 * Deep copy objects using serialization.
 * Inspiration credit: http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
 */
public class DeepCopy {
  public static Object copy(Object o) {
    Object obj = null;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(baos);
      out.writeObject(o);
      out.close();

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      ObjectInputStream in = new ObjectInputStream(bais);
      obj = in.readObject();
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Object copy failed.");
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("Object copy failed.");
    }
    return obj;
  }
}
