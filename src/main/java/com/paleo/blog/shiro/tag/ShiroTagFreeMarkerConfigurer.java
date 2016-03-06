package com.paleo.blog.shiro.tag;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateException;
/**
 * http://www.woxplife.com/articles/473.html
 * @author Paleo 
 *
 */
public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {
 
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
     
}
