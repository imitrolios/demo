package eu.acme.demo.mappers;

import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.web.dto.OrderItemDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrderItemMapperTests {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void when_orderItem_mapper_should_return_orderItemDto() {

        OrderItem orderItem = new OrderItem();
        orderItem.setTotalPrice(BigDecimal.valueOf(100.00));
        orderItem.setUnitPrice(BigDecimal.valueOf(20.00));
        orderItem.setUnits(5);
        orderItem.setCreatedDate(Instant.now());


        Order order = new Order();

        order.setClientReferenceCode("testCode");
        order.setStatus(OrderStatus.UNDER_PROCESS);
        order.setDescription("testDescription");

        orderItem.setOrder(order);

        OrderItemDto orderItemDto = orderItemMapper.toOrderItemDto(orderItem);

        assertThat(orderItemDto).isNotNull();
        assertThat(orderItemDto.getTotalPrice()).isNotNull();
        assertThat(orderItemDto.getUnits()).isEqualTo(5);
    }

}
