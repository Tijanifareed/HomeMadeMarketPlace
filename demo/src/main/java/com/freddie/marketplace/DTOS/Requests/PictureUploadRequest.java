package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class PictureUploadRequest {
    private Long id;
    private MultipartFile file;

}
