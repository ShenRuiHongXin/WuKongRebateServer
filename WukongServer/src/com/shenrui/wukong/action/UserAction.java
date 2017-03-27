package com.shenrui.wukong.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shenrui.wukong.service.UserAuthsService;
import com.shenrui.wukong.service.UserService;
import com.shenrui.wukong.utils.Constant;
import com.shenrui.wukong.vo.UserAuthsBean;
import com.shenrui.wukong.vo.UserBean;
import com.shenrui.wukong.vo.UserOnlineInfoBean;
import com.shenrui.wukong.votmp.ResultMessage;
import com.taobao.api.domain.DetailPriceInfo;

@Namespace("/")
@ParentPackage("json-default")
@Controller("userAction")
@Scope("prototype")
@Results({ @Result(name = ActionSupport.SUCCESS, type = "json"),
		@Result(name = ActionSupport.ERROR, type = "json") })
public class UserAction extends ActionSupport implements ModelDriven<UserBean> {
	private UserBean userBean;

	public UserBean getModel() {
		return userBean;
	}

	// Action中注入Service
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("userAuthsService")
	private UserAuthsService userAuthsService;

	// 响应数据
	private Map<String, Object> respData;

	public Map<String, Object> getRespData() {
		return respData;
	}

	public void setRespData(Map<String, Object> respData) {
		this.respData = respData;
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@Action(value = "user_register", results = { @Result(type = "json", params = {"root", "respData" }) })
	public String add() {
		// 1.获取ActionContext
		ActionContext actionContext = ActionContext.getContext();

		// 2获取请求参数
		Map<String, Object> paramMap = actionContext.getParameters();

		String userInfoJson = ((String[]) paramMap.get("userInfo"))[0];
		System.out.println("userInfo: " + userInfoJson);
		userBean = new Gson().fromJson(userInfoJson, UserBean.class);

		String userAuthsJson = ((String[]) paramMap.get("userAuths"))[0];
		System.out.println("userAuths: " + userAuthsJson);
		UserAuthsBean userAuthsBean = new Gson().fromJson(userAuthsJson,UserAuthsBean.class);

		respData = userService.add(userBean, userAuthsBean);
		// System.out.println(respData.isEmpty());

		// for (Map.Entry<String, Object> entry : respData.entrySet()) {
		// System.out.println("key: "+entry.getKey()+" value: " +
		// entry.getValue());
		// }

		return SUCCESS;
	}

	
	/**
	 * 用户登录
	 * @return
	 */
	@Action(value = "user_login", results = { @Result(type = "json", params = {"root", "respData" }) })
	public String login() {
		// 1.获取ActionContext
		ActionContext actionContext = ActionContext.getContext();

		// 2获取请求参数
		Map<String, Object> paramMap = actionContext.getParameters();
		
		
		String userAuthsJson = ((String[]) paramMap.get("userAuths"))[0];
		System.out.println(userAuthsJson);
		UserAuthsBean userAuthsBean = new Gson().fromJson(userAuthsJson,UserAuthsBean.class);
		System.out.println(userAuthsBean.toString());
		
		String loginInfoJson = ((String[]) paramMap.get("loginInfo"))[0];
		System.out.println(loginInfoJson);
		UserOnlineInfoBean userOnlineInfoBean = new Gson().fromJson(loginInfoJson,UserOnlineInfoBean.class);
		System.out.println(userOnlineInfoBean.toString());
		
		if(!Constant.IdentityType.PHONE.equals(userAuthsBean.getIdentity_type())){
			String userInfoJson = ((String[]) paramMap.get("userInfo"))[0];
			System.out.println(userInfoJson);
			userBean = new Gson().fromJson(userInfoJson, UserBean.class);
		}
		
		respData = userService.login(userAuthsBean,userOnlineInfoBean,userBean);
		
		return SUCCESS;
	}
	
/*---------------------------------修改用户信息start----------------------------------*/
	private File avatar;
	private String avatarContentType;
	private String avatarFileName;

	public File getAvatar() {
		return avatar;
	}

	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}

	public String getAvatarContentType() {
		return avatarContentType;
	}

	public void setAvatarContentType(String avatarContentType) {
		this.avatarContentType = avatarContentType;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}

	/**
	 * 修改用户信息，修改用户登录密码
	 * @return
	 */
	@Action(value = "user_update", results = { @Result(type = "json", params = {"root", "respData" }) })
	public String update(){
		// 1.获取ActionContext
		ActionContext actionContext = ActionContext.getContext();

		// 2获取请求参数
		Map<String, Object> paramMap = actionContext.getParameters();
		
		for(Map.Entry<String, Object> entry : paramMap.entrySet()){
			System.out.println("key: "+entry.getKey()+" value: "+entry.getValue());
		}
		
		UserAuthsBean userAuthsBean = null;
		respData = new HashMap<String, Object>();
		
		//修改用户基本信息
		if(paramMap.containsKey("userInfo")){
			String userInfoJson = ((String[]) paramMap.get("userInfo"))[0];
			System.out.println("userInfo: " + userInfoJson);
			userBean = new Gson().fromJson(userInfoJson, UserBean.class);
			
			//如果用户修改了头像
			if(avatarContentType != null && avatarFileName != null){
				System.out.println("上传文件的类型："+avatarContentType);
				System.out.println("上传文件的名称："+avatarFileName);
				
				ServletContext sc = (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
				
				String savePath = sc.getRealPath("/Resources/UserAvatar");
				System.out.println("path: " + savePath);
				File saveFileDir = new File(savePath);
				if (!saveFileDir.exists()) {
					// 创建临时目录
					saveFileDir.mkdirs();
				}
				
				String tmpFileName = ""+userBean.getId()+System.currentTimeMillis()+avatarFileName.substring(avatarFileName.lastIndexOf(".")-1);
				
				File avatarFile = new File(saveFileDir,tmpFileName);
				
				//创建映射目录
				String mappingPath = sc.getRealPath("/");
				System.out.println("mappingPath: " + mappingPath);
				String str = mappingPath.substring(0,mappingPath.indexOf(":")+2);
				System.out.println("盘符：" + str);
				String projectDir = sc.getContextPath();
				System.out.println("工程目录：" + projectDir);
				String mappingDir = str + projectDir.substring(1);
				System.out.println("映射目录: " + mappingDir);
				File mappingFileDir = new File(mappingDir + "\\Resources\\UserAvatar");
				
				if(!mappingFileDir.exists()){
					//创建保存目录
					mappingFileDir.mkdirs();
				}
				
				
				
				File mappingFile = new File(mappingFileDir,tmpFileName);
				
				try {
					FileUtils.copyFile(avatar, avatarFile);
					FileUtils.copyFile(avatar, mappingFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String filePath = avatarFile.getAbsolutePath(); 
				System.out.println("file path: "+ filePath);
				String fileNameTmp = filePath.substring(filePath.lastIndexOf("WukongServer"));
				System.out.println(fileNameTmp);
				String fileName = fileNameTmp.replace("\\", "/");
				System.out.println(fileName);
				
				userBean.setAvatar(fileName);
			}
			
			respData.putAll(userService.update(userBean));
		}
		
		//修改用户认证信息
		if(paramMap.containsKey("userAuths")){
			String userAuthsJson = ((String[]) paramMap.get("userAuths"))[0];
			System.out.println("userAuths: " + userAuthsJson);
			userAuthsBean = new Gson().fromJson(userAuthsJson,UserAuthsBean.class);
			if(userAuthsBean.getUser_id() != 0 ){
				respData.putAll(userAuthsService.update(userAuthsBean));
			}else{
				DetachedCriteria criteria = DetachedCriteria.forClass(UserAuthsBean.class);
				criteria.add(Restrictions.eq("identity_type", userAuthsBean.getIdentity_type()));
				criteria.add(Restrictions.eq("identifier", userAuthsBean.getIdentifier()));
				List<UserAuthsBean> listAuthsBeans = userAuthsService.findByCriteria(criteria);
				
//				System.out.println("listAuthsBeans: " + listAuthsBeans.size());
				
				if(listAuthsBeans.size() == 0){
					respData.put("result", new ResultMessage(Constant.ResultCode.FAILED,"该账号未注册"));
				}else{
					userAuthsBean.setUser_id(listAuthsBeans.get(0).getUser_id());
					userAuthsBean.setId(listAuthsBeans.get(0).getId());
					respData.putAll(userAuthsService.update(userAuthsBean));
				}
			}
		}
		
		System.out.println((userBean == null) + " , " + (userAuthsBean == null));
		
		return SUCCESS;
	}
/*---------------------------------修改用户信息end----------------------------------*/
}
