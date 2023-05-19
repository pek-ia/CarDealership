package com.example.ia.cars;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private DealershipFileManager dealershipFileManager;

    String filename = "inventory.csv";
    Scanner scanner = new Scanner(System.in);


    /*
     *  PUBLIC METHODS
     */

    // Display the User Interface and handle user input

    public void display() {
        init();


        boolean done = false;
        while (!done) {
            // Display choices

            System.out.printf("""
                    ===================================================
                    %s
                    %s, %s
                    ===================================================
                    """,
                    dealership.getName(), dealership.getAddress(), dealership.getPhone());

            System.out.print("""
                    
                       Vehicle Inventory
                ================================
                
                    1   Search by Price
                    2   Search by Make/Model
                    3   Search by Year
                    4   Search by Color
                    5   Search by Mileage
                    6   Search by Type
                    7   Show All Vehicles
                    8   ADD a vehicle
                    9   REMOVE a vehicle
                    
                   99   Quit
                    
                   Please select your choice (by number): 
                   """);
            // Get user input
            String input = scanner.nextLine().trim();

            // Dispatch to appropriate method
            switch(input){
                case "1" -> processByPriceRequest();
                case "2" -> processByMakeModelRequest();
                case "3" -> processByYearRequest();
                case "4" -> processByColorRequest();
                case "5" -> processByMileageRequest();
                case "6" -> processByVehicleTypeRequest();
                case "7" -> processAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "99"-> {done=true;}
                default -> badInput();

            }
        }
    }

    /*
     *  PRIVATE METHODS TO HANDLE USER INPUT
     */

    private void badInput() {
        System.out.println("Please select a menu option");
    }

    private void processAllVehiclesRequest() {
        List<Vehicle> list = dealership.getAllVehicles();
        displayVehicles(list);
    }

    private void processByYearRequest() {
        int min = LocalDate.now().getYear();
        int max = LocalDate.now().getYear();

        System.out.println("Enter preferred model year(s):");
        min = requestIntegerInput("Earliest: ", min);
        max = requestIntegerInput("Latest:  ", max);

        List<Vehicle> list = dealership.getVehiclesByYear(min, max);
        displayVehicles(list);
    }

    private void processByMakeModelRequest() {
        String make = "";
        String model = "";

        System.out.println("Enter preferred make and model:");
        make = requestStringInput("Make: ", make);
        model = requestStringInput("Model:  ", model);

        List<Vehicle> list = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(list);
    }

    private void processByPriceRequest() {
        double min = 0.0;
        double max = Double.MAX_VALUE;

        System.out.println("Enter preferred price range:");
        min = requestDoubleInput("Minimum: ", min);
        max = requestDoubleInput("Maximum: ", max);

        List<Vehicle> list = dealership.getVehiclesByPrice(min, max);
        displayVehicles(list);
    }

    private void processByColorRequest() {
        String color = "black";

        color = requestStringInput("Enter preferred color: ", color);
        List<Vehicle> list = dealership.getVehiclesByColor(color);

        displayVehicles(list);
    }

    private void processByMileageRequest() {
        int min = 0;
        int max = Integer.MAX_VALUE;

        System.out.println("Enter preferred mileage range:");
        min = requestIntegerInput("Minimum: ", min);
        max = requestIntegerInput("Maximum: ", max);

        List<Vehicle> list = dealership.getVehiclesByMileage(min, max);
        displayVehicles(list);
    }

    private void processByVehicleTypeRequest() {
        String vehicleType = "";
        vehicleType = requestStringInput("Enter preferred vehicle type: ", vehicleType);
        List<Vehicle> list = dealership.getVehiclesByType(vehicleType);
        displayVehicles(list);
    }


    // ADD a vehicle to the Dealership

    private void processAddVehicleRequest() {

        int vin = requestIntegerInput("VIN: ", 0);
        int year = requestIntegerInput("Model year: ", LocalDate.now().getYear());
        String make = requestStringInput("Make: ", "Unknown");
        String model = requestStringInput("Model: ", "Unknown");
        String vehicleType = requestStringInput("Type: ", "Unknown");
        String color = requestStringInput("Color: ", "Unknown");
        int odometer = requestIntegerInput("Odometer: ", 0);
        double price = requestDoubleInput("Price: ", 0.0);

        Vehicle v = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

        dealership.addVehicle(v);
        dealershipFileManager.saveDealership(dealership);

    }

    /**
     * ADD and REMOVE processing
     *
     * When vehicles are added or removed from the dealership,
     * the inventory file is also saved.
     *
     */

    // REMOVE a vehicle

    private void processRemoveVehicleRequest() {

        int vin = requestIntegerInput("VIN: ", 0);

        Vehicle v = dealership.getVehicleByVin(vin);

        if (v != null ) {
            dealership.removeVehicle(v);
            dealershipFileManager.saveDealership(dealership);
        }

    }





    /*
     *  PRIVATE HELPER METHODS
     */

    // Initialize the dealership
    private void init(){

        dealershipFileManager = new DealershipFileManager(filename);
        dealership = dealershipFileManager.getDealership();

    }

    // Display a list of Vehicles
    private void displayVehicles(List<Vehicle> vehicles){
        System.out.println("Vehicles matching your request: ");
        for (Vehicle v: vehicles){
            displayOneVehicle(v);
        }
        System.out.println();  // add an extra line for spacing
        requestStringInput("Hit ENTER to continue...", "");
    }


    // Display a single vehicle, nicely formatted
    private void displayOneVehicle(Vehicle v) {
        String format = "%-10d %-7d %-25s %-20s %15s %15s %15d %15.2f \n";
        System.out.printf( format,
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
     *  USER INPUT HELPER METHODS
     *
     *  Display a prompt & read input from CLI
     *
     *  Return default value if input is not available
     *  or cannot be parsed.
     */

    private String requestStringInput(String prompt, String defaultValue){
        System.out.print(prompt);
        String input =  scanner.nextLine();
        String value = input.equals("") ? defaultValue : input;
        return value;
    }


    private int requestIntegerInput(String prompt, int defaultValue){
        System.out.print(prompt);
        String input = scanner.nextLine();

        int value = defaultValue;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException ignored){}
        return value;
    }

    private double requestDoubleInput(String prompt, double defaultValue){
        System.out.print(prompt);
        String input = scanner.nextLine();

        double value = defaultValue;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException ignored){ }

        return value;
    }
}
