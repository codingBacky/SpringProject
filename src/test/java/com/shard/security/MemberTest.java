package com.shard.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml" })
@Log4j
public class MemberTest {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncorder;

	@Test
	public void testInsertMember() {
		String sql = "insert into customer(email, userpwd, username, phone, dob,authCode) values(?,?,?,?,?,?)";

		for (int i = 61; i <= 100; i++) {
			try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, "user"+i+"@user.com");
				ps.setString(2, passwordEncorder.encode("user"+i));
				ps.setString(3, "이름"+i);
				ps.setString(4, "010-0000-0000");
				ps.setString(5, "2000-11-29");
				ps.setInt(6, 0);

				ps.executeUpdate();

			} catch (Exception e) {

			}
		}

	}
}