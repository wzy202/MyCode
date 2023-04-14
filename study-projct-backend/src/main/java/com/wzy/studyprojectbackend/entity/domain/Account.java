package com.wzy.studyprojectbackend.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_account")
public class Account implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = -40356785423868312L;

    @TableId
    private Integer id;

    private String email;

    private String userName;

    private String password;

}
