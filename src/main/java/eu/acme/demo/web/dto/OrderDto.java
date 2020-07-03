package eu.acme.demo.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto extends OrderLiteDto {

    private List<OrderItemDto> orderItems;
}
