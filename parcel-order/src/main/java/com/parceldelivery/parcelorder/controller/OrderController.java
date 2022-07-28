package com.parceldelivery.parcelorder.controller;

import com.parceldelivery.parcelorder.model.dto.request.OrderAssignRequest;
import com.parceldelivery.parcelorder.model.dto.request.OrderRequest;
import com.parceldelivery.parcelorder.model.dto.request.OrderUpdateRequest;
import com.parceldelivery.parcelorder.model.entity.Order;
import com.parceldelivery.parcelorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/buy")
    public ResponseEntity buy(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
           System.out.println(String.format("Header '%s' = %s", key, value));
        });

        return ResponseEntity.ok("Hello");
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Order> create(@RequestHeader Map<String, String> headers, @RequestBody OrderRequest orderRequest) throws Exception {
        return ResponseEntity.ok(orderService.create(headers, orderRequest));
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Order> update(@RequestHeader Map<String, String> headers, @RequestBody OrderUpdateRequest orderUpdateRequest)throws Exception {
        return ResponseEntity.ok(orderService.updateOrder(headers, orderUpdateRequest));
    }

    @PostMapping(value = "/cancel/{orderId}")
    public ResponseEntity<Order> cancel(@RequestHeader Map<String, String> headers,@PathVariable Long orderId) throws Exception {
        return ResponseEntity.ok(orderService.cancelOrder(headers, orderId));
    }

    @GetMapping(value = "order-list")
    public ResponseEntity<List<Order>> orderList(@RequestHeader Map<String, String> headers) throws Exception {
        return ResponseEntity.ok(orderService.orderList(headers));
    }

    @GetMapping(value = "order-detail")
    public ResponseEntity<Order> detail(@RequestHeader Map<String, String> headers,@RequestParam Long id) throws Exception {
        return ResponseEntity.ok(orderService.orderDetail(headers, id));
    }

    @PostMapping(value = "change-status-order")
    public ResponseEntity changeStatusOrder(){
        return ResponseEntity.ok(orderService.changeStatusOrder());
    }

    @GetMapping(value = "all-list")
    public ResponseEntity<List<Order>> allList(@RequestHeader Map<String, String> headers) throws Exception {
        return ResponseEntity.ok(orderService.allList(headers));
    }

    @PostMapping(value = "assign-order")
    public ResponseEntity<Order> assignOrder(@RequestHeader Map<String, String> headers, @RequestBody OrderAssignRequest orderAssignRequest) throws Exception {
        return ResponseEntity.ok(orderService.assignOrder(headers, orderAssignRequest));
    }

    @GetMapping(value = "coordinate")
    public ResponseEntity<String> coordinate(){
        return ResponseEntity.ok("565756-4657");
    }


    @GetMapping(value = "courier-list-order")
    public ResponseEntity<List<Order>> courierListOrder(@RequestHeader Map<String, String> headers) throws Exception {
        return ResponseEntity.ok(orderService.courierListOrder(headers));
    }

    @PostMapping(value = "courier-change-status-parcel")
    public ResponseEntity<Order> courierChangeStatusParcel(@RequestHeader Map<String, String> headers, @RequestParam Long orderId) throws Exception {
        return ResponseEntity.ok(orderService.courierChangeStatusParcel(headers, orderId));

    }

    @GetMapping(value = "courier-detail-order")
    public ResponseEntity<Order> detailOrder(@RequestHeader Map<String, String> headers, @RequestParam Long orderId) throws Exception {
        return ResponseEntity.ok(orderService.courierDetailOrder(headers, orderId));
    }


}
