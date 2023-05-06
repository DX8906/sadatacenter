package com.dx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 32825
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PredictResult {
    float [][] predictions;
}
