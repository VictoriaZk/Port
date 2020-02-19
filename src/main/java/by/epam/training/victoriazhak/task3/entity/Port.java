package by.epam.training.victoriazhak.task3.entity;

import by.epam.training.victoriazhak.task3.action.Dock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);
    private static final int DOCKS_AMOUNT = 3;
    private static final long DOCKS_CAPACITY = 20000;
    private static final Lock lock = new ReentrantLock();
    private static Port instance;
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private Queue<Dock> docks;
    private Semaphore semaphore = new Semaphore(DOCKS_AMOUNT, true);

    public static Port getInstance() {
        if(!initialized.get()) {
            try {
                lock.lock();
                if(!initialized.get()) {
                    instance = new Port();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private Port() {
        docks = new ArrayDeque<>(DOCKS_AMOUNT);
        for(int i = 0; i < DOCKS_AMOUNT; ++i) {
            docks.add(new Dock(DOCKS_CAPACITY, i + 1));
        }
    }

    public void setDocks(Dock docks) {
        try {
            lock.lock();
            this.docks.add(docks);
            LOGGER.info("docks #" + docks.getId() + " free");
            semaphore.release();
        } finally {
            lock.unlock();
        }
    }

    public Dock getDocks() {
        try {
            lock.lock();
            semaphore.acquire();
            Dock docks = this.docks.poll();
            return docks;
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    public static int getDocksAmount() {
        return DOCKS_AMOUNT;
    }

}
