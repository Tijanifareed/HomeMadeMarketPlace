package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PictureUploadRequest {
    private MultipartFile file;

}
