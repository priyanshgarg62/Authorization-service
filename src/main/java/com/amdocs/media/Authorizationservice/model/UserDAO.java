package com.amdocs.media.Authorizationservice.model;

public class UserDAO {
	
	private String userName;
	private String password;
	private String address;
	private long phonenumber;
	private String topic;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return "UserDAO [userName=" + userName + ", password=" + password + ", address=" + address + ", phonenumber="
				+ phonenumber + ", topic=" + topic + "]";
	}

}

