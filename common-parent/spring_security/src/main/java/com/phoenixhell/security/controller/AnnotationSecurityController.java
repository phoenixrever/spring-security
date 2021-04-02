package com.phoenixhell.security.controller;

import com.phoenixhell.security.entity.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/2 0002-下午 3:49
 * @EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启配置权限注解
 */
@RestController
@RequestMapping("/security")
public class AnnotationSecurityController {

    @GetMapping("/index")
    public String index(){
        return "security index";
    }

    /**
     * ROLE_ 注解 可以包含多个权限
     * 有任意一种权限即可访问等同hasAnyRole
     * 注意 注解并不会自动加上ROLE_
     * 所有我们要手动加上和ROLE_admin相等
     * 还有ROLE_ 2端都不能少
     *  AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
     *  -----------------------------------------
     *  效果等同
     *  PreAuthorize("hasAnyRole('ROLE_admin,ROLE_customer')")
     */
    @Secured({"ROLE_customer","ROLE_admin"})
    @GetMapping("/role")
    public String role(){
        return "security admin";
    }

   //和Secured 效果相同 适合单个permission
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/preAuthorize")
    public String preAuthorize(){
        return "security PreAuthorize";
    }

    /**
     * 方法先执行在校验  没有权限也会先执行方法
     * 适合验证带有返回值的权限
     */

    @PostAuthorize("hasAnyAuthority('postAuthorize')")
    @GetMapping("/postAuthorize")
    public String postAuthorize(){
        return "security PostAuthorize";
    }

    /**
     * 权限验证过后对返回数据进行过滤吗
     * 对返回的list进行过滤只返回username=admin1的
     */

    @PostAuthorize("hasAnyAuthority('postfilter')")
    @PostFilter("filterObject.username=='admin1'")
    @GetMapping("/postfilter")
    public List<User> postfilter(){
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("admin1","password1"));
        list.add(new User("admin2","password2"));
        return list;
    }

    /**
     * 权限验证过后进入控制器之前对参数进行过滤吗
     * 传参数直传满足过滤条件的
     */

    @PreAuthorize("hasAnyAuthority('prefilter')")
    @PreFilter("filterObject.id%2==0")
    @GetMapping("/prefilter")
    public List<User> preFilter(@RequestBody List<User> list){
        list.forEach(System.out::println);
        return list;
    }
}
