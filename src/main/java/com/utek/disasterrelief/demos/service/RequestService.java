package com.utek.disasterrelief.demos.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface RequestService {
    HSSFWorkbook exportRequest() throws IOException;
}
