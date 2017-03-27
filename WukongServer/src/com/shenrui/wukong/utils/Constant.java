package com.shenrui.wukong.utils;

/**
 * 
 * @author created by heikki 2017-1-12 下午1:39:21
 *
 */
public class Constant {
	
	/**
	* 淘宝订单消息类型
	*/
	public interface TradeType{
		//创建订单
		static final String TRADE_CREATED 			= "taobao_tae_BaichuanTradeCreated";
		//交易关闭
		static final String TRADE_CLOSED 			= "taobao_tae_BaichuanTradeClosed";
		//付款成功
		static final String TRADE_PAID_DONE 		= "taobao_tae_BaichuanTradePaidDone";
		//创建退款消息
		static final String TRADE_REFUND_CREATED 	= "taobao_tae_BaichuanTradeRefundCreated";
		//退款成功
		static final String TRADE_REFUND_SUCCESS	= "taobao_tae_BaichuanTradeRefundSuccess";
		//交易成功
		static final String TRADE_SUCCESS 			= "taobao_tae_BaichuanTradeSuccess";
	}
	
	/**
	 * 接口返回码
	 * @author created by heikki 2017-1-14 上午11:04:27
	 *
	 */
	public interface ResultCode{
		static final int SUCCESS 		= 200;	//成功
		static final int FAILED 		= 400;	//失败，通用错误码，无具体原因
		static final int HAS_REGISTED 	= 401;	//已经注册，用于用户注册，用户输入帐号已被注册
		static final int NOT_REGISTED 	= 402;	//尚未注册，用于用户登录，用户输入的账号还未注册
		static final int PWD_ERROR 		= 403;	//密码错误
	}
	
	/**
	 * 账户注册类型
	 * @author created by heikki 2017-1-14 上午11:06:03
	 *
	 */
	public interface IdentityType{
		static final String PHONE 	= "phone";	//手机注册
		static final String TAOBAO 	= "taobao";	//淘宝账号三方登录
	}

}
