package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.DeliverAddrVO;
import com.shard.domain.ShardMemberVO;

public interface UserMapper {

	public ShardMemberVO getUser(String email); // 회원 정보

 	public String getEmail(String email); // 회원 가입할 때 이메일 체크
	
	public ShardMemberVO getUserEmail(String email); // 카카오 로그인을 했을 때 이메일로 회원 정보

	public int insertUser(ShardMemberVO vo); // 회원가입할 때
	
	public void insertAddr(DeliverAddrVO vo); // 회원가입할 때 주소 입력
	
	public int insertKakaoUser(ShardMemberVO vo); // 카카오 로그인으로 회원가입할 때

	public int updateUser(ShardMemberVO vo); // 회원정보를 업데이트할 떄

	public int deleteUser(String email); // 회원탈퇴를 할 때
	
	public String emailCheck(String email); // 카카오 로그인할 때 이메일 체크
	
	public String userCheck(String email); // 로그인할 떄 아이디로 비밀번호 체크

	public String adminCheck(String email);

	public void insertCoupon(@Param("coupon") List<Integer> coupon, @Param("email")String email); // 회원가입 했을 때 웰컴 쿠폰
	
	public void updatePwd(@Param("email") String email, @Param("pwd")String pwd);
}