package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.AverageTime;
import com.example.demo.entities.Member;
import com.example.demo.model.DeviceResponseDTO;
import com.example.demo.model.MemberVO;
import com.example.demo.repositories.AvgServiceTimeRepository;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.services.MemberService;
import com.example.demo.util.TimeExtendThread;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private UtilServices utilService;
	
	@Autowired
	private AvgServiceTimeRepository avgServiceTimeRepo;
	
	@Override
	public Member callNext() {
		Member nextMember = null;
		Member serviceMember = memberRepository.getMemberInService();
		if(serviceMember!=null){
			//int updateServMember = memberRepository.endService(serviceMember);
			Date currentTime = new Date();
			Long diff = currentTime.getTime() - serviceMember.getStartTime().getTime();
			Integer servSeconds = (int) (diff / 1000);
			serviceMember.setDeleted(currentTime);
			serviceMember.setState("COMPLETE");
			serviceMember.setEndTime(currentTime);
			serviceMember.setServiceTime(servSeconds);
			memberRepository.save(serviceMember);
		}
		nextMember = memberRepository.getMemberInWaitState();
		if(nextMember!=null){
			Date currentTime = new Date();
			nextMember.setStartTime(currentTime);
			nextMember.setState("SERVICE");
			memberRepository.save(nextMember);
			//memberRepository.startNextService(nextMember);
		}
		List<Member> completeMembers = memberRepository.getLastFiveCompMembers();
		if(completeMembers!=null && !completeMembers.isEmpty()){
			utilService.updateAvgServiceTime(completeMembers);
		}
		
		if(nextMember!=null){
			TimeExtendThread tar = TimeExtendThread.getTarget(avgServiceTimeRepo,nextMember.getStartTime());
		}
		return nextMember!=null?nextMember:serviceMember;
	}
	
	public DeviceResponseDTO getMemberWaitTime(String deviceId){
		String waitingTime = "00:00:00";
		Member member = memberRepository.getMemberByDeviceID(deviceId);
		if(member!=null && member.getState().equals("WAIT")){
			List<Member> waitingMembers = memberRepository.getAllWaitingMembers();
			if(waitingMembers!=null && !waitingMembers.isEmpty()){
				int index = waitingMembers.indexOf(member);
				Member serviceMember = memberRepository.getMemberInService();
				List<AverageTime> avgServiceTime = avgServiceTimeRepo.findAll();
				waitingTime = calculateMemberWaitTime(index, avgServiceTime, serviceMember);
			}
		}
		
		DeviceResponseDTO response = new DeviceResponseDTO();
		response.setDeviceId(deviceId);
		response.setWaitTime(waitingTime);
		return response;
	}
	
	public String calculateMemberWaitTime(int index, List<AverageTime> avgServiceTime, Member serviceMember ){
		String waitingTime = "00:00:00";
		int waitingSecs = utilService.waitingTimeCalculator(index, avgServiceTime, serviceMember);
		waitingTime = utilService.formatWaitingTime(waitingSecs);
		return waitingTime;
	}
	
	public int getMemberWaitSecs(int index, List<AverageTime> avgServiceTime, Member serviceMember ){
		int waitingSecs = 0;
		waitingSecs = utilService.waitingTimeCalculator(index, avgServiceTime, serviceMember);
		return waitingSecs;
	}
	
	public List<MemberVO> getAllMemberWaitTime(){
		List<MemberVO> membersList = new ArrayList<MemberVO>();
		int waitingTime = 0;
		int index=0;
		Member serviceMember = memberRepository.getMemberInService();
		if(serviceMember!=null){
			MemberVO memberVo = new MemberVO();
			memberVo.setId(serviceMember.getId());
			memberVo.setDeviceId(serviceMember.getDeviceId());
			memberVo.setName(serviceMember.getName());
			memberVo.setState(serviceMember.getState());
			membersList.add(memberVo);
		}
		List<AverageTime> avgServiceTime = avgServiceTimeRepo.findAll();
		List<Member> waitingMembers = memberRepository.getAllWaitingMembers();
		if(waitingMembers!=null && !waitingMembers.isEmpty()){
			for(Member waitMamber : waitingMembers){
				waitingTime = getMemberWaitSecs(index, avgServiceTime, serviceMember);
				MemberVO memberVo = new MemberVO();
				memberVo.setId(waitMamber.getId());
				memberVo.setDeviceId(waitMamber.getDeviceId());
				memberVo.setName(waitMamber.getName());
				memberVo.setState(waitMamber.getState());
				memberVo.setWaitTime(waitingTime);
				membersList.add(memberVo);
				index++;
			}
		}
		return membersList;
	}
}
