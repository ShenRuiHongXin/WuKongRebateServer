package com.shenrui.wukong.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_info")
public class UserBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nick_name;
	private int sex;
	private String avatar;
	private double balance;
	private double tmp_balance;
	private int integral;
	private String invite_code;
	private String regist_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getTmp_balance() {
		return tmp_balance;
	}
	public void setTmp_balance(double tmp_balance) {
		this.tmp_balance = tmp_balance;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public String getInvite_code() {
		return invite_code;
	}
	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}
	public String getRegist_time() {
		return regist_time;
	}
	public void setRegist_time(String regist_time) {
		this.regist_time = regist_time;
	}
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", nick_name=" + nick_name + ", sex="
				+ sex + ", avatar=" + avatar + ", balance=" + balance
				+ ", tmp_balance=" + tmp_balance + ", integral=" + integral
				+ ", invite_code=" + invite_code + ", regist_time="
				+ regist_time + "]";
	}
}
