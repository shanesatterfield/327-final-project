package com.fp;

import java.util.*;
import java.util.concurrent.locks.*;

public class Node {
  static final int string_length = 100;
  public Lock lock = new ReentrantLock();
  // used to communicate who gets the lock between the client and the workers
  public Condition cond = lock.newCondition();
  public String string;
  public Boolean hasToken = false;

  public Node() {
    // this.string = this.makeString();
    this.string = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
  }
  public Node(String string) {
    this.string = string;
    }
    public void signalAll() {
        lock.lock();
        cond.signalAll();
        lock.unlock();
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
      lock.lock();
      try {
          while (!hasToken) {
              cond.await();
          }

          System.out.printf("Editing\n");
          this.string = makeString();

        //   List<String> characters = new ArrayList<String>(Arrays.asList(this.string.split("")));
        //   Collections.shuffle(characters);
        //   this.string = String.join("", characters);

      } catch (Exception e) {
      } finally {
          lock.unlock();
      }
    }
}
