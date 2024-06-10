package com.merfuradu.java;

import java.util.ArrayList;
import java.lang.Runnable; // Add this import statement

public class VectorThread implements Runnable {
    private ArrayList<Vehicle> carList;
    private double avgWeight;

    public VectorThread(String file) {
        this.carList = Utils.readBinaryCars(file);
    }

    public ArrayList<Vehicle> getCarList() {
        return this.carList;
    }

    public double getAvgWeight() {
        return this.avgWeight;
    }

    @Override
    public void run() {
        int count = 0;
        double sumOfWeights = 0;
        for (Vehicle vehicle : carList) {
            Car car = (Car) vehicle;
            sumOfWeights += car.getWeight();
            count++;
        }
        this.avgWeight = sumOfWeights / count + (sumOfWeights % count);
    }

}
