package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Member;
import com.example.demo.model.MemberVO;

public interface MemberService {

	public Member callNext();
	public String getMemberWaitTime(String deviceId);
	public List<MemberVO> getAllMemberWaitTime();
}
