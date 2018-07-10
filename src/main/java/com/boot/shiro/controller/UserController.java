package com.boot.shiro.controller;

import com.boot.shiro.entity.User;
import com.boot.shiro.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String toUserlistPage(){

        return "page/user/user_list";
    }

    @RequestMapping("/userList")
    @ResponseBody
    public Map<String, Object> findAll(@RequestParam(value = "page",required = false) Integer page,@RequestParam(value = "pageSize",required = false) Integer pageSize){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (pageSize == null){
            pageSize = 10;
        }
        PageInfo<User> pager = userService.findAll(page,pageSize);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        //总条数
        resultMap.put("count", pager.getTotal());
        //获取每页数据
        resultMap.put("data", pager.getList());


        return resultMap;
    }

    @RequestMapping("/toUserPassword")
    public String toUserPassword(Integer userId, Model model){
        User user = userService.selectOneByPrimaryKey(userId);
        user.setPassword(null);
        model.addAttribute("user",user);
        return "page/user/user_password";
    }

    @RequestMapping("/updateUserTypeById")
    @ResponseBody
    public String updateUserTypeById(User user){
        boolean b = userService.updateUserTypeById(user);
        if (b){
            return user.getType().toString();
        }
        return "error";
    }

    @RequestMapping("/toUserEdit")
    public String toUserEdit(User user, Model model){
        User user1 = userService.selectOneByPrimaryKey(user.getUserId());
        user1.setPassword(null);
        model.addAttribute("user",user1);
        return "page/user/user_edit";
    }
    @RequestMapping("/updateUserById")
    public String updateUserById(User user,Model model){
        boolean b = userService.updateUserById(user);
        User user1 = userService.selectOneByPrimaryKey(user.getUserId());
        if (b){
            model.addAttribute("msg","保存成功");
        }else {
            model.addAttribute("msg","保存失败");
        }
        model.addAttribute("user",user1);
        return "page/user/user_edit";
    }
    @RequestMapping("/updatePasswordById")
    @ResponseBody
    public String updatePasswordById(User user){
        boolean b = userService.updateUserById(user);
        if (b){
            return "success";
        }
        return "error";
    }
}
