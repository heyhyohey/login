package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.Dao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import model.UserInfo;

/**
 * <pre>
 * 로그인 처리 서비스 클래스
 * </pre>
 * @author hyoje
 * @version 1.0
 * @since 2018-09-20
 */
public class LoginVerificationService {
	private static LoginVerificationService instance = new LoginVerificationService();
	
	public static LoginVerificationService getInstance() {
		return instance;
	}
	
	private LoginVerificationService() {
	}
	
	/**
	 * <pre>
	 * 비밀번호 일치여부 처리 메소드
	 * </pre>
	 * @param userId
	 * @param userPw
	 * @return boolean
	 */
	public boolean verifyLogin(String userId, String userPw) {
		Connection conn = null;
		UserInfo userInfo = null;
		try {
			conn = ConnectionProvider.getConnection();
			Dao dao = Dao.getInstance();
			userInfo = dao.select(conn, userId);
			
			// 1. 아이디가 존재하는지 검사
			if(userInfo == null)
				return false;
			
			// 2. 아이디 비밀번호 일치 검사
			if(userInfo.getUserPw().equals(userPw))
				return true;
			else
				return false;
		} catch (SQLException e) {
			throw new ServiceException("로그인 실패: " + e.getMessage(),e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
