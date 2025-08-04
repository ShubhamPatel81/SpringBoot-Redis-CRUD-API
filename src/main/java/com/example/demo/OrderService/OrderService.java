package com.example.demo.OrderService;

import com.example.demo.OrderRepository.OrderRepository;
import com.example.demo.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

        @Autowired
    private OrderRepository repository;

    // Retrieve an order by ID and cache the result
    @Cacheable(value = "order",key = "#id")
    public Order getOrder(@PathVariable String id ) throws Exception {
        System.out.println("Fetching Order From MongoDb of id : "+id);
        return repository.findById(id).orElseThrow(()->new Exception("Order is Not found by the given Id "+id));
    }

    // Retrieve all orders and cache the result
    @Cacheable(value = "orders")
    public List<Order> getOrders(){
        System.out.println("Fetching all the Orders");
        return new ArrayList<>(repository.findAll());
    }
    // Create a new order and evict the cache for orders
    @CacheEvict(cacheNames = "orders", allEntries = true)// Clears all cached orders
    public String createOrder(Order order) {
        Order newOrder = new Order();
        newOrder.setOrderId(order.getOrderId());
        newOrder.setDate(order.getDate());
        Order saveOrder = repository.save(newOrder);
        return saveOrder.getId();
    }
    // Update an existing order and manage cache eviction
    @CacheEvict(cacheNames = {"order", "orders"},
            allEntries = true, key = "#id") // Evicts the specific order and clears all orders from the cache
    public Order updateOrder(String id, Order updatedOrder)throws  Exception{
        Order existingOrder = repository.findById(id).orElseThrow(()->new Exception("Order Not Found "));
        existingOrder.setDate(updatedOrder.getDate());
        existingOrder.setOrderId(updatedOrder.getOrderId());
        return repository.save(existingOrder);
    }

    // Delete an order and manage cache eviction
    @CacheEvict(cacheNames = {"order", "orders"},
            allEntries = true, key = "#id") // Evicts the specific order and clears all orders from the cache
    public void deleteOrder(String id) throws Exception {
        if (!repository.existsById(id)){
            throw new Exception("Order Doesn't Exist by given Id");
        }
        repository.deleteById(id);
    }
}
