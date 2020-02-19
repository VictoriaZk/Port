package by.epam.training.victoriazhak.task3.action;

import by.epam.training.victoriazhak.task3.entity.Container;
import java.util.*;

public class Dock {
    private static List<Container> containers;
    private static long capacity;
    private long id;
    private Map<String, List<Long>> shipGivenContainers = new HashMap<>();

    public static List<Container> getContainers() {
        return containers;
    }

    public static long getCapacity() {
        return capacity;
    }

    public Dock(long capacity, long id) {
        this.capacity = capacity;
        this.id = id;
        containers = new ArrayList<>();
    }
    public boolean addContainers(Queue<Container> incomingContainers, String shipName) {
        registerShip(shipName);
        if(!isPossibleToAddContainers(incomingContainers)) {
            return false;
        }
        List<Long> containersIdsGivenByThisShip = shipGivenContainers.get(shipName);
        while(!incomingContainers.isEmpty()) {
            Container incomingContainer = incomingContainers.poll();
            containers.add(incomingContainer);
            long incomingContainerId = incomingContainer.getId();
            containersIdsGivenByThisShip.add(incomingContainerId);
        }
        return true;
    }

    private boolean isPossibleToAddContainers(Queue<Container> incomingContainers) {
        long incomingContainersWeight = calculateWeight(incomingContainers);
        long docksContainersWeight = calculateWeight(containers);
        return incomingContainersWeight + docksContainersWeight <= capacity;
    }

    private long calculateWeight(Collection<Container> containers) {
        long currentWeight = 0;
        for(Container container : containers) {
            currentWeight += container.getWeight();
        }
        return currentWeight;
    }

    private void registerShip(String shipName) {
        if(shipGivenContainers.get(shipName) == null) {
            shipGivenContainers.put(shipName, new ArrayList<Long>());
        }
    }

    public Queue<Container> takeContainers(long shipCapacity, String shipName) {
        registerShip(shipName);
        if(calculateWeightWithoutGivenContainers(shipName) == 0) {
            return null;
        }
        Queue<Container> takingContainers = new ArrayDeque<>();
        for(int i = 0; i < containers.size(); i++) {
            Container container = containers.get(i);
            if(!isLoadedThisContainer(shipName, container.getId())
                    && calculateWeight(takingContainers) + container.getWeight() <= shipCapacity) {
                takingContainers.add(container);
                containers.remove(i--);
            }
        }
        return takingContainers;
    }

    private long calculateWeightWithoutGivenContainers(String shipName) {
        long totalWeightWithoutGivenContainers = calculateWeight(containers);
        List<Long> givenContainersIds = shipGivenContainers.get(shipName);
        //List<Container>containersToRemove = new ArrayList<>();
        for(Container container : containers) {
           // containersToRemove.add(container);
            long containerId = container.getId();
            if(givenContainersIds.contains(containerId)) {
                totalWeightWithoutGivenContainers -= container.getWeight();
            }
        }
        //containers.removeAll(containersToRemove);
        return totalWeightWithoutGivenContainers;
    }

    private boolean isLoadedThisContainer(String shipName, long id) {
        List<Long> loadedIds = shipGivenContainers.get(shipName);
        return loadedIds.contains(id);
    }

    public long getId() {
        return id;
    }

}
