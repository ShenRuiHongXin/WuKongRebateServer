package com.shenrui.wukong.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shenrui.wukong.vo.UserAuthsBean;

@Repository("userAuthsDao")
public class UserAuthsDao {
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 增
	 * @param userAuthsBean
	 * @return
	 */
	public UserAuthsBean save(UserAuthsBean userAuthsBean){
		hibernateTemplate.save(userAuthsBean);
		return userAuthsBean;
	}
	
	/**
	 * 条件查询
	 * @param criteria
	 * @return
	 */
	public List<UserAuthsBean> findByCriteria(DetachedCriteria criteria){
		return hibernateTemplate.findByCriteria(criteria);
	}
	
	/**
	 * 修改用户认证信息
	 * @param userAuthsBean
	 * @return
	 */
	public UserAuthsBean update(UserAuthsBean userAuthsBean){
		hibernateTemplate.update(userAuthsBean);
		return userAuthsBean;
	}
}
