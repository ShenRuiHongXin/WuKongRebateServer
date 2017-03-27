package com.shenrui.wukong.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.Gson;
import com.shenrui.wukong.dao.PaidInfoDao;
import com.shenrui.wukong.dao.TaobaoTradeDao;
import com.shenrui.wukong.dao.UserDao;
import com.shenrui.wukong.vo.PaidInfoBean;
import com.shenrui.wukong.vo.TaobaoTradeBean;
import com.shenrui.wukong.vo.UserBean;
import com.shenrui.wukong.vo.UserOnlineInfoBean;
import com.shenrui.wukong.votmp.TaoBaoTrade;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;

/**
 * 获取订阅的淘宝后台的交易变更消息通知，启动TMC长连接，接收消息写入数据库
 * @author created by heikki 2017-1-12 下午5:57:31
 *
 */
public class GetTaobaoTMC {
	private TaobaoTradeDao taobaoTradeDao;
	public TaobaoTradeDao getTaobaoTradeDao() {
		return taobaoTradeDao;
	}
	public void setTaobaoTradeDao(TaobaoTradeDao taobaoTradeDao) {
		this.taobaoTradeDao = taobaoTradeDao;
	}
	
	@Autowired
	@Qualifier("paidInfoDao")
	private PaidInfoDao paidInfoDao;
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	public void getTaobaoTrade(String json,String msgType){
//		String testStr = "{\"buyer_id\":\"AAHgIoxYAD0bHYrQFWuIFScL\",\"extre\":\"\",\"paid_fee\":\"59.80\",\"shop_title\":\"雅羊人旗舰店\",\"is_eticket\":false,\"create_order_time\":\"2017-01-12 14:04:18\",\"order_id\":\"2990208639346416\",\"order_status\":\"7\",\"seller_nick\":\"雅羊人旗舰店\",\"auction_infos\":[{\"detail_order_id\":\"2990208639346416\",\"auction_id\":\"AAF5IoxbAD0bHYrQFRJFGSo2\",\"real_pay\":\"59.80\",\"auction_pict_url\":\"i2/1579139371/TB2liuTX9BjpuFjy1XdXXaooVXa_!!1579139371.jpg\",\"auction_title\":\"加绒加厚打底裤女士女裤显瘦紧身黑色小脚高腰保暖长裤秋冬季外穿\",\"auction_amount\":\"2\"}]}";
		TaoBaoTrade taoBaoTrade =new Gson().fromJson(json, TaoBaoTrade.class);
		System.out.println("**********************************************************************************");
		System.out.println(taoBaoTrade.toString());
		
		
		TaobaoTradeBean taobaoTradeBean = new TaobaoTradeBean();
		taobaoTradeBean.setAuctionInfos(new Gson().toJson(taoBaoTrade.getAuction_infos()));
		taobaoTradeBean.setBuyer_id(taoBaoTrade.getBuyer_id());
		taobaoTradeBean.setCreate_order_time(taoBaoTrade.getCreate_order_time());
		taobaoTradeBean.setExtre(taoBaoTrade.getExtre());
		taobaoTradeBean.setIs_eticket(taoBaoTrade.isIs_eticket());
		taobaoTradeBean.setOrder_id(taoBaoTrade.getOrder_id());
		taobaoTradeBean.setOrder_status(taoBaoTrade.getOrder_status());
		taobaoTradeBean.setPaid_fee(taoBaoTrade.getPaid_fee());
		taobaoTradeBean.setSeller_nick(taoBaoTrade.getSeller_nick());
		taobaoTradeBean.setShop_title(taoBaoTrade.getShop_title());
		taobaoTradeBean.setOrder_type(msgType);
		System.out.println("**********************************************************************************");
		System.out.println(taobaoTradeBean.toString());
		
		this.taobaoTradeDao.saveTaobaoTrade(taobaoTradeBean);
		
		if(Constant.TradeType.TRADE_PAID_DONE.equals(msgType) || 
				Constant.TradeType.TRADE_REFUND_SUCCESS.equals(msgType) ||
				Constant.TradeType.TRADE_SUCCESS.equals(msgType)){
			
			DetachedCriteria criteria = DetachedCriteria.forClass(PaidInfoBean.class);
			criteria.add(Restrictions.eq("tb_account_id",taobaoTradeBean.getBuyer_id()));
			criteria.add(Restrictions.eq("tb_trade_id",taobaoTradeBean.getOrder_id()));
			
			List<PaidInfoBean> listPaidInfoBeans = paidInfoDao.findByCriteria(criteria);
			
			PaidInfoBean paidInfoBean = listPaidInfoBeans.get(0);
			double rebateAmount = paidInfoBean.getRebate_amount();
			int userId = paidInfoBean.getUser_id();
			System.out.println("*************************");
			System.out.println("用户ID：" + userId + "返利金额: " + rebateAmount);
			
			UserBean userBean = userDao.findById(userId);
			if(Constant.TradeType.TRADE_PAID_DONE.equals(msgType)){
				userBean.setTmp_balance(userBean.getTmp_balance()+rebateAmount);
			}else if(Constant.TradeType.TRADE_REFUND_SUCCESS.equals(msgType)){
				userBean.setTmp_balance(userBean.getTmp_balance()-rebateAmount);
			}else{
				userBean.setTmp_balance(userBean.getTmp_balance()-rebateAmount);
				userBean.setBalance(userBean.getBalance() + rebateAmount);
			}
			userDao.update(userBean);
		}
	}
	

	public void getTaobaoMessage() {
		System.out.println("***************************TMC初始化****************************");
		
		TmcClient client = new TmcClient("23585348",
				"ac4a7853f5ae6b37642358c8ba8c7aef", "default");// 请求地址sdk默认已经封装
		client.setMessageHandler(new MessageHandler() {
			public void onMessage(Message message, MessageStatus status) {
				try {
					System.out.println("============");
					System.out.println(message.getContent());
					System.out.println(message.getTopic());

					getTaobaoTrade(message.getContent(),message.getTopic());

					// 默认不抛出异常则认为消息处理成功
				} catch (Exception e) {
					e.printStackTrace();
					status.fail();// 消息处理失败回滚，服务端需要重发
				}
			}
		});
		try {
			client.connect();
			System.out.println("***********************TMC是否在线 ："+ client.isOnline()+"***********************");
			while(!client.isOnline()){
				System.out.println("*********************TMC连接失败，准备重试！*********************");
				client.connect();
			}
		} catch (LinkException e) {
			System.out.println("****************TMC连接失败,请手动重启服务器重新连接！****************");
//			e.printStackTrace();
		}
		if(client.isOnline()){
			System.out.println("*************************TMC初始化完成**************************");
		}
	}

}
