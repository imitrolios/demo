package eu.acme.demo.mappers;

import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderRequest;

import java.util.List;

public interface OrderItemMapper {

    OrderItemDto toOrderItemDto(OrderItem orderItem);

    List<OrderItem> toOrderItem(OrderRequest request);

    OrderItem toOrderItem(OrderItemDto orderItemDto);
}
