package by.epam.training.victoriazhak.task3.entity;

import by.epam.training.victoriazhak.task3.action.Dock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayDeque;
import java.util.Queue;

public class Ship implements Runnable{
    private static final Logger LOGGER = LogManager.getLogger(Ship.class);
    private String name;
    private Queue<Container> containers;
    private long deadWeight;
    private LoadingType loadingType;

    public Queue<Container> getContainers() {
        return containers;
    }

    public LoadingType getLoadingType() {
        return loadingType;
    }

    public long getDeadWeight() {
        return deadWeight;
    }

    public String getName() {
        return name;
    }

    public long getContainersWeight() {
        return calculateWeight(containers);
    }

    public Ship(String name, long deadWeight) {
        this.name = name;
        this.deadWeight = deadWeight;
        containers = new ArrayDeque<>();
    }

    @Override
    public void run() {
        Port port = Port.getInstance();
        boolean done = false;
        while (!done) {
            Dock docks = port.getDocks();
            LOGGER.info(name + " got docks: #" + docks.getId());
            switch (loadingType) {
                case LOAD:
                    done = loadShip(docks);
                    break;
                case UNLOAD:
                    done = unloadShip(docks);
                    break;
                case UNLOAD_LOAD:
                    boolean unloaded = unloadShip(docks);
                    if(!unloaded) {
                        break;
                    }
                    boolean loaded = loadShip(docks);
                    if(loaded) {
                        done = true;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Not supported argument");
            }
            port.setDocks(docks);
        }
        LOGGER.info(name + " action is done");
    }

    private long calculateWeight(Queue<Container> containers) {
        long currentWeight = 0;
        for(Container container : containers) {
            currentWeight += container.getWeight();
        }
        return currentWeight;
    }

    private boolean loadShip(Dock docks) {
        long availableWeight = deadWeight - calculateWeight(containers);
        Queue<Container> takingContainers = docks.takeContainers(availableWeight, name);
        if(takingContainers != null) {
            containers.addAll(takingContainers);
            LOGGER.info(name + " loaded in docks #" + docks.getId());
        }
        return takingContainers != null;
    }

    private boolean unloadShip(Dock docks) {
        if(containers.isEmpty()) {
            return true;
        }
        boolean unload = docks.addContainers(containers, name);
        if(unload) {
            LOGGER.info(name + " unloaded in docks #" + docks.getId());
        }
        return unload;
    }
}
