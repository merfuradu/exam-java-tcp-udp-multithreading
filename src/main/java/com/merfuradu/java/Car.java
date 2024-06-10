package com.merfuradu.java;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Vehicle, Serializable, Cloneable {
    private float weight;
    private double price;
    private String producer;

    public Car() {
    }

    public float getWeight() {
        return this.weight;
    }

    public double getPrice() {
        return this.price;
    }

    public String getProducer() {
        return this.producer;
    }

    public void setWeight(float weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public void setProducer(String producersname) {
        if (producer == null || producer.length() <= 1) {
            throw new IllegalArgumentException();
        }
        this.producer = producersname;
    }

    @Override
    public String infoVehicle() {
        return this.producer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Car car = (Car) obj;
        return Double.compare(this.price, price) == 0 &&
                Float.compare(this.weight, weight) == 0 &&
                Objects.equals(producer, car.producer);
    }

    @Override
    public Car clone() {
        try {
            Car clonedCar = (Car) super.clone();
            return clonedCar;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
