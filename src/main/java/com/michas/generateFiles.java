package com.michas;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class generateFiles {
    public generateFiles() {
    }

    public FileInputStream addfile(String name) {

        try {

            FileInputStream file = new FileInputStream(new File("./src/main/resources/" + name + ".xlsx"));
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Sheet makeSheet(String name){
        try {
            Workbook workbook = new XSSFWorkbook(addfile(name));
            Sheet sheet = workbook.getSheetAt(0);
            workbook.close();
            return sheet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }





}
