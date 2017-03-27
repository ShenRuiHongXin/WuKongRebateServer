package com.shenrui.wukong.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shenrui.wukong.dao.UserDao;
import com.shenrui.wukong.dao.UserSignInfoDao;
import com.shenrui.wukong.utils.Constant;
import com.shenrui.wukong.utils.Utils;
import com.shenrui.wukong.vo.UserBean;
import com.shenrui.wukong.vo.UserSignInfoBean;
import com.shenrui.wukong.votmp.ResultMessage;

@Transactional
@Service("userSignInfoService")
public class UserSignInfoService {
	@Autowired
	@Qualifier("userSignInfoDao")
	private UserSignInfoDao userSignInfoDao;
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	Map<String, Object> respData;
	Date date;
	SimpleDateFormat sdf;
	
	public Map<String, Object> add(UserBean userBean){
		respData = new HashMap<String, Object>();
		date = new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 判断是否第一次签到
		DetachedCriteria criteria = DetachedCriteria.forClass(UserSignInfoBean.class);
		criteria.add(Restrictions.eq("user_id",userBean.getId()));
		List<UserSignInfoBean> list = userSignInfoDao.findByCriteria(criteria);
		
		if(list.size() != 0){
			respData.putAll(update(list.get(0),userBean));
		}else{
			UserSignInfoBean uSignInfoBean = new UserSignInfoBean();
			uSignInfoBean.setUser_id(userBean.getId());
			uSignInfoBean.setLast_sign_time(sdf.format(date));
			uSignInfoBean.setContinuity_sign_days(1);
			
			userBean.setIntegral(userBean.getIntegral()+5);
			userDao.update(userBean);
			
			respData.put("userSignInfo", userSignInfoDao.save(uSignInfoBean));
		}
		
		respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS,"签到成功"));
		return respData;
	}
	
	public Map<String, Object> update(UserSignInfoBean userSignInfoBean,UserBean userBean){
		respData = new HashMap<String, Object>();
		date = new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String lastSignTime = userSignInfoBean.getLast_sign_time();
		System.out.println("lastSignTime: " + lastSignTime);
	
		try {
			System.out.println("相差天数： " + Utils.calculateDays(sdf.parse(lastSignTime),date));
			userSignInfoBean.setLast_sign_time(sdf.format(date));
			userSignInfoBean.setLast_sign_time(sdf.format(date));
			if(Utils.calculateDays(sdf.parse(lastSignTime),date) <= 1){
				userSignInfoBean.setContinuity_sign_days(userSignInfoBean.getContinuity_sign_days()+1);
			}else{
				userSignInfoBean.setContinuity_sign_days(1);
			}
			
			userBean.setIntegral(userBean.getIntegral()+userSignInfoBean.getContinuity_sign_days()*5);
			userDao.update(userBean);
			
			respData.put("userSignInfo", userSignInfoDao.update(userSignInfoBean));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		return respData;
	}
}
