package com.example.demo.model;

public class MemberVO {

	private String deviceId;
	private String name;
	private String waitingTime;
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
	public String getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(String waitingTime) {
		this.waitingTime = waitingTime;
	}
	@Override
	public String toString() {
		return "MemberVO [deviceId=" + deviceId + ", name=" + name + ", waitingTime=" + waitingTime + "]";
	}
	
}
