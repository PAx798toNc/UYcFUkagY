// 代码生成时间: 2025-09-21 07:07:29
package com.example.demo.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
# 添加错误处理

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Service
public class ExcelGeneratorService {

    private static final String EXCEL_EXTENSION = ".xlsx";
    private static final int INITIAL_CAPACITY = 100;
    private static final int MAX_ROWS = 65535;

    /**
     * Generates an Excel file and writes it to the provided OutputStream.
     *
     * @param data The data to be written into the Excel file.
     * @param outputStream The OutputStream to write the Excel file to.
     * @return The generated Excel workbook.
     * @throws IOException If an I/O error occurs when reading or writing files.
     */
    public Workbook generateExcel(ArrayList<String[]> data, OutputStream outputStream) throws IOException {

        try {
            Workbook workbook = new XSSFWorkbook();
# NOTE: 重要实现细节
            // Create a new sheet
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Generated Data");
# 扩展功能模块

            // Write data to sheet
            for (int rowNum = 0; rowNum < data.size(); rowNum++) {
                String[] row = data.get(rowNum);
                org.apache.poi.ss.usermodel.Row sheetRow = sheet.createRow(rowNum);
                for (int colNum = 0; colNum < row.length; colNum++) {
                    org.apache.poi.ss.usermodel.Cell cell = sheetRow.createCell(colNum);
                    cell.setCellValue(row[colNum]);
                }
            }

            // Write workbook to output stream
            workbook.write(outputStream);
            workbook.close();
            return workbook;

        } catch (IOException e) {
            // Log the error and rethrow it
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Checks if the uploaded file is an Excel file.
     *
     * @param file The file to check.
     * @return true if the file is an Excel file, false otherwise.
     */
# FIXME: 处理边界情况
    public boolean isExcelFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && filename.endsWith(EXCEL_EXTENSION);
    }
}
