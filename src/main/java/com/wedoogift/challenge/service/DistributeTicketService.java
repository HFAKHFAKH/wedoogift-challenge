package com.wedoogift.challenge.service;

import com.wedoogift.challenge.exception.ApplicationException;
import com.wedoogift.challenge.entity.Balance;
import com.wedoogift.challenge.entity.BalanceType;
import com.wedoogift.challenge.entity.Company;
import com.wedoogift.challenge.entity.User;
import com.wedoogift.challenge.reposotory.BalanceRepository;
import com.wedoogift.challenge.reposotory.CompanyRepository;
import com.wedoogift.challenge.reposotory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DistributeTicketService {

    private UserRepository userRepository;
    private BalanceRepository balanceRepository;
    private CompanyRepository companyRepository;


    public void distributeMeal(Long userId, Double amount) {
        distribute(userId, amount, BalanceType.MEAL);
    }

    public void distributeGift(Long userId, Double amount) {
        distribute(userId, amount, BalanceType.GIFT);
    }

    private void distribute(Long userId, Double amount, BalanceType balanceType) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApplicationException(404, "User not found");
        }
        User user = userOptional.get();
        balanceCompanyEnough(amount, balanceType, user.getCompany());
        Balance balance = Balance.builder()
                .user(user)
                .accountBalance(amount)
                .balanceType(balanceType)
                .createdDate(LocalDate.now())
                .endDate(balanceType.getEndDate())
                .build();
        balanceRepository.save(balance);
    }

    private void balanceCompanyEnough(Double amount, BalanceType balanceType, Company company) {
        if (balanceType.equals(BalanceType.MEAL)) {
            if (company.getBalanceMeal() < amount) {
                throw new ApplicationException(500, "Company balance meal not enough");
            } else {
                company.setBalanceMeal(company.getBalanceMeal() - amount);
            }
        }
        if (balanceType.equals(BalanceType.GIFT)) {
            if (company.getBalanceGift() < amount) {
                throw new ApplicationException(500, "Company balance gift not enough");
            } else {
                company.setBalanceGift(company.getBalanceGift() - amount);
            }
        }
        companyRepository.save(company);
    }
}
