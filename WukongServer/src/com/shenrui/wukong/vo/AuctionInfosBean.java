package com.shenrui.wukong.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auction_infos")
public class AuctionInfosBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String detail_order_id;
	private String auction_id;
	private double real_pay;
	private String auction_pict_url;
	private String auction_title;
	private int auction_amount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDetail_order_id() {
		return detail_order_id;
	}
	public void setDetail_order_id(String detail_order_id) {
		this.detail_order_id = detail_order_id;
	}
	public String getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(String auction_id) {
		this.auction_id = auction_id;
	}
	public double getReal_pay() {
		return real_pay;
	}
	public void setReal_pay(double real_pay) {
		this.real_pay = real_pay;
	}
	public String getAuction_pict_url() {
		return auction_pict_url;
	}
	public void setAuction_pict_url(String auction_pict_url) {
		this.auction_pict_url = auction_pict_url;
	}
	public String getAuction_title() {
		return auction_title;
	}
	public void setAuction_title(String auction_title) {
		this.auction_title = auction_title;
	}
	public int getAuction_amount() {
		return auction_amount;
	}
	public void setAuction_amount(int auction_amount) {
		this.auction_amount = auction_amount;
	}
	@Override
	public String toString() {
		return "AuctionInfosBean [id=" + id + ", detail_order_id="
				+ detail_order_id + ", auction_id=" + auction_id
				+ ", real_pay=" + real_pay + ", auction_pict_url="
				+ auction_pict_url + ", auction_title=" + auction_title
				+ ", auction_amount=" + auction_amount + "]";
	}
}
