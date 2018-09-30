package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Member;

public interface MemberRepositoryCustom {

	Member getMemberByDeviceID(String deviceId);
	Member getMemberInWaitState();
	Member getMemberInService();
	int endService(Member member);
	int startNextService(Member member);
	List<Member> getLastFiveCompMembers();
	List<Member> getAllWaitingMembers();
}
