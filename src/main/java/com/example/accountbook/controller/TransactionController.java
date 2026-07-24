package com.example.accountbook.controller;

import com.example.accountbook.model.Transaction;
import com.example.accountbook.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // 가계부 메인 페이지 (목록 조회) - form 객체 바인딩 transaction 를 전달
    @GetMapping("/")
    public String index(Model model, Transaction transaction) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        model.addAttribute("totalIncome", transactionService.getTotalIncome());
        model.addAttribute("totalExpense", transactionService.getTotalExpense());
        return "index";
    }
    // id로 해당 트랜젝션을 삭제한다.
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.removeTransaction(id);
        return "redirect:/";
    }
    // 새 트랜젝션 저장 처리
    @PostMapping("/add")
    public String addTransaction(@Valid Transaction transaction,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("transactions", transactionService.getAllTransactions());
            model.addAttribute("totalIncome", transactionService.getTotalIncome());
            model.addAttribute("totalExpense", transactionService.getTotalExpense());
            return "index"; //되돌아 가기
        }
        transactionService.addTransaction(transaction); //db에 저장됨
        return "redirect:/"; // 저장 후 메인 페이지로 리다이렉트
    }
}
