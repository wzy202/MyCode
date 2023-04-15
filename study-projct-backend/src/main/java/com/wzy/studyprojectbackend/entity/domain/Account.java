package com.wzy.studyprojectbackend.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_account")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @TableId
    private Integer id;

    private String email;

    private String userName;

    private String password;

}
