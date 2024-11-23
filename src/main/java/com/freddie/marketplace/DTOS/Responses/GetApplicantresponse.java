package com.freddie.marketplace.DTOS.Responses;


import com.freddie.marketplace.data.model.Seller;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GetApplicantresponse {
    private List<Seller> sellerList;
}
