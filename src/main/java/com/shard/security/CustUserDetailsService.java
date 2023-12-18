package com.shard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shard.domain.CustomUser;
import com.shard.domain.ShardMemberVO;
import com.shard.mapper.MemberMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberMapper mapper;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ShardMemberVO vo = mapper.read(username);

        if (vo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 사용자를 찾은 경우 CustomUser 객체를 명시적으로 생성하여 반환
        return new CustomUser(vo);
    }
}