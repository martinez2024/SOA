/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author abiga
 */
public class Excel {
    public static void main(String[] args) {
    try {
      String excelFilePath = "C:\\Users\\abiga\\Documents\\SOA\\Unidad 2\\LAYOUT_ENTRADA.xlsx";
      FileInputStream inputStream = new FileInputStream(excelFilePath);
      XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(inputStream);
      Sheet sheet = xSSFWorkbook.getSheetAt(0);
      List<List<String>> excelData = new ArrayList<>();
      for (Row row : sheet) {
        List<String> rowData = new ArrayList<>();
        for (Cell cell : row)
          rowData.add(cell.toString()); 
        excelData.add(rowData);
      } 
      inputStream.close();
      for (List<String> rowData : excelData) {
        for (String cellValue : rowData)
          System.out.print(cellValue + "\t"); 
        System.out.println();
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
    
}
