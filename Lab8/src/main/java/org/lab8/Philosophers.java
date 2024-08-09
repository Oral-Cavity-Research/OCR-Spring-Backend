package org.example;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosophers implements Runnable {
    private final Lock leftChopstick;
    private final Lock rightChopstick;
    private final int philosopherNumber;
    private final Controller controller;
    private static final int ACTION_DURATION = 100;

    public Philosophers(int philosopherNumber, Lock leftChopstick, Lock rightChopstick, Controller controller) {
        this.philosopherNumber = philosopherNumber;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.controller = controller;
    }

    private void performAction(String action) throws InterruptedException {
        System.out.println("Philosopher " + philosopherNumber + " " + action);
        Thread.sleep((int) (Math.random() * ACTION_DURATION));
    }

    @Override
    public void run() {
        try {
            while (controller.isExecuting()) {
                performAction(": Thinking, picking up chopsticks");
                if (philosopherNumber % 2 == 0) {
                    leftChopstick.lock();
                    try {
                        performAction(": Picked up left chopstick");
                        rightChopstick.lock();
                        try {
                            performAction(": Picked up right chopstick - eating");
                        } finally {
                            rightChopstick.unlock();
                        }
                        performAction(": Put down left chopstick");
                    } finally {
                        leftChopstick.unlock();
                    }
                } else {
                    rightChopstick.lock();
                    try {
                        performAction(": Picked up right chopstick");
                        leftChopstick.lock();
                        try {
                            performAction(": Picked up left chopstick - eating");
                        } finally {
                            leftChopstick.unlock();
                        }
                        performAction(": Put down right chopstick");
                    } finally {
                        rightChopstick.unlock();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}