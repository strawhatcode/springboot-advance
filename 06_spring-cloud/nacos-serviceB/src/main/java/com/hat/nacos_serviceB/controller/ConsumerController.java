package com.hat.nacos_serviceB.controller;

import com.hat.nacos_serviceB.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/service")
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    FeignService feignService;  // 注入刚才声明的FeignService接口

    @GetMapping("/getA")
    public String getA(String msg){
        return restTemplate.getForObject("http://nacos-serviceA/serviceA/provider?msg="+msg,String.class);
    }

    @GetMapping("/getByFeign")
    public String getByFeign(String msg){
        return feignService.getProvider(msg);
    }


    @RequestMapping(value = "/**",method = {RequestMethod.POST,RequestMethod.GET})
    public String getAnyFeign(HttpServletRequest request,
                              HttpServletResponse response,
                              String msg,
                              @RequestParam(name = "val",required = false) String val){

        HttpServletResponse res = response;
        res.addHeader("testHeader","headerAAA");
        res.addHeader("testHeader","headerAAA");
        res.addHeader("testHeader","headerBBB");
        HttpSession session = request.getSession();
//        res.addHeader("Location","http://www.baidu.com/v2/search/");
//        res.setStatus(303);
        String header1 = res.getHeader("Test-Header");
        System.out.println(header1);
        return feignService.getProvider(request.getRequestURI()+"-----"+msg);
    }
}
