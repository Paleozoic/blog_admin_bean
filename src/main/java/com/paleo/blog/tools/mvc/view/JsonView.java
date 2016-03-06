package com.paleo.blog.tools.mvc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.paleo.blog.tools.GlobalData;
import com.paleo.blog.tools.exception.ResourceException;
import com.paleo.blog.tools.json.jackson.JacksonUtils;

/**
 * http 的json 的数据工具类
 * @author hc Paleo修改于2015-12
 */
public class JsonView {
	
	public static final int JSON = 1;
	public static final int HTML = 2;
	
	
	/**
	 * 可设定状态码和contentType
	 * @param response
	 * @param json
	 * @param jsonOutType 是否以application/json类型输出到视图
	 * @param httpStatus 状态码 404 200 500等
	 */
	public static void writeOutStr(HttpServletResponse response, String  json,int contentType,Integer httpStatus) {
		if(httpStatus!=null){response.setStatus(httpStatus);}
		// 因为兼容性问题，暂时去除.application/json IE10以下不支持
		if (contentType==JSON) {
			response.setContentType("application/json;charset="+GlobalData.DEFAULT_CHARSET);
		} else if (contentType==HTML){
			response.setContentType("text/html;charset="+GlobalData.DEFAULT_CHARSET);
		}else{//默认
			response.setContentType("text/html;charset="+GlobalData.DEFAULT_CHARSET);
		}
		PrintWriter out = null;
		int length;
		try {
			length = json.getBytes(GlobalData.DEFAULT_CHARSET).length;
			response.setContentLength(length);
			out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			throw  new ResourceException(e);
		}finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 可设定状态码，contentType为text/html;charset=utf-8
	 * @param response
	 * @param json
	 * @param httpStatus
	 */
	public static void writeOutStr(HttpServletResponse response, String  json,Integer httpStatus) {
		if(httpStatus!=null){response.setStatus(httpStatus);}
		response.setContentType("text/html;charset="+GlobalData.DEFAULT_CHARSET);
		PrintWriter out = null;
		int length;
		try {
			length = json.getBytes(GlobalData.DEFAULT_CHARSET).length;
			response.setContentLength(length);
			out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			throw  new ResourceException(e);
		}finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * :可设定状态码和contentType，传送参数为对象，内部转Json
	 * @param response
	 * @param obj
	 * @param contentType 
	 * @param httpStatus http status code  500 404 之类的
	 */
	public static void writeOutObj(HttpServletResponse response, Object obj,int contentType,Integer httpStatus) {
		String json = JacksonUtils.obj2Json(obj);
		writeOutStr(response,json,contentType,httpStatus);
	}
	public static void writeOutObj(HttpServletResponse response, Object obj) {
		String json = JacksonUtils.obj2Json(obj);
		writeOutStr(response,json,HTML,HttpStatusCode.OK.getCode());
	}
	
	
	/**
	 * http状态码200,contentType为text/html;charset=utf-8
	 * @param response
	 * @param json
	 */
	public static void success(HttpServletResponse response, String json) {
		response.setContentType("text/html;charset="+GlobalData.DEFAULT_CHARSET);
		PrintWriter out = null;
		int length;
		try {
			length = json.getBytes(GlobalData.DEFAULT_CHARSET).length;
			response.setContentLength(length);
			response.setStatus(HttpStatusCode.OK.getCode());
			out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			throw  new ResourceException(e);
		}finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * http状态码300,contentType位text/html;charset=utf-8
	 * @param response
	 * @param json
	 */
	public static void error(HttpServletResponse response, String json) {
		response.setContentType("text/html;charset="+GlobalData.DEFAULT_CHARSET);
		PrintWriter out = null;
		int length;
		try {
			length = json.getBytes(GlobalData.DEFAULT_CHARSET).length;
			response.setContentLength(length);
			response.setStatus(HttpStatusCode.ERROR.getCode());
			out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			throw  new ResourceException(e);
		}finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
}