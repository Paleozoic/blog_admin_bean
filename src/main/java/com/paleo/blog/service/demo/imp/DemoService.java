package com.paleo.blog.service.demo.imp;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.paleo.blog.remote.demo.IDemoService;
import com.paleo.blog.service.demo.dao.IDemoDao;

@Service("blog.service.demo.imp.DemoService")
public class DemoService implements IDemoService{

	@Resource
	private IDemoDao demoDao;
	
	@Override
	public Map test() {
		return demoDao.test();
	}

}
