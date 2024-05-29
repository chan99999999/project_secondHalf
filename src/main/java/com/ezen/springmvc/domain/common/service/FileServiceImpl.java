package com.ezen.springmvc.domain.common.service;

import com.ezen.springmvc.domain.common.dto.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

<<<<<<< HEAD
    private boolean existUploadDirectory(String directoryName) {
        return new File(directoryName).exists();
    }

=======
    private boolean existUploadDirectory(String directoryName){
        return new File(directoryName).exists();
    }
>>>>>>> e14cf53033f08ba3cd96bed4644341f42f9a9fb1
    private void makeUploadDirectory(String directoryName) {
        new File(directoryName).mkdirs();
    }

<<<<<<< HEAD
    /**
     * 단일 업로드 파일 저장
     */
    public UploadFile storeFile(MultipartFile multipartFile, String storePath) {
        if (!existUploadDirectory(storePath)) {
=======
    /** 단일 업로드 파일 저장 */
    public UploadFile storeFile(MultipartFile multipartFile, String storePath) {
        if(!existUploadDirectory(storePath)){
>>>>>>> e14cf53033f08ba3cd96bed4644341f42f9a9fb1
            makeUploadDirectory(storePath);
        }

        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        try {
            multipartFile.transferTo(new File(storePath + storeFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadFile(uploadFileName, storeFileName);
    }

<<<<<<< HEAD
    /**
     * 다중 업로드 파일 저장
     */
=======
    /** 다중 업로드 파일 저장 */
>>>>>>> e14cf53033f08ba3cd96bed4644341f42f9a9fb1
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles, String storePath) {
        List<UploadFile> storeFileResult = new ArrayList<UploadFile>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                // 업로드 파일 저장
                UploadFile uploadFile = null;
                uploadFile = storeFile(multipartFile, storePath);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    @Override
    public List<String> getStoreFiles(String storePath) {
        // DB에 저장된 파일 목록 반환해야 하지만 편의상 실제 저장된 파일명 반환
        List<String> list = new ArrayList<>();
        File directory = new File(storePath);
        File[] files = directory.listFiles();
        Arrays.asList(files).forEach(file -> {
            list.add(file.getName());
        });
        return list;
    }

    // 업로드된 파일이 중복되지 않게 저장될 파일명 생성
	private String createStoreFileName(String uploadFileName) {
        int position = uploadFileName.lastIndexOf(".");
        String prefix =uploadFileName.substring(0, position);
        String suffix = uploadFileName.substring(position + 1);
		String uuid = UUID.randomUUID().toString();
		return prefix + "-" + uuid + "." + suffix;
	}
<<<<<<< HEAD




}

=======
}
>>>>>>> e14cf53033f08ba3cd96bed4644341f42f9a9fb1
