package com.drools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfo {

    private String make;
    private String model;
    private String fuelType;
    private String variant;
    private Integer cubicCapacity=null;
    private Integer seatingCapacity=null;

}