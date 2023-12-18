package com.shard.mapper;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.ShardMemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class UserMapperTest {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;


	@Test
	public void test() {
		List<Integer> coupon = Arrays.asList(1, 2, 3);
		mapper.insertCoupon(coupon, "user1@admin.com");
	}

	@Test
	public void update() {
		String pwd = encoder.encode("faker123!@");
		ShardMemberVO vo = ShardMemberVO.builder()
							.userName("이름름")
							.userPwd(pwd)
							.email("user1@user.com")
							.build();
		mapper.updateUser(vo);
	}
}
