package com.monster.sr.mapper;

import com.monster.sr.entity.TransactionLogPO;
import org.springframework.stereotype.Repository;

/**
 * .
 */
@Repository
public interface TransactionLogMapper {

    void insert(TransactionLogPO transactionLog);

    TransactionLogPO getById(String id);

    void update(TransactionLogPO transactionLog);

    void delete(String id);

}
