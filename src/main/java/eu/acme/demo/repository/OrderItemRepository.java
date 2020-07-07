package eu.acme.demo.repository;

import eu.acme.demo.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    @Query("SELECT oi FROM OrderItem oi "
            + "WHERE oi.order.id = ?1")
    List<OrderItem> getOrderItemsByOrderId(UUID orderId);

}
