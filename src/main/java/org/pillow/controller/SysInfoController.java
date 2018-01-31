package org.pillow.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**  
 * 获取系统基本信息
 * @author peilu.wang
 */
@RestController
@RequestMapping(value = "/sysinfo")
public class SysInfoController {

	@Autowired
	private Environment env; //用来读取application.properties
	
	/**
	 * 返回Hello World字符串，用于展示系统存活
	 */
	@RequestMapping("")
	public String sayHello() {
		return "Hello World!";
	}
	
	@RequestMapping("/version")
	public String version() {
		String version = env.getProperty("sys.version");
		return version;
	}
	
	@RequestMapping("/all")
	public HashMap<String, String> all() {
		String version = env.getProperty("version");
		String envType = env.getProperty("sys.envType");
		String path = System.getProperty("user.dir");
		HashMap<String, String> dict = new HashMap<String,String>();
		dict.put("version", version);
		dict.put("path", path);
		dict.put("env", envType);
		return dict;
	}
	
}
