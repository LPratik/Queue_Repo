package com.example.demo.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.demo.entities.AverageTime;
import com.example.demo.repositories.AvgServiceTimeRepositoryCustom;

public class AvgServiceTimeRepositoryImpl implements AvgServiceTimeRepositoryCustom{
	
	@PersistenceContext
    EntityManager entityManager;

	@Override
	public AverageTime getAvgTimeById(int id) {
		AverageTime avg = null;
		Query query = entityManager.createNativeQuery("SELECT qm.* FROM average_time as qm " +
                "WHERE qm.id = ?", AverageTime.class);
        query.setParameter(1, id);
        try{ 
        	avg = (AverageTime) query.getSingleResult();
		}catch(NoResultException e){

		}finally{
			return avg;
		}
	}

}
