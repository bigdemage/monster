package com.monster.sr.mapper;

import com.monster.sr.entity.PointsPO;
import org.springframework.stereotype.Repository;

/**
 * .
 */
@Repository
public interface PointsMapper {

    void insert(PointsPO pointsPO);

    PointsPO getById(Long id);

    PointsPO getByOrderId(Long id);

    void update(PointsPO order);

    void delete(Long id);

}
