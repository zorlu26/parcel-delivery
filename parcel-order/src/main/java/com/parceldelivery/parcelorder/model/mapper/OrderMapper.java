package com.parceldelivery.parcelorder.model.mapper;

import com.parceldelivery.parcelorder.model.dto.request.OrderRequest;
import com.parceldelivery.parcelorder.model.dto.request.OrderUpdateRequest;
import com.parceldelivery.parcelorder.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order convertToModel(OrderRequest orderRequest);

    Order convertToUpdateOrderModel(OrderUpdateRequest orderUpdateRequest);
}
