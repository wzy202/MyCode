package com.wzy.studyprojectbackend.entity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_account")
public class Account {

    @TableId
    private Integer id;

    private String email;

    private String userName;

    private String password;

}
