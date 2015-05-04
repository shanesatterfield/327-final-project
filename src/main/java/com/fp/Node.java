package com.fp;

import java.util.*;
import java.util.concurrent.locks.*;

public class Node {
  static final int string_length = 100;
  public static Lock lock = new ReentrantLock();
  public String string;

  public Node() {
    this.string = this.makeString();
  }
  public Node(String string) {
    this.string = string;
  }
  public String makeString() {
    String pool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    Random r = new Random();
    StringBuilder ret = new StringBuilder(Node.string_length);
    for (int i = 0; i < Node.string_length; i++)
      ret.append(pool.charAt(r.nextInt(pool.length())));
    return ret.toString();
  }
  public void shuffle() {
    List<String> characters = new ArrayList<String>(Arrays.asList(this.string.split("")));
    Collections.shuffle(characters);
    this.string = String.join("", characters);
  }
}
