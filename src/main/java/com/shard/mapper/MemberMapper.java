package com.shard.mapper;

import com.shard.domain.ShardMemberVO;

public interface MemberMapper {
	
	public ShardMemberVO read(String email);
}
