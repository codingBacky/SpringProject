package com.shard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemVO;
import com.shard.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemMapper mapper;

	@Override
	public ItemVO getItem(int itemNum) {
		return mapper.getItem(itemNum);
	}

	String path = "c:/upload";

	private int counter = 1; // 파일 이름을 고유하게 만들기 위한 카운터

	@Override
	public int itemInsert(ItemVO vo, List<MultipartFile> files) {
		int result = 0;

		// 파일이 비어 있는지 확인
		if (files.isEmpty()) {
			result = -1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
			return result;
		}

		// 파일 크기 제한, 파일 확장자 확인
		for (MultipartFile file : files) {
			if (file.getSize() > 5 * 1024 * 1024) { // 5MB 제한 (설정 가능)
				result = -1; // 파일 크기는 5MB를 초과할 수 없습니다.
				return result;
			}

			String fileName = file.getOriginalFilename();
			if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
				result = -2; // 지원하지 않는 파일 형식입니다. (.jpg, .jpeg, .png만 허용)
				return result;
			}
		}

		// 파일 저장 및 vo에 저장
		String mainImg = null;
		List<String> subImgs = new ArrayList<>();

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			String success = storeFile(file);

			if (i == 0) {
				mainImg = success;
			} else {
				subImgs.add(success);
			}
		}

		// vo에 파일 경로 설정
		vo.setMainImg("/img/" + mainImg);
		if (!subImgs.isEmpty()) {
			vo.setSubImg1("/img/" + subImgs.get(0));
			vo.setSubImg2("/img/" + subImgs.get(1));
			vo.setSubImg3("/img/" + subImgs.get(2));
			vo.setSubImg4("/img/" + subImgs.get(3));
		}

		mapper.itemInsert(vo);

		result = 1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
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

	@Override
	public void itemDelete(int itemNum) {
		mapper.itemDelete(itemNum);
	}

	@Override
	public int itemUpdate(ItemVO vo, List<MultipartFile> files) {
		int result = 0;

		// 파일이 비어 있는지 확인
		if (files.isEmpty()) {
			result = -1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
			return result;
		}

		// 파일 크기 제한, 파일 확장자 확인
		for (MultipartFile file : files) {
			if (file.getSize() > 5 * 1024 * 1024) { // 5MB 제한 (설정 가능)
				result = -1; // 파일 크기는 5MB를 초과할 수 없습니다.
				return result;
			}

			String fileName = file.getOriginalFilename();
			if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
				result = -2; // 지원하지 않는 파일 형식입니다. (.jpg, .jpeg, .png만 허용)
				return result;
			}
		}

		// 파일 저장 및 vo에 저장
		String mainImg = null;
		List<String> subImgs = new ArrayList<>();

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			String success = storeFile(file);

			if (i == 0) {
				mainImg = success;
			} else {
				subImgs.add(success);
			}
		}

		// vo에 파일 경로 설정
		vo.setMainImg("/img/" + mainImg);
		if (!subImgs.isEmpty()) {
			vo.setSubImg1("/img/" + subImgs.get(0));
			vo.setSubImg2("/img/" + subImgs.get(1));
			vo.setSubImg3("/img/" + subImgs.get(2));
			vo.setSubImg4("/img/" + subImgs.get(3));
		}

		mapper.itemUpdate(vo);

		result = 1; // 파일 업로드 성공! and 데이터베이스에 저장 성공
		return result;
	}

	@Override
	public String getItemNameByItemNum(int itemNum) {
		return mapper.getItemNameByItemNum(itemNum);
	}

	@Override
	public int wishListSelect(int itemNum, String email) {
	 return	mapper.wishListSelect(itemNum, email);
	}

	@Override
	public void wishListInsert(int itemNum, String email) {
		mapper.wishListInsert(itemNum, email);
	}

	@Override
	public void wishListDelete(int itemNum, String email) {
		mapper.wishListDelete(itemNum, email);
	}

	@Override
	public List<Integer> getWishListItemNum(String email) {
		return mapper.getWishListItemNum(email);
	}
}
