package com.dx.db;

import com.dx.entity.SensorData;
import com.dx.mapper.SensorDataMapper;
import org.apache.ibatis.session.SqlSession;
import com.dx.entity.Result;

import java.sql.Date;

/**
 * @author 32825
 */
public class SaveSensorData {
    public static void saveSensorData(Result result){
        SqlSession session = GetSession.getSession();
        SensorDataMapper mapper = session.getMapper(SensorDataMapper.class);
        SensorData userEntity = new SensorData();
        userEntity.setDeviceId(result.data.device_id);
        userEntity.setType(result.data.type);
        userEntity.setValue(result.data.value);
        userEntity.setUnit(result.data.unit);
        java.util.Date date = new java.util.Date();
        long times = date.getTime();
        userEntity.setCreateTime(new Date(times));
        userEntity.setUpdateTime(new Date(times));
        mapper.insert(userEntity);
        session.commit();
    }
}
