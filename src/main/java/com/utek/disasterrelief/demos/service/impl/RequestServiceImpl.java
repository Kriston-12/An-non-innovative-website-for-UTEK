package com.utek.disasterrelief.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.utek.disasterrelief.demos.entity.Resource;
import com.utek.disasterrelief.demos.enum1.ResourceNum;
import com.utek.disasterrelief.demos.mapper.ResourceMapper;
import com.utek.disasterrelief.demos.service.RequestService;
import com.utek.disasterrelief.demos.util.ExcelUtil;
import com.utek.disasterrelief.demos.util.Utils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements RequestService {

    @Autowired ResourceMapper resourceMapper;

    @Override
    public HSSFWorkbook exportRequest() throws IOException {
        // 查询数据库中的数据
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "address", "instant_noodles", "tissue_paper", "towel", "water");
        List<Resource> list = resourceMapper.selectList(queryWrapper);

        if (list == null || list.isEmpty()) {
            throw new IOException("No data found in the database.");
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Resource Requests");

        List<String> headers = List.of("Name", "Address", "Instant Noodles", "Tissue Paper", "Towel", "Water");
        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i));
        }

        int rowIdx = 1;
        for (Resource resource : list) {
            HSSFRow row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(resource.getName());
            row.createCell(1).setCellValue(resource.getAddress());
            row.createCell(2).setCellValue(resource.getInstantNoodles());
            row.createCell(3).setCellValue(resource.getTissuePaper());
            row.createCell(4).setCellValue(resource.getTowel());
            row.createCell(5).setCellValue(resource.getWater());
        }

        return workbook;
    }

}
