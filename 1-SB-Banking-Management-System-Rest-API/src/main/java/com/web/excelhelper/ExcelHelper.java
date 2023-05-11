package com.web.excelhelper;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.web.model.Transaction;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Transaction_number", "Accountnumber", "Fullname", "Address", "Date", "Debit", "Credit", "Currentbalance" };
  static String SHEET = "Transaction";

  public static ByteArrayInputStream transactionToExcel(List<Transaction> transaction) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Transaction transactions : transaction) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(transactions.getTransaction_number());
        row.createCell(1).setCellValue(transactions.getAccountnumber());
        row.createCell(2).setCellValue(transactions.getFullname());
        row.createCell(3).setCellValue(transactions.getAddress());
        row.createCell(4).setCellValue(transactions.getDate());
        row.createCell(5).setCellValue(transactions.getDebit());
        row.createCell(6).setCellValue(transactions.getCredit());
        row.createCell(7).setCellValue(transactions.getCurrentbalance());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

}
