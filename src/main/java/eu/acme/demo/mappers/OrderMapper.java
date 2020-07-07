package eu.acme.demo.mappers;

import eu.acme.demo.domain.Order;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;

import java.util.List;

public interface OrderMapper {

    OrderLiteDto toOrderLiteDto(Order order);

    OrderDto toOrderDto(Order order, List<OrderItemDto> orderItemDtos);

    Order toOrder(OrderRequest orderRequest);
}
