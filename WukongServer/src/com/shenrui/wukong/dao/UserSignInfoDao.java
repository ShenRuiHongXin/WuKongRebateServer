package com.shenrui.wukong.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shenrui.wukong.vo.UserSignInfoBean;

@Repository("userSignInfoDao")
public class UserSignInfoDao {
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	public UserSignInfoBean save(UserSignInfoBean userSignInfoBean) {
		hibernateTemplate.save(userSignInfoBean);
		return userSignInfoBean;
	}
	
	public UserSignInfoBean update(UserSignInfoBean userSignInfoBean){
		hibernateTemplate.update(userSignInfoBean);
		return userSignInfoBean;
	}

	public List<UserSignInfoBean> findByCriteria(DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria);
	}
}
