package org.pillow.controller;

import java.util.ArrayList;

import org.pillow.common.exception.WebServerException;
import org.pillow.common.response.BaseResponse;
import org.pillow.common.response.ListResponse;
import org.pillow.common.response.PageResponse;
import org.pillow.common.util.ModelUtil;
import org.pillow.model.User;
import org.pillow.model.dto.PageData;
import org.pillow.model.dto.PageParam;
import org.pillow.model.dto.UserForm;
import org.pillow.model.dto.UserListItem;
import org.pillow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	UserService userService;
	
	/**
	 * 获取全部用户
	 * @return
	 * @throws WebServerException
	 */
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ListResponse<UserListItem> getAllUsers() throws WebServerException, Exception {
		Iterable<User> users = userService.getUsers();
		ArrayList<UserListItem> listData = ModelUtil.<UserListItem>convertList(users, UserListItem.class);
		return new ListResponse<UserListItem>(listData);
	}
	
	/**
	 * 获取分页用户
	 * @param pageParam
	 * @return
	 * @throws WebServerException
	 * @throws Exception
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public PageResponse<UserListItem> getUsersPage(PageParam pageParam) throws WebServerException, Exception{
		PageData pageData = userService.getUsersPage(pageParam);
		ArrayList<UserListItem> listData = ModelUtil.<UserListItem>convertList(pageData.getPageData(), UserListItem.class);
		return new PageResponse<UserListItem>(pageData.getTotalElements(), pageData.getPageNum(), listData);
	}
	
	/**
	 * 创建用户
	 * @param userForm
	 * @return
	 * @throws Exception 
	 * @throws WebServerException 
	 * @throws NoSuchMethodException 
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public BaseResponse addUser(@RequestBody UserForm userForm) throws NoSuchMethodException, WebServerException, Exception {
		userService.addUser(userForm);
		return new BaseResponse();
	}
	
}
