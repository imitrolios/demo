package eu.acme.demo.mappers;

import com.google.common.collect.Lists;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setItemId(orderItem.getId());
        orderItemDto.setTotalPrice(orderItem.getTotalPrice());
        orderItemDto.setUnitPrice(orderItem.getUnitPrice());
        orderItemDto.setUnits(orderItem.getUnits());
        return orderItemDto;
    }

    @Override
    public OrderItem toOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCreatedDate(Instant.now());
        orderItem.setTotalPrice(orderItemDto.getTotalPrice());
        orderItem.setUnitPrice(orderItemDto.getUnitPrice());
        orderItem.setUnits(orderItemDto.getUnits());
        return orderItem;
    }

    @Override
    public List<OrderItem> toOrderItem(OrderRequest request) {
        List<OrderItem> orderItems = Lists.newArrayList();
        if (request.getOrderItemDtos() != null && !request.getOrderItemDtos().isEmpty()) {
            orderItems = request.getOrderItemDtos().stream().map(this::toOrderItem).collect(Collectors.toList());
        }
        return orderItems;
    }

}
