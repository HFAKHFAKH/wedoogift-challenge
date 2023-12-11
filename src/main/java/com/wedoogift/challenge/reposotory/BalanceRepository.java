package com.wedoogift.challenge.reposotory;

import com.wedoogift.challenge.entity.Balance;
import com.wedoogift.challenge.entity.BalanceType;
import com.wedoogift.challenge.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {

    @Query("select coalesce(sum(b.accountBalance), 0) " +
            "from com.wedoogift.challenge.entity.Balance b " +
            "where b.user = :user " +
            "and b.balanceType = :type " +
            "and b.endDate > now()")
    Double calculateBalanceByType(User user, BalanceType type);
}
