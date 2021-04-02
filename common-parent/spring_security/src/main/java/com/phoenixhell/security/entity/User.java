package com.phoenixhell.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author phoenixhell
 * @create 2021/2/17 0017-下午 2:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ucenter_member")
public class User implements Serializable {
    @TableField("nickname")
    private String username;
    @ApiModelProperty("密码")
    private String password;

}
