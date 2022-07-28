package com.parceldelivery.parcelorder.service;

import com.parceldelivery.parcelorder.model.dto.request.OrderAssignRequest;
import com.parceldelivery.parcelorder.model.dto.request.OrderRequest;
import com.parceldelivery.parcelorder.model.dto.request.OrderUpdateRequest;
import com.parceldelivery.parcelorder.model.entity.Order;
import com.parceldelivery.parcelorder.model.enums.Role;
import com.parceldelivery.parcelorder.model.enums.Status;
import com.parceldelivery.parcelorder.model.mapper.OrderMapper;
import com.parceldelivery.parcelorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order create(Map<String, String> headers,OrderRequest orderRequest) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.USER.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
        final Order order = OrderMapper.INSTANCE.convertToModel(orderRequest);
        order.setStatus(Status.CREATE);
        order.setUserId(Long.valueOf(userId));
        return orderRepository.save(order);
    }

    public Order updateOrder(Map<String, String> headers, OrderUpdateRequest orderUpdateRequest) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.USER.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
        Order orderMapper = OrderMapper.INSTANCE.convertToUpdateOrderModel(orderUpdateRequest);
        Optional<Order> optionalOrder = orderRepository.findByIdAndUserId(orderMapper.getId(), Long.valueOf(userId));
        if(optionalOrder.isPresent() && Status.CREATE.getCode().equals(optionalOrder.get().getStatus().getCode())){
            Order order = optionalOrder.get();
            copyNonNullProperties(orderMapper,order);
            return orderRepository.save(order);
        }

        return  null;
    }

    public Order cancelOrder(Map<String, String> headers, Long orderId) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.USER.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
        Optional<Order> optionalOrder = orderRepository.findByIdAndUserId(orderId, Long.valueOf(userId));
        if(!optionalOrder.isPresent()){
            throw new Exception("Not foun Order");
        }
        Order order = optionalOrder.get();
        order.setStatus(Status.CANCELED);
        return orderRepository.save(order);
    }

    public List<Order> orderList(Map<String, String> headers) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.USER.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
       return orderRepository.findByUserId(Long.valueOf(userId));
    }

    public Order orderDetail(Map<String, String> headers,Long orderId) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.USER.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
        Optional<Order> optionalOrder = orderRepository.findByIdAndUserId(orderId, Long.valueOf(userId));
        if(!optionalOrder.isPresent()){
            throw new Exception("Not foun Order");
        }
        return optionalOrder.get();
    }

    public Order changeStatusOrder(){
        return new Order();
    }

    public List<Order> allList(Map<String, String> headers) throws Exception {
        String role = headers.get("role");
        if(!Role.ADMIN.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
        return orderRepository.findAll();
    }

    public Order assignOrder(Map<String, String> headers,OrderAssignRequest orderAssignRequest) throws Exception {
        String role = headers.get("role");
        if(!Role.ADMIN.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }
        Optional<Order> orderOptional = orderRepository.findById(orderAssignRequest.getOrderId());
        if(!orderOptional.isPresent()){
            throw new Exception("Order Not Found");
        }
        //TODO will add controller isExist courier
        Order order = orderOptional.get();
        order.setCourierId(orderAssignRequest.getCourierId());
        return orderRepository.save(order);
    }

    public void coordinate(){

    }

    public List<Order> courierListOrder(Map<String, String> headers) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.COURIER.getValue().equals(role)){
            throw new Exception("Yetkiniz yok");
        }
        return orderRepository.findByCourierId(Long.valueOf(userId));
    }

    public Order courierChangeStatusParcel(Map<String, String> headers,Long orderId) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.COURIER.getValue().equals(role)){
            throw new Exception("Yetkiniz yok");
        }
        Order order = orderRepository.findByIdAndCourierId(orderId, Long.parseLong(userId));
        order.setStatus(Status.DELIVERED);
        return order;
    }

    public Order courierDetailOrder(Map<String, String> headers, Long orderId) throws Exception {
        String userId = headers.get("userId");
        String role = headers.get("role");
        if(!Role.COURIER.getValue().equals(role)){
            throw new Exception("Yetkiniz yok");
        }
        return orderRepository.findByIdAndCourierId(orderId, Long.parseLong(userId));
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
