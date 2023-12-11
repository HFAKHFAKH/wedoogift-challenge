package com.wedoogift.challenge.service;

import com.wedoogift.challenge.exception.ApplicationException;
import com.wedoogift.challenge.entity.Balance;
import com.wedoogift.challenge.entity.BalanceType;
import com.wedoogift.challenge.entity.Company;
import com.wedoogift.challenge.entity.User;
import com.wedoogift.challenge.reposotory.BalanceRepository;
import com.wedoogift.challenge.reposotory.CompanyRepository;
import com.wedoogift.challenge.reposotory.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;


@SpringBootTest
class DistributeTicketServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private CompanyRepository companyRepository;


    @Captor
    private ArgumentCaptor<Balance> balanceArgumentCaptor;

    @InjectMocks
    private DistributeTicketService distributeTicketService = new DistributeTicketService(userRepository, balanceRepository, companyRepository);

    @Test
    @DisplayName("Test Mock DistributeTicketService - distributeMeal - user not found")
    void testCalculateMealUserNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> distributeTicketService.distributeMeal(1L, 100D), "Expected distributeMeal() to throw, but it didn't");
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketService - distributeGift - user not found")
    void testCalculateGiftUserNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> distributeTicketService.distributeGift(1L, 100D), "Expected distributeGift() to throw, but it didn't");
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketService - distributeMeal - Company balance meal not enough")
    void testCalculateMealCompanyBalanceEnough() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(getUser()));
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () ->
                        distributeTicketService.distributeMeal(1L, 30D),
                "Expected distributeMeal() to throw, but it didn't");
        Assertions.assertEquals("Company balance meal not enough", exception.getMessage());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketService - distributeGift - Company balance gift not enough")
    void testCalculateGiftCompanyBalanceEnough() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(getUser()));
        ApplicationException exception = Assertions.assertThrows(
                ApplicationException.class, () ->
                        distributeTicketService.distributeGift(1L, 200D),
                "Expected distributeGift() to throw, but it didn't");
        Assertions.assertEquals("Company balance gift not enough", exception.getMessage());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketService - distributeMeal")
    void testCalculateMeal() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(getUser()));
        distributeTicketService.distributeMeal(1L, 10D);
        ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        Mockito.verify(companyRepository, Mockito.times(1)).save(companyArgumentCaptor.capture());
        Company company = companyArgumentCaptor.getValue();
        Assertions.assertEquals(10L, company.getBalanceMeal());

        Mockito.verify(balanceRepository, Mockito.times(1)).save(balanceArgumentCaptor.capture());
        Balance balance = balanceArgumentCaptor.getValue();
        Assertions.assertEquals(10L, balance.getAccountBalance());
        Assertions.assertEquals(BalanceType.MEAL, balance.getBalanceType());
        Assertions.assertEquals(LocalDate.now(), balance.getCreatedDate());
        Assertions.assertEquals(LocalDate.now().minusDays(1).plusYears(1), balance.getEndDate());

    }

    @Test
    @DisplayName("Test Mock DistributeTicketService - distributeGift")
    void testCalculateGift() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(getUser()));
        distributeTicketService.distributeGift(1L, 90D);
        ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        Mockito.verify(companyRepository, Mockito.times(1)).save(companyArgumentCaptor.capture());
        Company capturedArgument = companyArgumentCaptor.getValue();
        Assertions.assertEquals(10L, capturedArgument.getBalanceGift());

        Mockito.verify(balanceRepository, Mockito.times(1)).save(balanceArgumentCaptor.capture());
        Balance balance = balanceArgumentCaptor.getValue();
        Assertions.assertEquals(90D, balance.getAccountBalance());
        Assertions.assertEquals(BalanceType.GIFT, balance.getBalanceType());
        Assertions.assertEquals(LocalDate.now(), balance.getCreatedDate());
        Assertions.assertEquals(LocalDate.now().withMonth(2).plusYears(1).
                with(TemporalAdjusters.lastDayOfMonth()), balance.getEndDate());
    }

    private User getUser() {
        Company company = new Company();
        company.setId(1L);
        company.setName("test_Company");
        company.setBalanceMeal(20D);
        company.setBalanceGift(100D);
        User user = new User();
        user.setId(1L);
        user.setCompany(company);
        user.setName("test_User");
        return user;
    }
}
