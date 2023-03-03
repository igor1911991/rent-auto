package com.project.rentauto.exeptions;

public class ChangeStatusAutoException extends RuntimeException{

    public ChangeStatusAutoException(String message) {
        super("Machine included in the order: " + message);
    }

}
