package com.example.vacation.repository;

import com.example.vacation.model.entity.LeaveEntity;
import com.example.vacation.model.service.LeaveManagerServiceModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class LeaveNativeSqlRepo {

    @PersistenceContext
    EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public List<LeaveEntity> getAllLeavesOnStatus(StringBuffer whereQuery) {

        Query query = entityManager.createNativeQuery("select * from leave_entity where " + whereQuery,
                LeaveEntity.class);

        return query.getResultList();
    }
}
