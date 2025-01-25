package com.utek.disasterrelief.demos.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.utek.disasterrelief.demos.entity.Resource;
import com.utek.disasterrelief.demos.mapper.ResourceMapper;
import com.utek.disasterrelief.demos.mapper.UserMapper;
import com.utek.disasterrelief.demos.service.EmailService;
//import com.utek.disasterrelief.demos.service.RequestService;
import com.utek.disasterrelief.demos.service.RequestService;
import com.utek.disasterrelief.demos.util.ExcelUtil;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
public class userController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    RequestService requestService;


    @PostMapping("/getResources")
    public ResponseEntity<Map<String, String>> getResources(@RequestBody Resource resource) throws javax.mail.MessagingException {
        resourceMapper.insert(resource);
        System.out.println(resource.getAddress());
//        System.out.println(resource.getId());

        System.out.println(resource.getInstantNoodles());
        System.out.println(resource.getTissuePaper());
        System.out.println(resource.getName());
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        String htmlContent = "<html>" +
                "<body style='margin:0; padding:0; font-family: Arial, sans-serif; background: url(\"https://publichealth.jhu.edu/sites/default/files/styles/article_feature_retina/public/2024-07/flood-cat.jpg\") no-repeat center center fixed; background-size: cover;'>" +

                "<div style='max-width: 600px; margin: 20px auto; background: rgba(255, 255, 255, 0.9); padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);'>" +

                "<h2 style='color: blue; text-align: center;'>Disaster Relief Support</h2>" +
                "<p style='font-size: 16px; color: #333;'>Hello,</p>" +
                "<p style='font-size: 16px; color: #333;'>Your relief request has been received successfully.</p>" +

                "<ul style='font-size: 16px; color: #555;'>" +
                "<li><b>Towel:</b> " + queryWrapper.select("towel") + "</li>" +
                "<li><b>Instant Noodles:</b> " + queryWrapper.select("instant_noodles") + "</li>" +
                "<li><b>Tissue Paper:</b> " + queryWrapper.select("tissue_paper") + "</li>" +
                "<li><b>Water:</b> " + queryWrapper.select("water") + "</li>" +
                "</ul>" +

                "<p style='font-size: 16px; color: #333;'>Thank you for reaching out!</p>" +
                "</div>" +
                "</body>" +
                "</html>";
        emailService.sendEmail("sihan.zhou@mail.utoronto.ca", "you are soo good", "aaaaa");
        return ResponseEntity.ok(Map.of("message","You are good"));
    }

    @GetMapping("/all-resource")
    public Resource getAllResources() {
        return resourceMapper.selectRandomResource();
    }


    @GetMapping("/exportRequest")
    public void exportResourceRequest(HttpServletResponse response, HttpServletRequest request) throws IOException {
        HSSFWorkbook hssfWorkbook = requestService.exportRequest();
        ExcelUtil.setBrowser(request, response, hssfWorkbook, "user");
    }
}
