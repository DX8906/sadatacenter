package com.dx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.dx.utils.ImageToTensorUtils;

/**
 * @author 32825
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PredictJson {

    String signature_name;

    float [][][][] instances;

    public PredictJson(String picturePath){
        signature_name="serving_default";
        instances= ImageToTensorUtils.getTensor(picturePath);
    }
}
