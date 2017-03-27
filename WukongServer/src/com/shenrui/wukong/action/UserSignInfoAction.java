package com.shenrui.wukong.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shenrui.wukong.service.UserSignInfoService;
import com.shenrui.wukong.utils.Utils;
import com.shenrui.wukong.vo.UserBean;
import com.shenrui.wukong.vo.UserSignInfoBean;

@Namespace("/")
@ParentPackage("json-default")
@Controller("userSignInfoAction")
@Scope("prototype")
@Results({ @Result(name = ActionSupport.SUCCESS, type = "json"),
		@Result(name = ActionSupport.ERROR, type = "json") })
public class UserSignInfoAction extends ActionSupport implements ModelDriven<UserSignInfoBean>{
	private UserSignInfoBean userSignInfoBean;
	@Override
	public UserSignInfoBean getModel() {
		return userSignInfoBean;
	}

	@Autowired
	@Qualifier("userSignInfoService")
	private UserSignInfoService userSignInfoService;
	
	// 响应数据
	private Map<String, Object> respData;

	public Map<String, Object> getRespData() {
		return respData;
	}

	public void setRespData(Map<String, Object> respData) {
		this.respData = respData;
	}
	
	/**
	 * 用户签到
	 * @return
	 */
	@Action(value = "user_sign", results = { @Result(type = "json", params = {"root", "respData" }) })
	public String add(){
		// 1.获取ActionContext
		ActionContext actionContext = ActionContext.getContext();

		// 2获取请求参数
		Map<String, Object> paramMap = actionContext.getParameters();

		String userInfoJson = ((String[]) paramMap.get("userInfo"))[0];
		System.out.println("userInfo: " + userInfoJson);
		UserBean userBean = new Gson().fromJson(userInfoJson, UserBean.class);
		
		respData = userSignInfoService.add(userBean);
		
		return SUCCESS;
	}
}
