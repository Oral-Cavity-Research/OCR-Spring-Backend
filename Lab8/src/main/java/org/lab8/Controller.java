package org.example;

public class Controller
{
    private volatile boolean running = true;

    public void stop()
    {
        running = false;
    }

    public boolean isRunning()
    {
        return running;
    }
}
