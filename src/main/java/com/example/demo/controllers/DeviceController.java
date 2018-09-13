package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.repositories.MemberRepository;

@RestController()
public class DeviceController {

	@Autowired
	private MemberRepository memberRepository;
	
	@RequestMapping(value = "/device/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> getMemberDevice(@PathVariable String deviceId){
		return ResponseEntity.ok().body(memberRepository.getMemberByDeviceID(deviceId));
	}
	
}
