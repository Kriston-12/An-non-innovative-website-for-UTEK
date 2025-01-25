package com.utek.disasterrelief.demos.util;


import com.utek.disasterrelief.demos.entity.Resource;
import com.utek.disasterrelief.demos.mapper.ResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Slf4j
public class ExcelUtil {


    public static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, List<String> str) {
        try {
            HSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i < str.size(); i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //创建表头名称
            HSSFCell cell;
            for (int j = 0; j < str.size(); j++) {
                cell = row.createCell(j);
                cell.setCellValue(str.get(j));
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！");
            e.printStackTrace();
        }
    }

    public static void setData(HSSFSheet sheet, List<String[]> data) {
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0; j < data.get(i).length; j++) {
                    row.createCell(j).setCellValue(data.get(i)[j]);
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    public static <T> void  setDataObject(HSSFSheet sheet, List<T> data,List<String> headKey,Class<T> clazz) {
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0;j<headKey.size();j++){
                    Object o;
                    String name = "get"+headKey.get(j).substring(0,1).toUpperCase()
                            + headKey.get(j).substring(1);
                    Method mo =  clazz.getMethod(name);
                    if(mo.getReturnType().getName().equals(Date.class.getName())){
                        Date date = (Date) mo.invoke(data.get(i));
                        if(date == null){
                            row.createCell(j).setCellValue("");
                            continue;
                        }
                        o=Utils.tranSysDate(date);
                    }else {
                        o = mo.invoke(data.get(i));
                    }
                    if(o==null){
                        row.createCell(j).setCellValue("");
                        continue;
                    }
                    row.createCell(j).setCellValue(o.toString());
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    public static <T> void  setDataObject(HSSFSheet sheet, List<Resource> data, List<String> headKey, Class<ResourceMapper> clazz, String format) {
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0;j<headKey.size();j++){
                    Object o;
                    String name = "get"+headKey.get(j).substring(0,1).toUpperCase()
                            + headKey.get(j).substring(1);
                    Method mo =  clazz.getMethod(name);
                    if(mo.getReturnType().getName().equals(Date.class.getName())){
                        Date date = (Date) mo.invoke(data.get(i));
                        if(date == null){
                            row.createCell(j).setCellValue("");
                            continue;
                        }
                        o=Utils.tranSysDate(date,format);
                    }else {
                        o = mo.invoke(data.get(i));
                    }
                    if(o==null){
                        row.createCell(j).setCellValue("");
                        continue;
                    }
                    row.createCell(j).setCellValue(o.toString());
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    /**
     * 方法名：setBrowser
     * 功能：使用浏览器下载
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static void setBrowser(HttpServletResponse response, HSSFWorkbook workbook,String name) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            if(Utils.isBlank(name)){
                response.addHeader("Content-Disposition", "attachment;filename=" + "file.xls");
            }else {
                response.addHeader("Content-Disposition", "attachment;filename=" + name+".xls");
            }
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }
    }

    public static void setBrowser(HttpServletRequest httpServletRequest,HttpServletResponse response, HSSFWorkbook workbook, String name) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            if(Utils.isBlank(name)){
                response.addHeader("Content-Disposition", "attachment;filename=" + "file.xls");
            }else {
                response.addHeader("Content-Disposition", "attachment;filename=" + name+".xls");
            }
            response.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("origin"));
            //该字段可选，是个布尔值，表示是否可以携带cookie
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "*");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }
    }
    public static void setDataObject(HSSFSheet sheet, List<Map<String, Object>> data, List<String> headKey) {
        try{
            int rowNum = 1;
            for (Map<String, Object> map :data) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0; j < headKey.size(); j++) {
                    String key = headKey.get(j);
                    row.createCell(j).setCellValue(map.containsKey(key)?map.get(key).toString():"0");
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

}

