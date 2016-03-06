package com.paleo.blog.web.core.mvc.freemarker;

import java.util.HashMap;

import com.paleo.blog.tools.uuid.UUIDUtils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * 
 * @author mzz
 *
 */
public class FreemarkerStaticModels extends HashMap<Object, Object> {

	private static final long serialVersionUID = 6802382783838457088L;
	private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS;

	private FreemarkerStaticModels() {

	}

	public static FreemarkerStaticModels getInstance() {
		if (FREEMARKER_STATIC_MODELS == null) {
			FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();
			// 初始化,FreeMarker需要调用哪个工具类，就放进这里
			FREEMARKER_STATIC_MODELS.put(UUIDUtils.class.getSimpleName(), useStaticPackage(UUIDUtils.class.getName()));
		}
		return FREEMARKER_STATIC_MODELS;
	}

	public static TemplateHashModel useStaticPackage(String packageName) {
		try {
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();
			TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
			return fileStatics;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
