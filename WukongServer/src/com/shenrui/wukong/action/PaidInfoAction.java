package com.shenrui.wukong.action;

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
import com.shenrui.wukong.service.PaidInfoService;
import com.shenrui.wukong.vo.PaidInfoBean;

@Namespace("/")
@ParentPackage("json-default")
@Controller("paidInfoAction")
@Scope("prototype")
@Results({ @Result(name = ActionSupport.SUCCESS, type = "json"),
		@Result(name = ActionSupport.ERROR, type = "json") })
public class PaidInfoAction extends ActionSupport implements
		ModelDriven<PaidInfoBean> {
	private PaidInfoBean paidInfoBean = new PaidInfoBean();
	public PaidInfoBean getModel() {
		return paidInfoBean;
	}

	// Action中注入Service
	@Autowired
	@Qualifier("paidInfoService")
	private PaidInfoService paidInfoService;

	/**
	 * 保存支付成功的订单信息
	 * @return
	 */
	@Action(value = "paidInfo_add")
	public String add() {
		// 1.获取ActionContext
		ActionContext actionContext = ActionContext.getContext();

		// 2获取请求参数
		Map<String, Object> paramMap = actionContext.getParameters();
		
		String json = ((String[])paramMap.get("paidInfo"))[0];
		System.out.println(json);
		paidInfoBean = new Gson().fromJson(json, PaidInfoBean.class);

		paidInfoService.add(paidInfoBean);
		return NONE;
	}

}
