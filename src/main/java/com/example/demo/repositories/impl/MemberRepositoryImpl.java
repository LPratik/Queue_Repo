package com.example.demo.repositories.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Member;
import com.example.demo.repositories.MemberRepositoryCustom;
import com.example.demo.services.impl.UtilServices;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

	@PersistenceContext
    EntityManager entityManager;
	
	@Autowired
	UtilServices utilService;
	
	@Override
	public Member getMemberByDeviceID(String deviceId) {
		Member member = null;
		Query query = entityManager.createNativeQuery("SELECT qm.* FROM member as qm " +
                "WHERE qm.device_id = ?", Member.class);
        query.setParameter(1, deviceId);
        try{ 
			member = (Member) query.getSingleResult();
		}catch(NoResultException e){

		}finally{
			return member;
		}
	}

	@Override
	public Member getMemberInService() {
		Member member = null;
		Query query = entityManager.createNativeQuery("SELECT qm.* FROM member as qm " +
				"WHERE qm.state = ?", Member.class);
		query.setParameter(1, "SERVICE");
		try{ 
			member = (Member) query.getSingleResult();
		}catch(NoResultException e){

		}finally{
			return member;
		}
	}

	@Override
	@Transactional
	public int endService(Member member) {
		Date currentTime = new Date();
		Long diff = currentTime.getTime() - member.getStartTime().getTime();
		Integer servSeconds = (int) (diff / 1000);
		Query query = entityManager.createNativeQuery("UPDATE member as qm SET end_time =? , service_time=?, state = 'COMPLETE'" +
                "WHERE qm.id = ?", Member.class);
        query.setParameter(1, currentTime);
        query.setParameter(2,servSeconds);
        query.setParameter(3, member.getId());
        return query.executeUpdate();
	}

	@Override
	@Transactional
	public int startNextService(Member member) {
		Date currentTime = new Date();
		Query query = entityManager.createNativeQuery("UPDATE member as qm SET start_time =? , state = 'SERVICE'" +
                "WHERE qm.id = ?", Member.class);
        query.setParameter(1, currentTime);
        query.setParameter(2, member.getId());
        return query.executeUpdate();
	}

	@Override
	public Member getMemberInWaitState() {
		Query query = entityManager.createNativeQuery("SELECT qm.* FROM member as qm " +
                "WHERE qm.state = ? ORDER BY 1 ASC", Member.class);
        query.setParameter(1, "WAIT");
        return (Member) (!query.getResultList().isEmpty()?query.getResultList().get(0):null);
	}

	@Override
	public List<Member> getLastFiveCompMembers() {
		Query query = entityManager.createNativeQuery("SELECT qm.* FROM member as qm " +
                "WHERE qm.state = ? ORDER BY 1 DESC LIMIT 5", Member.class);
        query.setParameter(1, "COMPLETE");
        return (List<Member>) query.getResultList();
	}

	@Override
	public List<Member> getAllWaitingMembers() {
		Query query = entityManager.createNativeQuery("SELECT qm.* FROM member as qm " +
                "WHERE qm.state = ? ORDER BY 1 ASC", Member.class);
        query.setParameter(1, "WAIT");
        return (List<Member>) (!query.getResultList().isEmpty()?query.getResultList():null);
	}

}
