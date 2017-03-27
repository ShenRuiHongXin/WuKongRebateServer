package com.shenrui.wukong.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_online_info")
public class UserOnlineInfoBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private String last_login_time;//最近一次登录时间
	private String last_login_ip;//最近一次登录IP
	private String last_login_device_model;//最近一次登录设备型号
	private String last_login_device_id;//最近一次登录设备ID
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public String getLast_login_device_model() {
		return last_login_device_model;
	}
	public void setLast_login_device_model(String last_login_device_model) {
		this.last_login_device_model = last_login_device_model;
	}
	public String getLast_login_device_id() {
		return last_login_device_id;
	}
	public void setLast_login_device_id(String last_login_device_id) {
		this.last_login_device_id = last_login_device_id;
	}
	
	@Override
	public String toString() {
		return "UserOnlineStatus [id=" + id + ", user_id=" + user_id
				+ ", last_login_time=" + last_login_time + ", last_login_ip="
				+ last_login_ip + ", last_login_device_model="
				+ last_login_device_model + ", last_login_device_id="
				+ last_login_device_id + "]";
	}
}
