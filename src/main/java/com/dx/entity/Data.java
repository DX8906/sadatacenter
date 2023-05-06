package com.dx.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    public String type;
    public int device_id;
    public Double value;
    public String unit;
}
