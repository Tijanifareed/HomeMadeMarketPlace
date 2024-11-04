package com.freddie.marketplace.DTOS.Requests;

import com.freddie.marketplace.data.model.CategoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
@ToString
public class AddProductRequest {
    private List<MultipartFile>  file;
    private String productName;
    private String description;
    private Double price;
    private Long seller_id;
    private CategoryType productType;
    private int stock;
}
