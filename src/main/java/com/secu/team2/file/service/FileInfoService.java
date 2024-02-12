package com.secu.team2.file.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.secu.team2.common.firebase.FirebaseService;
import com.secu.team2.file.mapper.FileInfoMapper;
import com.secu.team2.file.vo.FileInfoVO;
import com.secu.team2.type.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class FileInfoService {

	private final FileInfoMapper fileInfoMapper;
	private final FirebaseService firebaseService;

	@Value("${upload.file-path}")
	private String uploadFilePath;

	public int insertFileInfo(FileInfoVO file) {
		return fileInfoMapper.insertFileInfo(file);
	}

	public List<FileInfoVO> selectFileInfos(int biNum){
		return fileInfoMapper.selectFileInfos(biNum);
	}

	public int insertFileInfos(int biNum, List<FileInfoVO> files) {
	    int result = 0;
	    for (FileInfoVO myFile : files) {
	        if (myFile.getFile() != null) {
	            String originName = myFile.getFile().getOriginalFilename();
	            try {
	                String filePath = firebaseService.uploadFile(myFile.getFile(), originName);
	                myFile.setFiName(originName);
	                myFile.setFiPath(filePath);
	            } catch (Exception e) {
	                log.error("File upload error=>{}", e);
	                myFile.setFiName(originName);
	                myFile.setFiPath("/upload-failed/" + originName);
	            }
	        } else if (myFile.getStatus() != Status.DELETE) {
	            // 파일이 업로드되지 않았고, 삭제 상태가 아닌 경우에 대한 처리
	            myFile.setFiName("No File");
	            myFile.setFiPath("No Path");
	        }
	        myFile.setBiNum(biNum);

	        if (myFile.getStatus() == Status.UPDATE) {
	            result += fileInfoMapper.updateFileInfos(myFile);
	        } else {
	            result += fileInfoMapper.insertFileInfo(myFile);
	        }
	    }
	    return result;
	}

	public int updateFileInfos(int biNum, List<FileInfoVO> files) {
	    int result = 0;
	    log.info("files=>{}", files);
	    for (FileInfoVO myFile : files) {
	        if (myFile.getStatus() == Status.DELETE) {
	            try {
	                firebaseService.deleteFile(myFile.getFiPath());
	            } catch (Exception e) {
	                log.error("Error deleting file from Firebase Storage: {}", e.getMessage(), e);
	            }
	            result += fileInfoMapper.deleteFileInfo(myFile.getFiNum());
	            continue;
	        }

	        MultipartFile file = myFile.getFile();
	        if (file != null) {
	            String originName = file.getOriginalFilename();
	            try {
	                String filePath = firebaseService.uploadFile(file, originName);
	                myFile.setFiName(originName);
	                myFile.setFiPath(filePath);
	            } catch (Exception e) {
	                log.error("File upload error=>{}", e);
	                myFile.setFiName(originName);
	                myFile.setFiPath("/upload-failed/" + originName);
	            }
	        }

	        myFile.setBiNum(biNum);

	        if (myFile.getStatus() == Status.UPDATE) {
	            result += fileInfoMapper.updateFileInfos(myFile);
	        } else {
	            result += fileInfoMapper.insertFileInfo(myFile);
	        }
	    }
	    return result;
	}
	public int deleteFileInfos(int biNum, List<FileInfoVO> files) {
		int result = 0;
		for(FileInfoVO myFile : files) {
			String fileName = myFile.getFiPath();
	        int idx = fileName.lastIndexOf("/") + 1;
	        fileName = fileName.substring(idx);
	        File f = new File(uploadFilePath + fileName);
	        if(f.exists()) {
	        	f.delete();
	        	result += fileInfoMapper.deleteFileInfo(myFile.getFiNum());
	        }
		}
		return result;
	}

}