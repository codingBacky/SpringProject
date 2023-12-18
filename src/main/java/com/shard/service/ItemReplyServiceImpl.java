package com.shard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemReplyVO;
import com.shard.domain.PageVO;
import com.shard.mapper.ItemReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemReplyServiceImpl implements ItemReplyService{
	
	private final ItemReplyMapper itemReplyMapper;
	
	// itemNum에 맞는 reply 가져오기
	@Override
	public List<ItemReplyVO> getReplyList(int itemNum, PageVO vo) {
		return itemReplyMapper.getReplyList(itemNum, vo);
	}

	// 리뷰 생성
	@Override	
	public int insertReply(ItemReplyVO vo) {
		return itemReplyMapper.insertReply(vo);
	}
	
	//리뷰 읽기, replyNum으로 가져와서 읽음
	@Override
	public ItemReplyVO readReply(int itemNum) {
		return itemReplyMapper.readReply(itemNum);
	}
	
	//리뷰 수정
	@Override
	public int updateReply(ItemReplyVO vo) {
		return itemReplyMapper.updateReply(vo);
	}
	
	//리뷰 삭제
	@Override
	public int deleteReply(int replyNum) {
		return itemReplyMapper.deleteReply(replyNum);
	}


	
	/* 페이징 구현 영역 */
	@Override
	public int totalCount(int itemNum) {
		return itemReplyMapper.totalCount(itemNum);
	}


	@Override
	public ItemReplyVO getReply(int replyNum) {
		return itemReplyMapper.getReply(replyNum);
	}

	/* replyNum으로 상품 번호 가져오기 */
	@Override
    public int getItemNumByReplyNum(int replyNum) {
        return itemReplyMapper.getItemNumByReplyNum(replyNum);
    }

	/* 파일(이미지) 업로드 */
	String path = "c:/upload";
	
	private int counter = 1; // 파일 이름을 고유하게 만들기 위한 카운터
	@Override
	public int insertReply(ItemReplyVO vo, MultipartFile file) {
		int result = 0;

		// 파일이 비어 있는지 확인
		if (file.isEmpty()) {
			itemReplyMapper.insertReply(vo);
			result = 1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
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

		String success = storeFile(file);

		if (success != null) {
			vo.setImg1("/img/"+success);
			itemReplyMapper.insertReply(vo);
			result = 1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
		}

		return result;
	}
	
	public String storeFile(MultipartFile file) {
		int result = 0;
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uniqueFileName = generateUniqueFileName(originalFileName);

		try {
			Path filePath = Paths.get(path, uniqueFileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			return uniqueFileName;
		} catch (IOException e) {
			result = -3; // 파일 업로드 중 오류가 발생했습니다.
			throw new RuntimeException("파일 저장 실패", e);
		}
	}

	private String generateUniqueFileName(String originalFileName) {
		String extension = extractFileExtension(originalFileName);
		String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());

		// 카운터를 사용하여 파일 이름을 고유하게 만듭니다.
		String uniqueFileName = timeStamp + "-" + counter + "." + extension;

		// 다음 파일을 위해 카운터를 증가시킵니다.
		counter++;

		return uniqueFileName;
	}

	private String extractFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		return lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
	}

}
