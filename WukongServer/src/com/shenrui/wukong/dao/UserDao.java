package com.shenrui.wukong.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shenrui.wukong.utils.Utils;
import com.shenrui.wukong.vo.UserBean;

@Repository("userDao")
public class UserDao {
	// 装载hibernateTemplate
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	
	/**
	 * 增
	 * @param userBean
	 * @return 
	 */
	public UserBean save(UserBean userBean){
		System.out.println(userBean.toString());
		hibernateTemplate.save(userBean);
		System.out.println("自动生成ID：" + userBean.getId());
		return userBean;
	}

//	/**
//	 * 增
//	 * @param userBean
//	 * @param userAuthsBean
//	 * @return
//	 */
//	public Map<String, Object> save(UserBean userBean, UserAuthsBean userAuthsBean) {
//		System.out.println(userBean.toString());
//		System.out.println(userAuthsBean.toString());
//		
//		hibernateTemplate.save(userBean);
//		System.out.println("自动生成ID：" + userBean.getId());
//		
//		userAuthsBean.setUser_id(userBean.getId());
//		userAuthsDao.save(userAuthsBean);
//		
//		respData.put("userInfo", userBean);
//		respData.put("userAuths", userAuthsBean);
//				
//		return respData;
//	}
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public UserBean findById(int id){
		return hibernateTemplate.get(UserBean.class, id);
	}
	/**
	 * 改
	 * @param userBean
	 * @return
	 */
	public UserBean update(UserBean userBean){
		hibernateTemplate.update(userBean);
		return userBean;
	}
}