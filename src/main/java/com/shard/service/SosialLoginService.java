package com.shard.service;

import java.util.HashMap;

public interface SosialLoginService {
	String getAccessToken(String code) throws Throwable;

	HashMap<String, Object> getUserInfo(String access_Token) throws Throwable;

	void setAuthentication(String email);
	
//	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException;
	
//	public String getUserNaverInfo(OAuth2AccessToken oauthToken) throws IOException;
}