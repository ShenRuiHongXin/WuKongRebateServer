package com.shenrui.wukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shenrui.wukong.dao.PaidInfoDao;
import com.shenrui.wukong.vo.PaidInfoBean;

@Transactional
@Service("paidInfoService")
public class PaidInfoService {
	//Service中注入Dao
	@Autowired
	@Qualifier("paidInfoDao")
	private PaidInfoDao paidInfoDao;
	
	/**
	 * 增
	 * @param paidInfoBean
	 */
	public void add(PaidInfoBean paidInfoBean){
		paidInfoDao.save(paidInfoBean);
	}
}
