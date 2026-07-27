package com.example.accountbook.service;

import com.example.accountbook.mapper.TransactionMapper;
import com.example.accountbook.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionMapper transactionMapper;

    // 모든 트랜젝션 가져오기
    public List<Transaction> getAllTransactions(String username) {
        return transactionMapper.findAll(username);
    }
    // 새로운 트랜젝션 입력
    public void addTransaction(Transaction transaction) {
        transactionMapper.save(transaction);
    }
    // id로 삭제하기
    public void removeTransaction(Long id, String username) {
        transactionMapper.deleteById(id, username);
    }
    // 총 수익
    public Long getTotalIncome(String username) {
        return transactionMapper.getTotalAmountByType("INCOME", username);
    }
    // 총 지출
    public Long getTotalExpense(String username) {
        return transactionMapper.getTotalAmountByType("EXPENSE", username);
    }
    // 페이지번호, 10을 입력시 해당페이지의 10개 트랜젝션 가져옴
    public List<Transaction> getTransactions(int page, int size,
                                             String startDate, String endDate, String category, String username) {
        int offset = (page - 1) * size;
        return transactionMapper.findPaged(offset, size, startDate, endDate, category, username);
    }
    // 한페이지 size 입력서 전체 페이지번호 리턴
    public int getTotalPages(int size, String startDate, String endDate, String category, String username) {
        int totalCount = transactionMapper.countAll( startDate, endDate, category, username);
        return (int) Math.ceil((double) totalCount / size);
    }
    // 유저의 지출내역을 카테고리 별로 합계를 리스트로 가져옴
    public List<Map<String, Object>> getCategoryStats(String username) {
        return transactionMapper.getCategoryStats(username);
    }
}
