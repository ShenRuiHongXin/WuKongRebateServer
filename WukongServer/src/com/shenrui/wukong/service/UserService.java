package com.shenrui.wukong.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shenrui.wukong.dao.UserAuthsDao;
import com.shenrui.wukong.dao.UserDao;
import com.shenrui.wukong.dao.UserOnlineInfoDao;
import com.shenrui.wukong.utils.Constant;
import com.shenrui.wukong.utils.Utils;
import com.shenrui.wukong.vo.UserAuthsBean;
import com.shenrui.wukong.vo.UserBean;
import com.shenrui.wukong.vo.UserOnlineInfoBean;
import com.shenrui.wukong.votmp.ResultMessage;
import com.taobao.api.domain.Userinfos;

@Transactional
@Service("userService")
public class UserService {
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("userAuthsDao")
	private UserAuthsDao userAuthsDao;

	@Autowired
	@Qualifier("userOnlineInfoDao")
	private UserOnlineInfoDao userOnlineInfoDao;

	Map<String, Object> respData;

	/**
	 * 注册
	 * 
	 * @param userBean
	 * @param userAuthsBean
	 * @return
	 */
	public Map<String, Object> add(UserBean userBean,UserAuthsBean userAuthsBean) {
		respData = new HashMap<String, Object>();
		// 判断账号是否已注册
		DetachedCriteria criteria = DetachedCriteria.forClass(UserAuthsBean.class);
		criteria.add(Restrictions.eq("identity_type",userAuthsBean.getIdentity_type()));
		criteria.add(Restrictions.eq("identifier",userAuthsBean.getIdentifier()));
		List<UserAuthsBean> list = userAuthsDao.findByCriteria(criteria);

		System.out.println("list.size(): " + list.size());
		if (list.size() != 0) {
//		if(false){
				respData.put("result", new ResultMessage(Constant.ResultCode.HAS_REGISTED, "该手机号已被注册"));
		} else {
			if ("".equals(userAuthsBean.getCredential()) || userAuthsBean.getCredential() == null) {
				// 验证手机号
				respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS, "可以注册"));
			} else {
				// 手机号通过验证，注册
				userBean = userDao.save(userBean);
				userBean.setInvite_code(Utils.generateInviteCode(userBean.getId()));
				userBean = userDao.update(userBean);

				userAuthsBean.setUser_id(userBean.getId());
				userAuthsBean = userAuthsDao.save(userAuthsBean);

				respData.put("userInfo", userBean);
				respData.put("userAuths", userAuthsBean);
				respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS, "注册成功"));
			}
		}
		System.out.println("密码: " + userAuthsBean.getCredential());

		return respData;
	}

	/**
	 * 登陆
	 * @param userAuthsBean
	 * @param userOnlineInfoBean
	 * @param userBean
	 * @return
	 */
	public Map<String, Object> login(UserAuthsBean userAuthsBean,UserOnlineInfoBean userOnlineInfoBean,UserBean userBean) {
		respData = new HashMap<String, Object>();
		// 如果是手机号登录，判断手机账号是否注册过
		DetachedCriteria criteria = DetachedCriteria.forClass(UserAuthsBean.class);
		criteria.add(Restrictions.eq("identity_type",userAuthsBean.getIdentity_type()));
		criteria.add(Restrictions.eq("identifier",userAuthsBean.getIdentifier()));
		List<UserAuthsBean> list = userAuthsDao.findByCriteria(criteria);

		if (Constant.IdentityType.PHONE.equals(userAuthsBean.getIdentity_type())) {

			if (list.size() == 0) {
				respData.put("result", new ResultMessage(Constant.ResultCode.NOT_REGISTED, "该账号还未注册"));
			} else {
				// 判断密码是否正确
				if (!list.get(0).getCredential().equals(userAuthsBean.getCredential())) {
					respData.put("result", new ResultMessage(Constant.ResultCode.PWD_ERROR, "密码错误"));
				} else {
					userAuthsBean = list.get(0);
					userBean = userDao.findById(userAuthsBean.getUser_id());

					// 判断是否有用户登录信息
					DetachedCriteria criteria1 = DetachedCriteria.forClass(UserOnlineInfoBean.class);
					criteria1.add(Restrictions.eq("user_id",userAuthsBean.getUser_id()));
					List<UserOnlineInfoBean> list1 = userOnlineInfoDao.findByCriteria(criteria1);

					if (list1.size() != 0) {
						// 更新用户登录信息
						UserOnlineInfoBean unibTmp = list1.get(0);
						unibTmp.setLast_login_device_id(userOnlineInfoBean.getLast_login_device_id());
						unibTmp.setLast_login_device_model(userOnlineInfoBean.getLast_login_device_model());
						unibTmp.setLast_login_ip(userOnlineInfoBean.getLast_login_ip());
						unibTmp.setLast_login_time(userOnlineInfoBean.getLast_login_time());
						userOnlineInfoDao.update(unibTmp);
					} else {
						// 新增用户登录信息
						userOnlineInfoBean.setUser_id(userAuthsBean.getUser_id());
						userOnlineInfoDao.save(userOnlineInfoBean);
					}

					respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS, "登录成功"));
					respData.put("userInfo", userBean);
					respData.put("userAuths", userAuthsBean);

				}
			}
		} else {
			// 三方账号登录
			// 判断是否第一次登录
			if(list.size() == 0){
				userBean = userDao.save(userBean);
				
				userBean.setInvite_code(Utils.generateInviteCode(userBean.getId()));
				userBean.setRegist_time(userOnlineInfoBean.getLast_login_time());
				userBean = userDao.update(userBean);
				
				userAuthsBean.setUser_id(userBean.getId());
				userAuthsBean = userAuthsDao.save(userAuthsBean);
				
				// 新增用户登录信息
				userOnlineInfoBean.setUser_id(userAuthsBean.getUser_id());
				userOnlineInfoDao.save(userOnlineInfoBean);
			}else{
				userAuthsBean = list.get(0);
				userBean = userDao.findById(userAuthsBean.getUser_id());
				
				DetachedCriteria criteria1 = DetachedCriteria.forClass(UserOnlineInfoBean.class);
				criteria1.add(Restrictions.eq("user_id",userAuthsBean.getUser_id()));
				List<UserOnlineInfoBean> list1 = userOnlineInfoDao.findByCriteria(criteria1);

				// 更新用户登录信息
				UserOnlineInfoBean unibTmp = list1.get(0);
				unibTmp.setLast_login_device_id(userOnlineInfoBean.getLast_login_device_id());
				unibTmp.setLast_login_device_model(userOnlineInfoBean.getLast_login_device_model());
				unibTmp.setLast_login_ip(userOnlineInfoBean.getLast_login_ip());
				unibTmp.setLast_login_time(userOnlineInfoBean.getLast_login_time());
				userOnlineInfoDao.update(unibTmp);
			}
			respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS, "三方账号登录成功"));
			respData.put("userInfo", userBean);
			respData.put("userAuths", userAuthsBean);
		}
		return respData;
	}
	
	/**
	 * 修改用户基本信息
	 * @param userBean
	 * @return
	 */
	public Map<String, Object> update(UserBean userBean){
		respData = new HashMap<String, Object>();
		respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS, "修改成功"));
		respData.put("userInfo", userDao.update(userBean));
		return respData;
	}
}
