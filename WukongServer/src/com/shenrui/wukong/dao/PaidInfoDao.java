package com.shenrui.wukong.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shenrui.wukong.vo.PaidInfoBean;

@Repository("paidInfoDao")
public class PaidInfoDao {
	//装载hibernateTemplate
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 增
	 * @param paidInfoBean
	 */
	public void save(PaidInfoBean paidInfoBean){
		System.out.println("PaidInfoDao save(): " + paidInfoBean.toString());
		hibernateTemplate.save(paidInfoBean);
		System.out.println("自动生成的ID：" + paidInfoBean.getId());
	}
	
	/**
	 * 条件
	 * @param criteria
	 * @return
	 */
	public List<PaidInfoBean> findByCriteria(DetachedCriteria criteria){
		return hibernateTemplate.findByCriteria(criteria);
	}
}
