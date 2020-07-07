package eu.acme.demo.web;

import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.exceptions.ValidationException;
import eu.acme.demo.mappers.OrderItemMapper;
import eu.acme.demo.mappers.OrderMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@Api(
        tags = {"/orders"}
)
public class OrderAPI {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    public OrderAPI(OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderMapper orderMapper,
                    OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @GetMapping
    @ApiOperation(value = "Web call to get list of orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderLiteDto> fetchOrders() {

        return orderRepository.findAll().stream().map(orderMapper::toOrderLiteDto).collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Web call to get order by orderId")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto fetchOrder(@PathVariable UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ValidationException("order id does not exist"));
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByOrderId(orderId);
        return orderMapper.toOrderDto(order,
                orderItems.stream().map(orderItemMapper::toOrderItemDto).collect(Collectors.toList())
        );
    }

    @PostMapping
    @ApiOperation(value = "Web call to submit an order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto submitOrder(@RequestBody OrderRequest orderRequest) {
        //TODO: submit a new order
        // if client reference code already exist then return an HTTP 400 (bad request) with a proper payload that contains an error code and an error message
        // just a comment on the above requirement. Normally I would deduce that client reference code must have a unique constraint,
        // I would add one in db and when I would try to insert a new object with same data an exception would be thrown (DataIntegrityViolationException),
        // catching and throwing a new application exception and again
        // catching the latter exception in ExceptionHandlerControllerAdvice I would return 400 http. This way I don't "dive" in db twice
        // reducing resource and time costing operations. Instead since it is not clear that client reference code is uniqueI will proceed with a 2 step procedure

        Order order = orderMapper.toOrder(orderRequest);
        List<OrderItem> orderItems = orderItemMapper.toOrderItem(orderRequest);

        if (!orderRepository.findOrdersByClientReferenceCode(orderRequest.getClientReferenceCode()).isEmpty()) {
            throw new ValidationException("client reference code already exists");
        }

        order = orderRepository.saveAndFlush(order);
        for (OrderItem oi : orderItems) {
            oi.setOrder(order);
        }
        ;
        orderItems = orderItemRepository.saveAll(orderItems);
        return orderMapper.toOrderDto(order, orderItems.stream().map(orderItemMapper::toOrderItemDto).collect(Collectors.toList()));
    }

}
