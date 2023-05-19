package com.example.ia.cars;

import java.util.ArrayList;
import java.util.List;

/**
 * Dealership
 * <p>
 * This class models a car dealership with a minimal
 * set of contact information(name, address, telephone)
 * <p>
 * A Dealership has a list of Vehicles
 * <p>
 * The Dealership class provides methods to search the
 * inventory, and add and remove vehicles.
 *
 */
public class Dealership {

    /*
     *  INSTANCE VARIABLES
     */

    // Dealership contact information

    private String name;
    private String address;
    private String phone;

    // Vehicle inventory

    private List<Vehicle> inventory = new ArrayList<>();


    /*
     *  CONSTRUCTOR
     */

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    /*
     *  GET methods for dealership information
     */

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    // Note that this is package private, since it returns
    // the reference to the private member variable.  This
    // is intended for the use of other classes in the same
    // package

    List<Vehicle> getInventory() {
        return inventory;
    }


    /*
     *  VEHICLE FINDER METHODS
     *
     *  Each of these returns a new List with the selected vehicles.
     *  An empty list indicates no vehicles were found.
     *
     *  Note that the finder methods that depend on String objects
     *  use the contains() method rather than equals() to perform
     *  comparisons.  This is similar to selecting in SQL using
     *  a LIKE clause, and means that callers can provide a partial
     *  string for matching
     */

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> values = new ArrayList<>();
        values.addAll(inventory);
        return values;
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        List<Vehicle> values = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getPrice() >= min && v.getPrice() <= max)
                values.add(v);
        }
        return values;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> values = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getMake().contains(make) || v.getModel().contains(model))
                values.add(v);
        }
        return values;
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        List<Vehicle> values = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getPrice() >= min && v.getPrice() <= max)
                values.add(v);
        }
        return values;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> values = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getColor().contains(color))
                values.add(v);
        }
        return values;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        List<Vehicle> values = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getOdometer() >= min && v.getOdometer() <= max)
                values.add(v);
        }
        return values;
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> values = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getVehicleType().contains(vehicleType))
                values.add(v);
        }
        return values;
    }


    /*
     *  ADD and REMOVE methods
     *
     *  These work on the inventory list
     *
     *  Note that they do not save to the inventory data file.
     *  Synchronization with the data file is provided by the
     *  UI and the DelaershipFileManager in this implementation
     *
     *  The remove method expects a vehicle that already
     *  exists in the list.  Callers can use getVehicleByVin()
     *  to verify that a particular vehicle exists, and get
     *  its reference.
     */


    public void addVehicle(Vehicle v) {

        inventory.add(v);
    }

    public void removeVehicle(Vehicle v) {

        inventory.remove(v);
    }

    // Select a particular vehicle by vin
    // Or return null if not found

    public Vehicle getVehicleByVin(int vin) {
        Vehicle vehicle = null;
        for (Vehicle v : inventory) {
            if (v.getVin() == vin) {
                vehicle = v;
            }
        }
        return vehicle;
    }
}
