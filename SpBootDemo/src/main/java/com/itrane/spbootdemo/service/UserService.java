package com.itrane.spbootdemo.service;

import java.util.List;

import com.itrane.spbootdemo.model.User;

/**
 * UserDetailsService を拡張したユーザー・サービス.
 */
public interface UserService  {
	
	public List<User> findPage(int offset, int pageRows,
			String searchStr, String sortDir, String...sortCols);
	public User save(User e) throws DbAccessException;
}
