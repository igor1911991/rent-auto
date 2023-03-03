package com.project.rentauto.exeptions;

public class CreateOrderException extends RuntimeException{

    public CreateOrderException() {
        super("Already have an order");
    }

}
