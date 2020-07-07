package eu.acme.demo.repository;

import eu.acme.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.clientReferenceCode = ?1")
    List<Order> findOrdersByClientReferenceCode(String clientReferenceCode);
}
