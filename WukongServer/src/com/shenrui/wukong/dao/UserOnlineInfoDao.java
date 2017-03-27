package com.shenrui.wukong.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shenrui.wukong.vo.UserAuthsBean;
import com.shenrui.wukong.vo.UserOnlineInfoBean;

@Repository("userOnlineInfoDao")
public class UserOnlineInfoDao {
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	/**
	 * 增
	 * @param userOnlineInfoBean
	 */
	public void save(UserOnlineInfoBean userOnlineInfoBean){
		System.out.println("保存用户登录信息");
		hibernateTemplate.save(userOnlineInfoBean);
	}
	
	/**
	 * 改
	 * @param userOnlineInfoBean
	 */
	public void update(UserOnlineInfoBean userOnlineInfoBean){
		hibernateTemplate.update(userOnlineInfoBean);
	}
	
	public UserOnlineInfoBean findById(int id){
		return hibernateTemplate.get(UserOnlineInfoBean.class, id);
	}
	
	public List<UserOnlineInfoBean> findByCriteria(DetachedCriteria criteria){
		return hibernateTemplate.findByCriteria(criteria);
	}
}
