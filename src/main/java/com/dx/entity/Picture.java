package com.dx.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Picture)表实体类
 *
 * @author makejava
 * @since 2023-05-05 21:30:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("picture")
public class Picture  {
    @TableId
    private Long id;

    
    private String picturePath;
    
    private String diseaseCategory;
    
    private Date createTime;
    
    private Date updateTime;
}
