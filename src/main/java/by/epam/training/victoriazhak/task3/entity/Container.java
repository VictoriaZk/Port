package by.epam.training.victoriazhak.task3.entity;

public class Container {
    private long weight;
    private long id;

    public Container(long id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public long getWeight() {
        return weight;
    }

    public long getId() {
        return id;
    }

    //equals, hashcode
}
