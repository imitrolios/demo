package eu.acme.demo.data;


import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@SpringBootTest
public class OrderDataTests {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testCreateOrder() {
        Order o = createOrderWithTestValues();
        orderRepository.save(o);

        Assert.isTrue(orderRepository.findById(o.getId()).isPresent(), "order not found");
        Assert.isTrue(!orderRepository.findById(UUID.randomUUID()).isPresent(), "non existing order found");
    }

    @Test
    public void testCreateOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setTotalPrice(BigDecimal.valueOf(100.00));
        orderItem.setUnitPrice(BigDecimal.valueOf(20.00));
        orderItem.setUnits(5);
        orderItem.setCreatedDate(Instant.now());

        Order o = createOrderWithTestValues();
        orderRepository.save(o);
        orderItem.setOrder(o);

        orderItemRepository.save(orderItem);

        Assert.isTrue(orderItemRepository.findById(orderItem.getId()).isPresent(), "order not found");
        Assert.isTrue(!orderItemRepository.findById(UUID.randomUUID()).isPresent(), "non existing order found");
        Assert.notNull(orderItemRepository.findById(orderItem.getId()).get().getOrder(), "Orde of orderItem is null");
    }

    private Order createOrderWithTestValues() {
        Order o = new Order();
        o.setStatus(OrderStatus.SUBMITTED);
        o.setClientReferenceCode("ORDER-1");
        o.setDescription("first order");
        o.setItemCount(10);
        o.setItemTotalAmount(BigDecimal.valueOf(100.23));

        return o;
    }

}
