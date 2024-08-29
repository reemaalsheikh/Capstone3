package com.example.capstone3.Repository;

import com.example.capstone3.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findOrdersById(Integer id);

    long countByUsersId(Integer usersId);

}
