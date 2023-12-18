package com.shard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemVO;
import com.shard.domain.NoticeVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.PageVO;
import com.shard.domain.QnAVO;
import com.shard.domain.ShardMemberVO;
import com.shard.mapper.AdminMapper;
import com.shard.util.ImgUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper mapper;
	
	private ImgUtil util = ImgUtil.getInstence();

	@Override
	public List<ShardMemberVO> userList(PageVO vo) {
		return mapper.userList(vo);
	}

	@Override
	public List<ItemVO> itemList(PageVO vo) {
		return mapper.itemList(vo);
	}

	@Override
	public int userCount() {
		return mapper.userCount();
	}

	@Override
	public int itemCount() {
		return mapper.itemCount();
	}

	@Override
	public List<QnAVO> noEnswerList(PageVO vo) {
		return mapper.noEnswerList(vo);
	}

	@Override
	public int noEnswerCount() {
		return mapper.noEnswerCount();
	}

	@Override
	public List<ShardMemberVO> userSearchList(String name, PageVO vo) {
		return mapper.userSearchList(name, vo);
	}

	@Override
	public List<ItemVO> itemSearchList(String itemName, PageVO vo) {
		return mapper.itemSearchList(itemName, vo);
	}

	@Override
	public int userCount(String name) {
		return mapper.userSearchCount(name);
	}

	@Override
	public int itemCount(String itemName) {
		return mapper.itemSearchCount(itemName);
	}

	@Override
	public NoticeVO getNotice(int noticeNum) {
		return mapper.getNotice(noticeNum);
	}

	@Override
	public int insertNotice(NoticeVO vo, MultipartFile file) {
		int result = 0;

		// 파일이 비어 있는지 확인
		if (file.isEmpty()) {
			result = -1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
			return result;
		}

		// 파일 크기 제한
		if (file.getSize() > 5 * 1024 * 1024) { // 5MB 제한 (설정 가능)
			result = -1; // 파일 크기는 5MB를 초과할 수 없습니다.
			return result;
		}

		// 파일 확장자 확인
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
			result = -2; // 지원하지 않는 파일 형식입니다. (.jpg, .jpeg, .png만 허용)
			return result;
		}

		String success = util.storeFile(file);

		if (success != null) {
			vo.setNoticeImg("/img/"+success);
			mapper.insertNotice(vo);
			result = 1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
		}

		return result;
	}

	@Override
	public void deleteNotice(int noticeNum) {
		mapper.deleteNotice(noticeNum);
	}

	@Override
	public void updateNotice(NoticeVO vo) {
		mapper.updateNotice(vo);
	}

	@Override
	public List<NoticeVO> noticeList(PageVO vo) {
		return mapper.noticeList(vo);
	}

	@Override
	public int noticeCount() {
		return mapper.noticeCount();
	}

	@Override
	public int statisticsCount(int orderId) {
		return mapper.statisticsCount(orderId);
	}

	@Override
	public void insertNotice(NoticeVO vo) {
		mapper.insertNotice(vo);
	}

	@Override
	public void deleteUser(String email) {
		mapper.deleteUser(email);
	}

	@Override
	public int updateNotice(NoticeVO vo, MultipartFile file) {
		int result = 0;

		// 파일이 비어 있는지 확인
		if (file.isEmpty()) {
			result = -1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
			return result;
		}

		// 파일 크기 제한
		if (file.getSize() > 5 * 1024 * 1024) { // 5MB 제한 (설정 가능)
			result = -1; // 파일 크기는 5MB를 초과할 수 없습니다.
			return result;
		}

		// 파일 확장자 확인
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
			result = -2; // 지원하지 않는 파일 형식입니다. (.jpg, .jpeg, .png만 허용)
			return result;
		}

		String success = util.storeFile(file);

		if (success != null) {
			vo.setNoticeImg("/img/"+success);
			mapper.updateNotice(vo);
			result = 1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
		}
		return result;
	}
}
