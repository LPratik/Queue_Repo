package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Member;
import com.example.demo.model.DeviceResponseDTO;
import com.example.demo.model.MemberVO;

public interface MemberService {

	public Member callNext();
	public DeviceResponseDTO getMemberWaitTime(String deviceId);
	public List<MemberVO> getAllMemberWaitTime();
}
