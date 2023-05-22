package com.example.ia.cars;

public class SalesContract extends Contract {
    public SalesContract(String date, String customer_name, String customer_email, Vehicle vehicle) {
        super(date, customer_name, customer_email, vehicle);
    }

    @Override
    public double getTotalPrice() {
        return 0;
    }

    @Override
    public double getMonthlyPayment() {
        return 0;
    }
}
