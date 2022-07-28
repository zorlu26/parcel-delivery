package com.parceldelivery.parcelorder;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.parcelorder.controller.OrderController;
import com.parceldelivery.parcelorder.model.dto.request.OrderRequest;
import com.parceldelivery.parcelorder.model.entity.Order;
import com.parceldelivery.parcelorder.model.enums.Role;
import com.parceldelivery.parcelorder.model.enums.Status;
import com.parceldelivery.parcelorder.model.mapper.OrderMapper;
import com.parceldelivery.parcelorder.repository.OrderRepository;
import com.parceldelivery.parcelorder.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTests {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void testListOrder() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("role", Role.USER.getValue());
        headers.put("userId","1");
        when(orderRepository.findByUserId(1L)).thenReturn(Arrays.asList(new Order()));
        orderService.orderList(headers);
    }
}
