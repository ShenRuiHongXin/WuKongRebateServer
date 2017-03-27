package com.shenrui.wukong.action;

import java.util.HashMap;
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
import com.shenrui.wukong.service.BookService;
import com.shenrui.wukong.vo.AuctionInfosBean;
import com.shenrui.wukong.vo.Book;

@Namespace("/")
@ParentPackage("json-default")
@Controller("bookAction")
@Scope("prototype")
@Results( { @Result(name = ActionSupport.SUCCESS, type = "json"),
@Result(name = ActionSupport.ERROR, type = "json") })
public class BookAction extends ActionSupport implements ModelDriven<Book> {
	//在Action中注入Service
	@Autowired
	@Qualifier("bookService")
	private BookService bookService;
	
	//
	private Map<String, Object> jdata;
	
	public Map<String, Object> getJdata() {
		return jdata;
	}
	public void setJdata(Map<String, Object> jdata) {
		this.jdata = jdata;
	}

	// 模型驱动类
	private Book book = new Book();
	public Book getModel() {
		return book;
	}
	
	//处理请求的方法
	@Action(value="book_add")
	public String add(){
		System.out.println("web层的添加执行了......");
		bookService.add(book);
		return NONE;
	}
	
	@Action(value="login", results={@Result(type="json", params={"root","jdata"})})
	public String login(){
		//1.获取ActionContext
		ActionContext actionContext = ActionContext.getContext();

		//2.获取Servlet api
		
		//2.1获取请求参数
		Map<String,Object> paramMap = actionContext.getParameters();
//		for (Map.Entry<String,Object> entry : paramMap.entrySet()) {
//			System.out.println("key = " + entry.getKey() + " / " + "value = " + ((String[])entry.getValue())[0]);
//		}
		
		jdata = new HashMap<String, Object>();
		
		System.out.println(((String[])paramMap.get("userInfo"))[0]);
		
		jdata.put("user", (((String[])paramMap.get("userInfo"))[0]));
		
		
		String json = "{\"detail_order_id\":\"2990208639346416\",\"auction_id\":\"AAF5IoxbAD0bHYrQFRJFGSo2\",\"real_pay\":\"59.80\",\"auction_pict_url\":\"i2/1579139371/TB2liuTX9BjpuFjy1XdXXaooVXa_!!1579139371.jpg\",\"auction_title\":\"加绒加厚打底裤女士女裤显瘦紧身黑色小脚高腰保暖长裤秋冬季外穿\",\"auction_amount\":\"2\"}";
				
		AuctionInfosBean aInfosBean = new Gson().fromJson(json, AuctionInfosBean.class);
		
		return SUCCESS;
	}

}
