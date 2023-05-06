package com.dx.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (SensorData)表实体类
 *
 * @author makejava
 * @since 2023-05-05 21:08:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sensor_data")
public class SensorData  {
    @TableId
    private Long id;

    private Integer deviceId;
    
    private String type;
    
    private Double value;
    
    private String unit;
    
    private Date createTime;
    
    private Date updateTime;
}
