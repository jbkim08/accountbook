package com.example.accountbook.service;

import com.example.accountbook.model.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    //트랜젝션 리스트를 입력받아서 엑셀로 변환(쓰기) => 다운로드가 가능하도록 입력스트림으로 리턴
    public ByteArrayInputStream transactionsToExcel(List<Transaction> transactions) throws IOException {
        String[] columns = {"ID", "내역", "금액", "유형", "카테고리", "등록일"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("가계부 내역"); //엑셀시트 제목

            // 헤더 스타일 설정
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // 헤더 행 생성
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // 데이터 행 삽입
            int rowIdx = 1;
            for (Transaction t : transactions) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(t.getId());
                row.createCell(1).setCellValue(t.getTitle());
                row.createCell(2).setCellValue(t.getAmount());
                row.createCell(3).setCellValue(t.getType());
                row.createCell(4).setCellValue(t.getCategory());
                row.createCell(5).setCellValue(t.getRegDate().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
