package com.monster.sr.mapper;

import com.monster.sr.entity.OrderPO;
import org.springframework.stereotype.Repository;

/**
 * .
 */
@Repository
public interface OrderMapper {

    void insert(OrderPO order);

    OrderPO getById(Long id);

    int update(OrderPO order);

    void delete(Long id);

}
