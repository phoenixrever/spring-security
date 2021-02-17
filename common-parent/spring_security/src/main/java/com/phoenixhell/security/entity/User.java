package com.phoenixhell.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author phoenixhell
 * @create 2021/2/17 0017-下午 2:39
 */
@Data
@ApiModel(description = "用户实体类")
public class User implements Serializable {
    @ApiModelProperty("微信openid")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("用户头像")
    private String salt;
    @ApiModelProperty("用户签名")
    private String token;
}
