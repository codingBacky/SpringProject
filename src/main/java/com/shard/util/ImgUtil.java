package com.shard.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImgUtil {
	
	private static ImgUtil instence = new ImgUtil();
	
	private ImgUtil() {
	}
	
	public static ImgUtil getInstence() {
		return instence;
	}
	
	private String PATH = "c:/upload";
	
	private int counter = 1; // 파일 이름을 고유하게 만들기 위한 카운터	
	
	public String storeFile(MultipartFile file) {
		int result = 0;
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uniqueFileName = generateUniqueFileName(originalFileName);

		try {
			Path filePath = Paths.get(PATH, uniqueFileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			return uniqueFileName;
		} catch (IOException e) {
			result = -3; // 파일 업로드 중 오류가 발생했습니다.
			throw new RuntimeException("파일 저장 실패", e);
		}
	}

	private  String generateUniqueFileName(String originalFileName) {
		String extension = extractFileExtension(originalFileName);
		String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());

		// 카운터를 사용하여 파일 이름을 고유하게 만듭니다.
		String uniqueFileName = timeStamp + "-" + counter + "." + extension;

		// 다음 파일을 위해 카운터를 증가시킵니다.
		counter++;

		return uniqueFileName;
	}

	private static  String extractFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		return lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
	}
}
