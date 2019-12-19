package com.ase.gymjudge.repositories;


import com.ase.gymjudge.entities.Apparatus;
import com.ase.gymjudge.entities.Grouping;
import com.ase.gymjudge.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query(value = "select o.group from Order o where o.roundNumber = ?1 and o.apparatus = ?2")
    Grouping getGroup(int roundNumber, Apparatus apparatus);

    @Query(value = "select o.group from Order o where o.roundNumber = ?1")
    List<Order> getOrdersByRoundNumber (int roundNumber);

}
