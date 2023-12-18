package com.shard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shard.domain.DeliverAddrVO;
import com.shard.domain.ShardMemberVO;
import com.shard.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper mapper;
	
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public int userCheck(String email, String userPwd) {
		int result = 0;
		String pwd = mapper.userCheck(email);
		boolean realPwd = passwordEncoder.matches(userPwd, pwd);
		if(realPwd) {
			result = 1;
		}else 
			result = 0;
		
		return result;
	}

	@Override
	public ShardMemberVO getUser(String email) {
		return mapper.getUser(email);
	}

	@Override
	public int idCheck(String email) { // 회원가입할 때 id 체크
		int result = -1;

		String id = mapper.getEmail(email);
		System.out.println(id);

		if (id != null) // 데이터베이스에 아이디가 있을 때
			result = 1;
		else // 데이터베이스에 아이디가 없을 때
			result = 0;

		return result;
	}

	@Override
	public int insertUser(ShardMemberVO vo) {
		return mapper.insertUser(vo);
	}
	
	@Override
	public int insertKakaoUser(ShardMemberVO vo) {
		return mapper.insertKakaoUser(vo);
	}

	@Override
	public int updateUser(ShardMemberVO vo) {
		return mapper.updateUser(vo);
	}

	@Override
	public int deleteUser(String email) {
		return mapper.deleteUser(email);
	}

	@Override
	public ShardMemberVO getUserEmail(String email) {
		return mapper.getUserEmail(email);
	}


	@Override
	public int adminCheck(String email, String userPwd) {
		int result = 0;
		String pwd = mapper.adminCheck(email);
		if(pwd == null) {
			result = -1;
		}else {
			if(pwd.equals(userPwd)) {
				result = 1;
			}else {
				result = 0;
			}
		}
		return result;
	}

	@Override
	public int emailCheck(String email) {
		int result = 0;
		String userEmail = mapper.emailCheck(email);
		if(userEmail != null) {
			result = 1;
		}else {
			result = 0;
		}
		return result;
	}

	@Override
	public void insertAddr(DeliverAddrVO vo) {
		mapper.insertAddr(vo);
	}

	@Override
	public void insertCoupon(List<Integer> coupon, String email) {
		mapper.insertCoupon(coupon, email);
	}

	@Override
	public void updatePwd(String email, String pwd) {
		mapper.updatePwd(email, pwd);
	}
}
