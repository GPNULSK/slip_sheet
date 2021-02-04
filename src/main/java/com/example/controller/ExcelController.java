package com.example.controller;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.net.URLDecoder;

@CrossOrigin
@RestController
public class ExcelController {

    @ApiIgnore
    @RequestMapping(value = "/downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse res, HttpServletRequest req, String name) throws Exception {
        String fileName = "物料导入模板.xls";
        ServletOutputStream out;
        res.setContentType("multipart/form-data");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        String filePath = getClass().getResource("/excel/" + fileName).getPath();
        String userAgent = req.getHeader("User-Agent");
        System.out.println(userAgent);
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
        // 非IE浏览器的处理：
               fileName = new String((fileName).getBytes("UTF-8"), "ISO-8859-1");
        }
        filePath = URLDecoder.decode(filePath, "UTF-8");
        res.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        FileInputStream inputStream = new FileInputStream(filePath);
        out = res.getOutputStream();
        int b = 0;
        byte[] buffer = new byte[1024];
        while ((b = inputStream.read(buffer)) != -1) {
        // 4.写到输出流(out)中
        out.write(buffer, 0, b);
        }
        inputStream.close();

        if (out != null) {
        out.flush();
        out.close();
        }

    }

}
