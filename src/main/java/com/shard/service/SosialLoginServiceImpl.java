package com.shard.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shard.domain.CustomUserKakao;
import com.shard.domain.ShardMemberVO;
import com.shard.mapper.MemberMapper;

@Service
public class SosialLoginServiceImpl implements SosialLoginService {

	@Autowired
	private MemberMapper mapper;

//	private final static String CLIENT_ID = "wpeMKe4_8YEoNX1CpngP"; // 네이버API Client ID
//	private final static String CLIENT_SECRET = "dZfN5TxTY6";
//	private final static String REDIRECT_URI = "http://localhost:8181/shard/login/naver";
//	private final static String SESSION_STATE = "oauth_state";
//	/* 프로필 조회 API URL */
//	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";/// Api 종류 기본 !!

	@Override
	public String getAccessToken(String authorize_code) throws Throwable {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로

			con.setRequestMethod("POST");
			con.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");

			sb.append("&client_id=4f8fd0ea2b58d54fc209c75db615c0e7"); // REST_API키 본인이 발급받은 key 넣어주기
			sb.append("&redirect_uri=http://localhost:8181/shard/login/oauth"); // REDIRECT_URI 본인이 설정한 주소 넣어주기

			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.close();

			int responseCode = con.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
			});
			access_Token = jsonMap.get("access_token").toString();
			refresh_Token = jsonMap.get("refresh_token").toString();

			System.out.println("access_Token : " + access_Token);
			System.out.println("refresh_Token : " + refresh_Token);

			br.close();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return access_Token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getUserInfo(String access_Token) throws Throwable {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = con.getResponseCode();
			System.out.println("responseCode" + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response Body" + result);
			System.out.println("response type" + result.getClass().getName());

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
				});

				System.out.println(jsonMap.get("properties"));

				Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
				Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("kakao_account");

				String nickname = properties.get("nickname").toString();
				String email = kakao_account.get("email").toString();

				userInfo.put("nickName", nickname);
				userInfo.put("email", email);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userInfo;
	}

	@Override
	public void setAuthentication(String email) {
		// 이메일을 기반으로 데이터베이스에서 사용자 정보를 조회
		ShardMemberVO kakaoUser = mapper.read(email);

		List<GrantedAuthority> authorities = kakaoUser.getAuthList().stream()
				.map(auth -> createGrantedAuthority(auth.getAuthCode())).collect(Collectors.toList());

		// 사용자 정보를 UserDetails로 변환
		UserDetails userDetails = new CustomUserKakao(kakaoUser);

		// 스프링 시큐리티 컨텍스트에 인증 정보 설정
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private static SimpleGrantedAuthority createGrantedAuthority(String authCode) {
		// "ROLE_" 접두사 추가
		String role = authCode.equals("1") ? "ROLE_ADMIN" : "ROLE_USER";
		System.out.println(role);
		return new SimpleGrantedAuthority(role);
	}
}
