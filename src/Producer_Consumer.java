
// Java program to implement solution of producer
// consumer problem.

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.LinkedList;

public class Producer_Consumer {
    public static void main(String[] args)
            throws InterruptedException, IOException {

        // Object of a class that has both produce()
        // and consume() methods
        final PC pc = new PC();

        // Create the first producer thread.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce();
                }
                catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //Create the second producer thread.
        Thread t = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce2();
                }
                catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //Create 5 consumer threads
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.consume();

                }
                catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.consume();
                }
                catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.list2.add(1);
                    pc.list.add(1);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    while (true) {
                        synchronized (this)
                        {
                            // consumer thread waits while list
                            // is empty
                            while (pc.list2.size() == 0)
                                wait();

                            // to retrieve the first job in the list
                            int val = pc.list2.removeFirst();

                            System.out.println("Consumer 2 consumed-"
                                    + val);

                            // Wake up producer thread
                            notify();

                            int max = 100;
                            int min = 10;
                            int time = (int)Math.floor(Math.random()*(max-min+1)+min);
                            Thread.sleep(time);
                        }
                    }

                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    while (true) {
                        synchronized (this)
                        {
                            // consumer thread waits while list
                            // is empty
                            while (pc.list2.size() == 0)
                                wait();

                            // to retrieve the first job in the list
                            int val = pc.list2.removeFirst();

                            System.out.println("Consumer 2 consumed-"
                                    + val);

                            // Wake up producer thread
                            notify();

                            int max = 100;
                            int min = 10;
                            int time = (int)Math.floor(Math.random()*(max-min+1)+min);
                            Thread.sleep(time);
                        }
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        // Start all the threads
        t1.start();
        t.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        // t1 and t finishes before t2
        t1.join();
        t.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();

    }

    // This class has a list, producer (adds items to list
    // and consumer (removes items).
    public static class PC {
        //Write into the text file
        BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));
        // Create a list shared by producer and consumer
        // Size of list is 2.
        LinkedList<Integer> list = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        int capacity = 2;

        public PC() throws IOException {
        }

        // Function called by producer thread
        public void produce() throws InterruptedException,IOException
        {
            int value = 0;
            while (true) {
                synchronized (this)
                {
                    // producer thread waits while list
                    // is full
                    while (list.size() == capacity)
                        wait();

                    System.out.println("Producer produced-"
                            + value);
                    writer.write("Producer produced product 1-"
                            + value + "production Sequence 1" + "\n");

                    // to insert the jobs in the list
                    list.add(value++);

                    // notifies the consumer thread that
                    // now it can start consuming
                    notify();

                    // makes the working of program easier
                    // to  understand
                    //Chose random time between 0.01 seconds to 0.1 seconds
                    //Get random value between 0.01 and 0.1
                    int max = 100;
                    int min = 10;
                    int time = (int)Math.floor(Math.random()*(max-min+1)+min);
                    Thread.sleep(time);
                }
            }
        }
        // Function called by producer thread
        public void produce2() throws InterruptedException, IOException {
            int value = 0;
            //Update Capacity
            capacity = 2;
            while (true) {
                synchronized (this)
                {
                    // producer thread waits while list
                    // is full
                    while (list2.size() == capacity)
                        wait();

                    System.out.println("Producer produced-"
                            + value);
                    writer.write("Producer produced product 2-"
                            + value + "Production sequence 2" + "\n");
                    // to insert the jobs in the list
                    list2.add(value++);

                    // notifies the consumer thread that
                    // now it can start consuming
                    notify();

                    // makes the working of program easier
                    // to  understand
                    //Chose random time between 0.01 seconds to 0.1 seconds
                    //Get random value between 0.01 and 0.1
                    int max = 100;
                    int min = 10;
                    int time = (int)Math.floor(Math.random()*(max-min+1)+min);
                    Thread.sleep(time);
                }
            }
        }

        // Function called by consumer thread
        public void consume() throws InterruptedException, IOException {
            while (true) {
                synchronized (this)
                {
                    // consumer thread waits while list
                    // is empty
                    while (list.size() == 0)
                        wait();

                    // to retrieve the first job in the list
                    int val = list.removeFirst();

                    System.out.println("Consumer consumed-"
                            + val);
                    writer.write("Consumer consumed product 1-"
                            + val + "Consumption Sequence id" +  " 0.\n");

                    // Wake up producer thread
                    notify();

                    int max = 100;
                    int min = 10;
                    int time = (int)Math.floor(Math.random()*(max-min+1)+min);
                    Thread.sleep(time);
                }
            }
        }

    }
}