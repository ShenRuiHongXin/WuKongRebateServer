package com.shenrui.wukong.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paid_info")
public class PaidInfoBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private String tb_account_id;
	private String tb_trade_id;
	private double rebate_amount;
	private String paid_time;
	private String device_model;
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
	public String getTb_account_id() {
		return tb_account_id;
	}
	public void setTb_account_id(String tb_account_id) {
		this.tb_account_id = tb_account_id;
	}
	public String getTb_trade_id() {
		return tb_trade_id;
	}
	public void setTb_trade_id(String tb_trade_id) {
		this.tb_trade_id = tb_trade_id;
	}
	public double getRebate_amount() {
		return rebate_amount;
	}
	public void setRebate_amount(double rebate_amount) {
		this.rebate_amount = rebate_amount;
	}
	public String getPaid_time() {
		return paid_time;
	}
	public void setPaid_time(String paid_time) {
		this.paid_time = paid_time;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	@Override
	public String toString() {
		return "PaidInfoBean [id=" + id + ", user_id=" + user_id
				+ ", tb_account_id=" + tb_account_id + ", tb_trade_id="
				+ tb_trade_id + ", rebate_amount=" + rebate_amount
				+ ", paid_time=" + paid_time + ", device_model=" + device_model
				+ "]";
	}

}
