package com.example.accountbook.service;

import com.example.accountbook.mapper.TransactionMapper;
import com.example.accountbook.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionMapper transactionMapper;

    // 모든 트랜젝션 가져오기
    public List<Transaction> getAllTransactions() {
        return transactionMapper.findAll();
    }
    // 새로운 트랜젝션 입력
    public void addTransaction(Transaction transaction) {
        transactionMapper.save(transaction);
    }
    // id로 삭제하기
    public void removeTransaction(Long id) {
        transactionMapper.deleteById(id);
    }
    // 총 수익
    public Long getTotalIncome() {
        return transactionMapper.getTotalAmountByType("INCOME");
    }
    // 총 지출
    public Long getTotalExpense() {
        return transactionMapper.getTotalAmountByType("EXPENSE");
    }
}
