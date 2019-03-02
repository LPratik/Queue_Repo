package com.example.demo.services.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.AverageTime;
import com.example.demo.entities.Member;
import com.example.demo.repositories.AvgServiceTimeRepository;
import com.example.demo.repositories.MemberRepository;

@Service
public class UtilServices {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AvgServiceTimeRepository avgTimeRepository;

	@Transactional
	void updateAvgServiceTime(List<Member> completeMembers){
		int avgTime = 0;
		int count = 0;
		for(Member member:completeMembers){
			avgTime = avgTime +member.getServiceTime();
			count++;
		}
		avgTime = avgTime/count;
		//avgTimeRepository.deleteAll();

		AverageTime avgTimeObj = avgTimeRepository.getAvgTimeById(1);
		if(avgTimeObj!=null) {
			avgTimeObj.setAverageTime(avgTime);
			avgTimeObj.setUpdated(new Date());
			avgTimeRepository.save(avgTimeObj);
		}else {
			avgTimeObj = new AverageTime();
		avgTimeObj.setAverageTime(avgTime);
		avgTimeRepository.save(avgTimeObj);
	}
	}

	int waitingTimeCalculator (int index, List<AverageTime> avgServiceTime, Member serviceMember){
		int avgTime = avgServiceTime.get(0).getAverageTime() * index;
		if(serviceMember!=null){
			avgTime = avgTime + (avgServiceTime.get(0).getAverageTime() - ((int) ((new Date().getTime() - serviceMember.getStartTime().getTime()) / 1000)));
		}else{
			avgTime = 0;
		}
		return avgTime;
	}

	String formatWaitingTime(int secs){
		int hour = 0,min = 0,sec=0;
		if(secs!=0){
			sec=secs/60;
			if(sec > 0){
				min = secs/60;
				sec = secs % 60;
				if(min>=60){
					hour = min/60;
					min = min % 60;
				}
			}else{
				sec = secs;
			}
		}

		DecimalFormat df2 = new DecimalFormat("00");

		return df2.format(hour)+":"+df2.format(min)+":"+df2.format(sec);
	}
	
}
