package org.pillow.repository;

import java.util.List;

import org.pillow.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findByUserName(String userName);
	
	public List<User> findByEmail(String email);
	
	public List<User> findByPhoneNo(String phone);
	
	/*
	 * 根据用户名或邮件查找
	 * 用于创建用户时的过滤
	 */
	public Long countByUserNameOrEmail(String userName, String email);
	
	public Long countByUserName(String userName);
	
	public Long countByEmail(String email);
}
