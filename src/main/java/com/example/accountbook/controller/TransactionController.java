package com.example.accountbook.controller;

import com.example.accountbook.model.Transaction;
import com.example.accountbook.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // 가계부 메인 페이지 (목록 조회) - form 객체 바인딩 transaction 를 전달
    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String category,
            Model model, Transaction transaction, @AuthenticationPrincipal UserDetails userDetails) {
        int pageSize = 10; //한페이지에 10개 표시
        //검색조건이 포함된 목록과 전체 개수 조회
        model.addAttribute("transactions", transactionService.getTransactions(page, pageSize, startDate, endDate, category, userDetails.getUsername()));
        int totalPages = transactionService.getTotalPages(pageSize, startDate, endDate, category, userDetails.getUsername());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("category", category);
        // 합계 로직
        model.addAttribute("totalIncome", transactionService.getTotalIncome(userDetails.getUsername()));
        model.addAttribute("totalExpense", transactionService.getTotalExpense(userDetails.getUsername()));
        return "index";
    }
    // id로 해당 트랜젝션을 삭제한다.
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        transactionService.removeTransaction(id, userDetails.getUsername());
        return "redirect:/";
    }
    // 새 트랜젝션 저장 처리
    @PostMapping("/add")
    public String addTransaction(@Valid Transaction transaction,
                                 BindingResult bindingResult, Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentPage", 1);
            model.addAttribute("transactions", transactionService.getAllTransactions(userDetails.getUsername()));
            model.addAttribute("totalIncome", transactionService.getTotalIncome(userDetails.getUsername()));
            model.addAttribute("totalExpense", transactionService.getTotalExpense(userDetails.getUsername()));
            return "index"; //되돌아 가기
        }
        transaction.setUsername(userDetails.getUsername()); //유저네임을 추가한다.
        transactionService.addTransaction(transaction); //db에 저장됨
        return "redirect:/"; // 저장 후 메인 페이지로 리다이렉트
    }
}
