package org.pillow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pillow.Application;
import org.pillow.common.exception.WebServerException;
import org.pillow.model.User;
import org.pillow.model.dto.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class UserServiceTest{
	
	@Autowired
	UserService userService;
	
	@Test
	public void testSql1() throws WebServerException {
		//正确的用户名
		int num=userService.sql1("Euler");
		assertEquals(num, 1);
		//错误的用户名
		num=userService.sql1("Euler_X");
		assertEquals(num, 0);
	}
	
	@Test
	public void testSql2() throws WebServerException {
		int num=userService.sql2();
		assertTrue(num>=1);
	}
	
	@Test
	public void testAddUser() throws NoSuchMethodException, Exception {
		//验证待添加用户名不存在
		List<User> tUsers = userService.getUsers("Dirichlet_1234");
		assertEquals(tUsers.size(), 0);
		//添加用户，用户名需要很独
		UserForm user = new UserForm();
		user.setUserName("Dirichlet");
		user.setRealName("Peter Dirichlet");
		user.setEmail("dirichlet@math.de");
		user.setPassword("123456");
		user.setPhoneNo("131415926");
		user.setIsDisabled(false);
		user.setFirstName("Peter");
		user.setLastName("Dirichlet");
		userService.addUser(user);
		//根据用户名搜索
		tUsers = userService.getUsers("Dirichlet_1234");
		assertTrue(tUsers.size()>0);
	}
	
	@Test
	public void testUpdateUser() throws WebServerException, Exception {
		//更新之前验证待更新项不正确
		User oUser = userService.getUserById(4L);
		assertNotEquals(oUser.getPassword(),"aawwee");
		//更新
		User user = new User();
		user.setId(4L);
		user.setIsDisabled(false);
		user.setPassword("aawwee");
		userService.updateUser(user);
		//更新之后验证更新项正确
		oUser = userService.getUserById(4L);
		assertEquals(oUser.getPassword(),"aawwee");
	}
	
	@Test
	public void testDeleteUser() throws WebServerException {
		//删除之前验证用户存在
		User oUser = userService.getUserById(4L);
		assertNotNull(oUser);
		//删除用户
		User user = new User();
		user.setId(4L);
		userService.deleteUser(user);
		//删除之后验证用户不存在
		oUser = userService.getUserById(4L);
		assertNull(oUser);
	}
	
}