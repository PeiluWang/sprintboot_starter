package org.pillow.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pillow.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class UserControllerTest {

	MockMvc mvc;
	
	@Autowired
	UserController userController;
	
	/*
	 * 在每个函数之前调用
	 */
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
	
    /**
     * 测试post接口
     * @throws Exception
     */
	@Test
	public void testAddUser() throws Exception {
		String uri = "/users";
		
		JSONObject reqObj = new JSONObject();
		reqObj.put("userName", "Newton");
		reqObj.put("realName", "Isaac Newton");
		reqObj.put("firstName", "Issac");
		reqObj.put("lastName", "Newton");
		reqObj.put("password", "123456");
		reqObj.put("email", "newtown@physics.en");
		reqObj.put("phoneNo", "12345678");
		String responseStr = mvc.perform(post(uri)
				.content(reqObj.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertEquals(responseStr,"success");
	}
	
	/**
	 * 测试get接口
	 * @throws Exception
	 */
	@Test
	public void testGetUser() throws Exception {
		String uri = "/users";
		JSONObject reqObj = new JSONObject();
		reqObj.put("pageOffset", "0");
		reqObj.put("pageSize", "3");
		String responseStr = mvc.perform(get(uri)
				.content(reqObj.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		JSONObject responseObj = new JSONObject(responseStr);
		assertTrue(responseObj.getInt("count")>0);
		assertTrue(responseObj.getJSONArray("data").length()>0);
		System.out.println(responseStr);
	}
	
	
}
