package model;

import exception.StatusNotFoundException;
import lombok.Getter;

@Getter
public enum Status {

    PENDING("1"),
    PREPARING("2"),
    READY("3"),
    DELIVERED("4");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public static Status getStatusByCode(String code) {
        for (Status value : Status.values() ){
            if (value.getCode().equals(code)){
                return value;
            }
        }
        throw new StatusNotFoundException("Status with code " + code + " not found");
    }
}

