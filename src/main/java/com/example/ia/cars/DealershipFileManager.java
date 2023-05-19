package com.example.ia.cars;

import java.io.*;

/**
 * DealershipFileManager
 *
 * This class reads and writes the dealership inventory file
 *
 */

public class DealershipFileManager {

    private String inventoryFilename;

    public DealershipFileManager(String filename) {
        this.inventoryFilename = filename;
    }


    // READ .csv file and return a Dealership

    public Dealership getDealership(){

        Dealership d = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(inventoryFilename));

            // READ Dealership information
            String input = br.readLine();
            String[] split = input.split("\\|");
            String name = split[0];
            String address = split[1];
            String phoneNumber = split[2];


            d = new Dealership(name, address, phoneNumber);

            // READ Vehicles
            while (null != (input = br.readLine())) {
                Vehicle v = parseVehicle(input);
                d.addVehicle(v);
            }

            br.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return d;
    }



    // WRITE the dealership file by completely
    // replacing it.
    //

    public void saveDealership(Dealership dealership){
        try {

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(inventoryFilename, false));


            // WRITE Dealership information to first line
            StringBuilder sb = new StringBuilder();
            sb.append(dealership.getName());
            sb.append("|");
            sb.append(dealership.getAddress());
            sb.append("|");
            sb.append(dealership.getPhone());

            bw.write(sb.toString());

            // WRITE ALL vehicles to file
            // Each is preceded by a newline
            for (Vehicle v: dealership.getInventory()){
                bw.write("\n");
                bw.write(toFileString(v));
            }

            bw.flush();
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /*
     *  Produces the String representation of a vehicle that
     *  will be saved in the inventory data file
     */
    String toFileString(Vehicle v){
        return String.format("%d|%d|%s|%s|%s|%s|%d|%.2f",
                v.getVin(),
                v.getYear(),
                v.getMake(),
                v.getModel(),
                v.getVehicleType(),
                v.getColor(),
                v.getOdometer(),
                v.getPrice());
    }

    /*
     * This method parses the data file representation of
     * a vehicle (as a String) and produces a Vehicle object.
     *
     * Parse errors here will be fatal.  The data file representation
     * is expected to be "perfect"
     */

    Vehicle parseVehicle(String string){

        String [] split = string.split("\\|");

        int vin = Integer.parseInt(split[0]);
        int year = Integer.parseInt(split[1]);
        String make = split[2];
        String model = split[3];
        String vehicleType = split[4];
        String color = split[5];
        int odometer = Integer.parseInt(split[6]);
        double price = Double.parseDouble(split[7]);

        Vehicle v = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

        return v;
    }

}
