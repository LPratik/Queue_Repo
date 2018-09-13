package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.model.MemberVO;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.services.MemberService;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins="http://localhost:4200/",allowedHeaders="*")
public class MemberController {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/members")
	public ResponseEntity<List<Member>> getAllMembers(){
		//return memberRepository.findAll();
		return ResponseEntity.ok().body(memberRepository.findAll());
	}
	
	@GetMapping("/member/{id}")
	public ResponseEntity<Member> getMember(@PathVariable Integer id){
		return ResponseEntity.ok().body(memberRepository.getOne(id));
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> addMember(@RequestBody Member member){
		return ResponseEntity.ok().body(memberRepository.save(member));
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.PUT,
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> editMember(@RequestBody Member member){
		return ResponseEntity.ok().body(memberRepository.save(member));
	}
	
	@DeleteMapping("/member/{id}")
	public ResponseEntity<Boolean> deleteMember(@PathVariable Integer id){
		memberRepository.deleteById(id);
		return ResponseEntity.ok().body(true);
	}
	
	@RequestMapping(value = "/device/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMemberDevice(@PathVariable String id){
		return ResponseEntity.ok().body(memberService.getMemberWaitTime(id));
	}
	
	@RequestMapping(value = "/next", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> callNextMember(){
		return ResponseEntity.ok().body(memberService.callNext());
	}
	
	@GetMapping("/members/waiting")
	public ResponseEntity<List<MemberVO>> getAllWaitingMembers(){
		//return memberRepository.findAll();
		return ResponseEntity.ok().body(memberService.getAllMemberWaitTime());
	}
}
