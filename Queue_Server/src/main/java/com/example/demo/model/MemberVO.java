package com.example.demo.model;

public class MemberVO {
    private int id;
	private String deviceId;
	private String name;
	private String state;
	private int waitTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", deviceId=" + deviceId + ", name=" + name + ", state=" + state
				+ ", waitTime=" + waitTime + "]";
	}
	
}
