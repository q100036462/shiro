package com.boot.shiro.listener;


import com.boot.shiro.entity.Role;
import com.boot.shiro.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
@Configuration
public class ApplicationListener implements ServletContextListener {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==================初始化======================");
        try {
            ServletContext application = sce.getServletContext();
            List<Role> roles = roleMapper.selectAll();
            System.out.println(roles);
            application.setAttribute("roles", roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
