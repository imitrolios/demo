package eu.acme.demo.mappers;

import eu.acme.demo.domain.Order;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderLiteDto toOrderLiteDto(Order order) {
        OrderLiteDto orderLiteDto = new OrderLiteDto();
        orderLiteDto.setClientReferenceCode(order.getClientReferenceCode());
        orderLiteDto.setDescription(order.getDescription());
        orderLiteDto.setItemCount(order.getItemCount());
        orderLiteDto.setTotalAmount(order.getItemTotalAmount());
        orderLiteDto.setId(order.getId());
        orderLiteDto.setStatus(order.getStatus());

        return orderLiteDto;
    }

    @Override
    public OrderDto toOrderDto(Order order, List<OrderItemDto> orderItemDtos) {
        OrderDto orderDto = new OrderDto();
        orderDto.setClientReferenceCode(order.getClientReferenceCode());
        orderDto.setDescription(order.getDescription());
        orderDto.setItemCount(order.getItemCount());
        orderDto.setTotalAmount(order.getItemTotalAmount());
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderItems(orderItemDtos);
        return orderDto;
    }

    @Override
    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setClientReferenceCode(orderRequest.getClientReferenceCode());
        order.setDescription(orderRequest.getDescription());
        order.setItemCount(orderRequest.getItemCount());
        order.setItemTotalAmount(orderRequest.getItemTotalAmount());
        order.setStatus(orderRequest.getStatus());
        return order;
    }
}
