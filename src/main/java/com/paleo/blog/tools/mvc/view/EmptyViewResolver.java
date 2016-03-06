package com.paleo.blog.tools.mvc.view;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;

public class EmptyViewResolver extends AbstractView implements ViewResolver,Ordered{
	
	public final static String EMPTY_VIEW = "_empty_view";
	
	private int order;
	
	public void setOrder(int order){
		this.order=order;
	}
	
	@Override
	public int getOrder() {return this.order;}
	
	@Override
	public View resolveViewName(String viewName, Locale locale){
		if(StringUtils.isEmpty(viewName) || viewName.equals(EMPTY_VIEW)){return this;}
		return null;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
	}
	
}