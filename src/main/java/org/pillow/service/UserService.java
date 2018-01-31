package org.pillow.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.pillow.common.database.DBUtil;
import org.pillow.common.database.PageData;
import org.pillow.common.exception.WebServerError;
import org.pillow.common.exception.WebServerException;
import org.pillow.common.util.EncryptionUtil;
import org.pillow.common.util.ModelUtil;
import org.pillow.model.User;
import org.pillow.model.dto.PageParam;
import org.pillow.model.dto.UserForm;
import org.pillow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private DBUtil dbUtil;
	

	/**
	 * 根据用户名搜索
	 * @param userName 
	 * 用户名
	 * @return
	 * 符合搜索条件的用户列表
	 * @throws WebServerException 
	 */
	public List<User> getUsers(String userName) throws WebServerException{
		return userRepository.findByUserName(userName);
	}
	
	public Iterable<User> getUsers(){
		return userRepository.findAll();
	}
	
	public PageData getUsersPage(PageParam pageParam) throws WebServerException {
		return dbUtil.query(User.class).pageOffset(pageParam.getPageOffset()).pageSize(pageParam.getPageSize()).executeQuery();
	}
	
	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}
	
	public boolean addUser(UserForm userForm) throws NoSuchMethodException, WebServerException, Exception {
		//检查是否有重复
		Long count=userRepository.countByUserName(userForm.getUserName());
		if(count>0) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"用户名存在");
		}
		count=userRepository.countByUserName(userForm.getEmail());
		if(count>0) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"注册邮箱已存在");
		}
		User user = new User();
		ModelUtil.updateModel(user, userForm);
		user.setPassword(EncryptionUtil.encryptPassword(user.getPassword()));
		user.setCreateTime(new Date());
		user.setLastUpdateTime(new Date());
		userRepository.save(user);
		return true;
	}
	
	public boolean updateUser(User user) throws WebServerException, Exception {
		if(user.getId()==null) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"用户Id为空 ");
		}
		User oldUser = userRepository.findOne(user.getId());
		if(oldUser==null) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"用户不存在，error userId: "+user.getId());
		}
		ModelUtil.updateModel(oldUser, user);
		userRepository.save(oldUser);
		return true;
	}
	
	
	public boolean deleteUser(User user) throws WebServerException {
		if(user.getId()==null) {
			throw new WebServerException(WebServerError.INVALID_INPUT,"用户Id为空 ");
		}
		userRepository.delete(user);
		return true;
	}
	
	public int sql1(String userName) throws WebServerException {
		List<Object[]> result = dbUtil.setSql("select * from user where user_name = :userName").setSqlParams("userName", userName).executeSql();
		System.out.println(result.size());
		return result.size();
	}
	
	public int sql2() throws WebServerException {
		PageData pageData = dbUtil.query(User.class)
				.ge("Id", 0L)
				.orderByDesc("userName")
				.pageOffset(0)
				.pageSize(2)
				.executeQuery();
		System.out.println("total elements: "+pageData.getTotalElements());
		List contents = pageData.getPageData();
		for(Object content : contents) {
			User user =(User) content;
			System.out.println(user.getEmail());
		}
		return contents.size();
	}
	
}
