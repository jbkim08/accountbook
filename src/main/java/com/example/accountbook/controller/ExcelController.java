package com.example.accountbook.controller;

import com.example.accountbook.service.ExcelService;
import com.example.accountbook.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ExcelController {
    // 자동 생성자 주입
    private final TransactionService transactionService;
    private final ExcelService excelService;

    @GetMapping("/download/excel")
    public ResponseEntity<InputStreamResource> downloadExcel(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        // 전체 내역 조회 (페이징 없이 전체 출력)
        ByteArrayInputStream in = excelService.transactionsToExcel(transactionService.getAllTransactions(userDetails.getUsername()));

        HttpHeaders headers = new HttpHeaders();
        //브라우저가 파일을 다운로드 하도록 지정, attachment 첨부파일로 다운로드 account_book.xlsx 파일명
        headers.add("Content-Disposition", "attachment; filename=account_book.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
