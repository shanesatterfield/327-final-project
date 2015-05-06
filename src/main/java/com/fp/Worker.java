package com.fp;

import java.util.*;

public class Worker extends Thread {
    public Node[] nodes;
    public Client client;
    public Random rand = new Random();
    public Worker(Node[] nodes, Client client) {
        this.nodes = nodes;
        this.client = client;
  }
    public void run() {
        try {
            int node_num = rand.nextInt(100);
            client.handleWorkerRequest(node_num);
            nodes[node_num].shuffle();
            Thread.sleep(500);
        } catch (Exception e) {}
    }
}
