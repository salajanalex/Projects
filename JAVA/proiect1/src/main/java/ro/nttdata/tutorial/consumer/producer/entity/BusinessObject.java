package ro.nttdata.tutorial.consumer.producer.entity;

import java.io.Serializable;

public class BusinessObject implements Serializable {
    private final String name;

    public BusinessObject(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BusinessObject [name = " + name + "]";
    }
}