package eu.acme.demo.web;

import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Api(
        tags = {"/orders"}
)
public class OrderAPI {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderAPI(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping
    @ApiOperation(value = "Web call to get list of orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderLiteDto> fetchOrders() {
        //TODO: fetch all orders in DB
        return null;
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Web call to get order by orderId")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto fetchOrder(@PathVariable UUID orderId) {
        //TODO: fetch specific order from DB
        // if order id not exists then return an HTTP 400 (bad request) with a proper payload that contains an error code and an error message
        return null;
    }

    @PostMapping
    @ApiOperation(value = "Web call to submit an order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto submitOrder(@RequestBody OrderRequest orderRequest) {
        //TODO: submit a new order
        // if client reference code already exist then return an HTTP 400 (bad request) with a proper payload that contains an error code and an error message
        return null;
    }

}
