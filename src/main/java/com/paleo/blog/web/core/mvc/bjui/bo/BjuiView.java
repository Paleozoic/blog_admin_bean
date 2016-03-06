package com.paleo.blog.web.core.mvc.bjui.bo;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.paleo.blog.tools.http.resource.WebResource;
import com.paleo.blog.tools.mvc.view.JsonView;

public class BjuiView {

	public final static String CALLBACK_CLOSECURRENT = "closeCurrent";

	public final static String DIV_ID = "innerContainer";
	
	/**
	 * 构造bjui返回的对象，根据文档ajax 回调函数章节
	 * @param message
	 * @param closeCurrent 是否关闭当前窗口(navtab或dialog)。
	 * @return
	 */
	public static BjuiCallback buildBJUICallback(String message, boolean closeCurrent) {
		BjuiCallback result = new BjuiCallback();
		result.setMessage(message);
		result.setCloseCurrent(closeCurrent);
		return result;
	}

	private static void jsonOut(Object obj, HttpServletResponse... response) {
		if (response != null && response.length > 0) {
			JsonView.writeOutObj(response[0], obj);
		} else {
			JsonView.writeOutObj(WebResource.response(), obj);
		}
	}

	/**
	 * 返回成功信息，不过经测试在自定义的回调函数中closeCurrent即使设为true也不起作用，还需在回调函数手动处理
	 * 	$(this)
	      .bjuiajax('ajaxDone', json)       // 信息提示
		  .navtab('refresh')               // 刷新当前navtab
		  .dialog('closeCurrent');			// 关闭当前dialog
	 * @param message  返回的消息
	 * @param objValue 封装的返回数据
	 * @param closeCurrent 是否关闭当前窗口
	 */
	public static void success(String message, Object objValue,boolean closeCurrent) {
		BjuiCallback result = buildBJUICallback(message, closeCurrent);
		result.setStatusCode(BjuiCallback.STATUS_CODE_SUCCESS);
		if (objValue != null) {
			result.setResult(objValue);
		}
		jsonOut(result);
	}
	/**
	 * 不带封装的返回数据
	 * @param message
	 * @param closeCurrent
	 */
	public static void success(String message,boolean closeCurrent) {
		success(message,null,closeCurrent);
	}


	/**
	 * 只返回信息，其他不做处理
	 * @param message
	 */
	public static void success(String message) {
		success(message,null,false);
	}

	/**
	 * 返回成功信息，刷新特定divId
	 * @param message
	 * @param divids
	 */
	public static void successAndRefreshDiv(String message, String divid) {
		BjuiCallback result = buildBJUICallback(message, true);
		result.setStatusCode(BjuiCallback.STATUS_CODE_SUCCESS);
		if (StringUtils.isNotEmpty(divid))
			result.setDivid(divid);
		jsonOut(result);
	}

	/**
	 * 返回成功信息，不过经测试在自定义的回调函数中closeCurrent即使设为true也不起作用，还需在回调函数手动处理
	 * 	$(this)
	      .bjuiajax('ajaxDone', json)       // 信息提示
		  .navtab('refresh')               // 刷新当前navtab
		  .dialog('closeCurrent');			// 关闭当前dialog
	 * @param message  返回的消息
	 * @param objValue 封装的返回数据
	 * @param closeCurrent 是否关闭当前窗口
	 */
	public static void fail(String message, Object objValue,boolean closeCurrent) {
		BjuiCallback result = buildBJUICallback(message, closeCurrent);
		result.setStatusCode(BjuiCallback.STATUS_CODE_ERROR);
		if (objValue != null) {
			result.setResult(objValue);
		}
		jsonOut(result);
	}
	/**
	 * 不带封装的返回数据
	 * @param message
	 * @param closeCurrent
	 */
	public static void fail(String message,boolean closeCurrent) {
		fail(message,null,closeCurrent);
	}

	/**
	 * TODO:只返回信息，其他不做处理
	 * @param message
	 */
	public static void fail(String message) {
		fail(message,null,false);
	}
	/**
	 * 会话超时的处理
	 */
	public static void sessionOut(HttpServletResponse... response) {
		BjuiCallback result = buildBJUICallback("会话超时，请重新登录", false);
		result.setStatusCode(BjuiCallback.STATUS_CODE_TIMEOUT);
		jsonOut(result, response);
	}

	/**
	 * 访问权限限制处理
	 */
	public static void noPriv(HttpServletResponse... response) {
		BjuiCallback result = buildBJUICallback("没有访问权限", false);
		result.setStatusCode(BjuiCallback.STATUS_CODE_ERROR);
		jsonOut(result, response);
	}

}