package com.dx.executor;

import com.dx.db.SavePictureDisease;
import com.dx.utils.Base64ToImageUtils;
import com.dx.enums.DiseaseCategory;
import com.dx.utils.TomatoDiseasePredictUtils;

/**
 * @author 32825
 */
public class TomatoDiseaseExecutor {
    String picturePath="";
    String diseaseCategory="";
    final String foldPath="/tomatoPicture/";
    public void executeTomatoDisease(String base64){
        picturePath= Base64ToImageUtils.generateImage(base64,foldPath);
        int type= TomatoDiseasePredictUtils.predict(picturePath);
        DiseaseCategory dc=DiseaseCategory.class.getEnumConstants()[type];
        diseaseCategory=dc.toString();
        SavePictureDisease.save(picturePath,diseaseCategory);
    }
}
