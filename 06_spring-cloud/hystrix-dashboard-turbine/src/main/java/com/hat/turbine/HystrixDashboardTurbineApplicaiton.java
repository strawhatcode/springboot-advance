package com.hat.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine    //开启turbine
@EnableHystrixDashboard   //开启dashboard
public class HystrixDashboardTurbineApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardTurbineApplicaiton.class,args);
    }
}
