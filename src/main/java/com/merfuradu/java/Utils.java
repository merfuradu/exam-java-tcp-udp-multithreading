package com.merfuradu.java;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static List<Vehicle> vehicles;

    // 4.a.
    public static List<Vehicle> createCars(int n) throws Exception {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        for (int i = 0; i < n; i++) {
            vehicles.add(new Car());
        }
        return vehicles;
    }

    // 4.b.
    public static List<Vehicle> readCars(String file) {
        List<Vehicle> cars = new ArrayList<Vehicle>();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = raf.readLine()) != null) {
                float weight = Float.parseFloat(line);
                line = raf.readLine();
                double price = Double.parseDouble(line);
                line = raf.readLine();
                String producer = line;

                Car car = new Car();
                car.setPrice(price);
                car.setWeight(weight);
                car.setProducer(producer);
                vehicles.add(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cars;

    }

    // 4.c.
    public static void writeBinaryCars(String file, List<Vehicle> listP) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Vehicle vehicle : listP) {
                oos.writeObject(vehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 4.d.
    public static ArrayList<Vehicle> readBinaryCars(String file) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Vehicle vehicle = (Vehicle) ois.readObject();
                    vehicles.add(vehicle);
                } catch (EOFException eofe) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

}
