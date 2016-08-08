package com.itrane.spbootdemo.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.eclipse.persistence.exceptions.OptimisticLockException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itrane.spbootdemo.model.User;
import com.itrane.spbootdemo.repo.UserRepository;

/**
 * ユーザーのサービス実装.
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Resource private UserRepository repo;
	
	public UserServiceImpl() {}

	@Override
	public List<User> findPage(int offset, int pageRows, 
			String searchStr, String sortDir, String... sortCols) {
		Sort.Direction dir = Sort.Direction.ASC;
		if (sortDir.equals("desc")) {
			dir = Sort.Direction.DESC;
		}
		int pageNum = offset / pageRows;
		PageRequest request = new PageRequest(pageNum, pageRows, dir, sortCols);
		
		if (searchStr.equals("")) {
			return repo.findPage(request);
		} else {
			return repo.findPage(searchStr, request);
		}
	}

	@Override
	public User save(User e) throws DbAccessException {
		User p = null;
		try {
			p = repo.save(e);
		} catch (Exception ex) {
			for(Throwable t = ex.getCause(); t != null; t = t.getCause()) {
				if (t instanceof OptimisticLockException) {
					throw new DbAccessException("楽観的ロックエラー", 
							DbAccessException.OPTIMISTICK_LOCK_ERROR);
				} else if (t instanceof SQLException) {
					SQLException sqlex = (SQLException) t;  //ルート例外を SQL Exceptionでキャスト
					if(sqlex.getSQLState().equals("23000") && sqlex.getErrorCode()==1062) {
						throw new DbAccessException("重複エラー",
								DbAccessException.DUPLICATE_ERROR);
					} 
				}
			}
			throw new DbAccessException("その他のDBエラー",
					DbAccessException.OTHER_ERROR);
		}
		return p;
	}
}
