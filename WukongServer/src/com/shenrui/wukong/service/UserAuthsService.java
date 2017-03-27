package com.shenrui.wukong.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shenrui.wukong.dao.UserAuthsDao;
import com.shenrui.wukong.utils.Constant;
import com.shenrui.wukong.vo.UserAuthsBean;
import com.shenrui.wukong.votmp.ResultMessage;

@Transactional
@Service("userAuthsService")
public class UserAuthsService {
	@Autowired
	@Qualifier("userAuthsDao")
	private UserAuthsDao userAuthsDao;
	
	Map<String, Object> respData;
	
	public Map<String, Object> update(UserAuthsBean userAuthsBean){
		respData = new HashMap<String, Object>();
		respData.put("result", new ResultMessage(Constant.ResultCode.SUCCESS, "修改成功"));
		respData.put("userAuths", userAuthsDao.update(userAuthsBean));
		return respData;
	}
	
	public List<UserAuthsBean> findByCriteria(DetachedCriteria criteria){
		return userAuthsDao.findByCriteria(criteria);
	}

}
