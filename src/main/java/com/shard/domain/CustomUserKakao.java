package com.shard.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserKakao extends User {
   
   private static final long serialVersionUID = 1L;
   
   private ShardMemberVO member;
   
   
   public CustomUserKakao(String username, String password, Collection<? extends GrantedAuthority> authorities) {
      super(username, password, authorities);
   }
   
   public CustomUserKakao(ShardMemberVO vo) {
      super(vo.getEmail(), "", 
            vo.getAuthList().stream()
               .map(auth -> createGrantedAuthority(auth.getAuthCode())) // 권한 문자열 생성 메서드 사용
               .collect(Collectors.toList()));
      this.member = vo;
   }	

   private static SimpleGrantedAuthority createGrantedAuthority(String authCode) {
      // "ROLE_" 접두사 추가
      String role = authCode.equals("1") ? "ROLE_ADMIN" : "ROLE_USER";
      System.out.println(role);
      return new SimpleGrantedAuthority(role);
   }
}
