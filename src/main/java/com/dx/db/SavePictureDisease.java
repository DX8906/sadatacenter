package com.dx.db;

import com.dx.mapper.PictureMapper;
import com.dx.entity.Picture;
import org.apache.ibatis.session.SqlSession;
import java.sql.Date;

/**
 * @author 32825
 */
public class SavePictureDisease {
    public static void save(String picturePath,String diseaseCategory){
        SqlSession session = GetSession.getSession();
        PictureMapper mapper = session.getMapper(PictureMapper.class);
        Picture userEntity = new Picture();
        userEntity.setPicturePath(picturePath);
        userEntity.setDiseaseCategory(diseaseCategory);
        java.util.Date date = new java.util.Date();
        long times = date.getTime();
        userEntity.setCreateTime(new Date(times));
        userEntity.setUpdateTime(new Date(times));
        mapper.insert(userEntity);
        session.commit();
    }
}
