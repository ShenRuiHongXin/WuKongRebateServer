package com.shenrui.wukong.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.shenrui.wukong.vo.AuctionInfosBean;
import com.shenrui.wukong.vo.TaobaoTradeBean;

public class TaobaoTradeDao extends HibernateDaoSupport{
	/**
	 * 增
	 * 
	 * @param book
	 */
	public void saveTaobaoTrade(TaobaoTradeBean taobaoTradeBean){
		System.out.println("保存淘宝订单信息");
		this.getHibernateTemplate().save(taobaoTradeBean);
	}

}
