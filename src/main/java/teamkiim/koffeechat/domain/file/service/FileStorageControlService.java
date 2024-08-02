package teamkiim.koffeechat.domain.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import teamkiim.koffeechat.domain.file.domain.File;
import teamkiim.koffeechat.domain.file.dto.response.ProfileImageInfoResponse;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;
import teamkiim.koffeechat.domain.member.domain.Member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * 물리적 파일 I/O 담당
 */
@Component
public class FileStorageControlService {

    @Value("${file-path}")
    private String filePath;

    private static final String profileImagePath = "PROFILE";

    /**
     * 파일 단건 저장
     * @param file 파일 도메인
     * @param multipartFile 실제 파일
     */
    public void saveFile(File file, MultipartFile multipartFile){

        StringBuilder filePathBuilder = new StringBuilder(filePath)
                .append(file.getPath())
                .append(java.io.File.separator)
                .append(file.getName());

        Path savePath = Paths.get(filePathBuilder.toString());

        System.out.println("파일 경로 : " + savePath.toString());

        try {
            multipartFile.transferTo(savePath);
        } catch (IOException e){
            throw new CustomException(ErrorCode.FILE_IO_FAILED);
        } catch (IllegalStateException e){
            throw new CustomException(ErrorCode.FILE_REQUEST_ERROR);
        }
    }

    /**
     * 회원 프로필 이미지 단건 저장
     * @param multipartFile 실제 파일
     * @param member 회원
     */
    public ProfileImageInfoResponse saveFile(Member member, MultipartFile multipartFile){

        StringBuilder fileNameBuilder = new StringBuilder().append(UUID.randomUUID()).append("_").append(multipartFile.getOriginalFilename());

        StringBuilder filePathBuilder = new StringBuilder(filePath)
                .append(profileImagePath)
                .append(java.io.File.separator)
                .append(fileNameBuilder);

        Path savePath = Paths.get(filePathBuilder.toString());

        if(member.getProfileImageName() != null && !member.getProfileImageName().equals("basic_profile_image.png")){
            StringBuilder deleteFilePathBuilder = new StringBuilder(filePath)
                    .append(profileImagePath)
                    .append(java.io.File.separator)
                    .append(member.getProfileImageName());

            Path deletePath = Paths.get(deleteFilePathBuilder.toString());

            try {
                Files.delete(deletePath);

            } catch (IOException e){
                throw new CustomException(ErrorCode.FILE_IO_FAILED);
            } catch (IllegalStateException e){
                throw new CustomException(ErrorCode.FILE_REQUEST_ERROR);
            }
        }

        try {
            multipartFile.transferTo(savePath);
        } catch (IOException e){
            throw new CustomException(ErrorCode.FILE_IO_FAILED);
        } catch (IllegalStateException e){
            throw new CustomException(ErrorCode.FILE_REQUEST_ERROR);
        }

        return ProfileImageInfoResponse.of(profileImagePath, fileNameBuilder.toString());
    }

    /**
     * 파일 단건 삭제
     * @param file 파일 도메인
     */
    public void deleteFile(File file){

        StringBuilder filePathBuilder = new StringBuilder(filePath)
                .append(file.getPath())
                .append(java.io.File.separator)
                .append(file.getName());

        Path savePath = Paths.get(filePathBuilder.toString());

        java.io.File deleteFile = new java.io.File(savePath.toString());

        if(!deleteFile.exists()){
            throw new CustomException(ErrorCode.FILE_IO_FAILED);
        }

        deleteFile.delete();
    }

    public void deleteFiles(List<File> fileList){

        for(File file : fileList){

            StringBuilder filePathBuilder = new StringBuilder(filePath)
                    .append(file.getPath())
                    .append(java.io.File.separator)
                    .append(file.getName());

            Path savePath = Paths.get(filePathBuilder.toString());

            java.io.File deleteFile = new java.io.File(savePath.toString());

            if(!deleteFile.exists()){
                throw new CustomException(ErrorCode.FILE_IO_FAILED);
            }

            deleteFile.delete();
        }
    }
}
