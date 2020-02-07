package xyz.nhblog.paydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nhblog.paydemo.dataobject.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
