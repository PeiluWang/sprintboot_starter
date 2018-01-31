package org.pillow.service.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;
import org.pillow.common.exception.WebServerException;
import org.pillow.common.util.ModelUtil;
import org.pillow.model.User;

public class ModelUtilTest {
	
	@Test
	public void testUpdateModel() throws NoSuchMethodException, Exception, WebServerException {
		User originUser = new User();
		originUser.setUserName("Dirichlet");
		originUser.setRealName("Peter Dirichlet");
		originUser.setEmail("dirichlet@math.de");
		originUser.setPassword("123456");
		originUser.setPhoneNo("131415926");
		originUser.setCreateTime(new Date());
		originUser.setLastUpdateTime(new Date());
		User updateUser = new User();
		updateUser.setUserName("Euler");
		updateUser.setPhoneNo("12345678");
		ModelUtil.updateModel(originUser, updateUser);
		System.out.println(originUser.getUserName());
		System.out.println(originUser.getPhoneNo());
		System.out.println(originUser.getPassword());
	}
	
	@Test
	public void test() {
		Map returnMap = new HashMap();  
		returnMap.put("aa", 1);
		returnMap.put("bb", 2);
		JSONObject jObj = new JSONObject(returnMap);
		System.out.println(jObj.toString());
	}
	
}
