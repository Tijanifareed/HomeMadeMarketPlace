package com.freddie.marketplace.DTOS.Requests;

import com.freddie.marketplace.data.model.CategoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class AddProductRequest {
    private String productName;
    private String description;
    private Double price;
    private Long seller_id;
    private CategoryType productType;
    private List<String> images;
    private int stock;
}
