package com.parceldelivery.parcelorder.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAssignRequest {

        Long orderId;
        Long courierId;
}
