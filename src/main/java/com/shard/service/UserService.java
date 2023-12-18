package com.shard.service;

import java.util.List;

import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.ShardMemberVO;

public interface UserService {
	public int userCheck(String email, String userPwd);
	
	public ShardMemberVO getUser(String email);
	
	public int idCheck(String email);

	public ShardMemberVO getUserEmail(String email);

	public int insertUser(ShardMemberVO vo);
	
	public void insertAddr(DeliverAddrVO vo);
	
	public int insertKakaoUser(ShardMemberVO vo);

	public int updateUser(ShardMemberVO vo);

	public int deleteUser(String email);
	
	public int emailCheck(String email);

	public int adminCheck(String email, String userPwd);
	
	public void insertCoupon(List<Integer> coupon, String email);
	
	public void updatePwd(String email, String pwd);
}
