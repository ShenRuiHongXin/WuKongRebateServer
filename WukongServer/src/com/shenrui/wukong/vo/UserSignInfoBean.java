package com.shenrui.wukong.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_sign_info")
public class UserSignInfoBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private String last_sign_time;
	private int continuity_sign_days;
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
	public String getLast_sign_time() {
		return last_sign_time;
	}
	public void setLast_sign_time(String last_sign_time) {
		this.last_sign_time = last_sign_time;
	}
	public int getContinuity_sign_days() {
		return continuity_sign_days;
	}
	public void setContinuity_sign_days(int continuity_sign_days) {
		this.continuity_sign_days = continuity_sign_days;
	}
	@Override
	public String toString() {
		return "UserSignInfoBean [id=" + id + ", user_id=" + user_id
				+ ", last_sign_time=" + last_sign_time
				+ ", continuity_sign_days=" + continuity_sign_days + "]";
	}

}
