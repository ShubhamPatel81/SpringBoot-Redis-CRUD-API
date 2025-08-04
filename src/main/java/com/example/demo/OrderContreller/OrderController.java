package com.example.demo.OrderContreller;

import com.example.demo.OrderService.OrderService;
import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService service;

    // Retrieve all orders
    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Order>> getAllOrders(){
            List<Order> orders = service.getOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    // Retrieve an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) throws Exception {
        Order order = service.getOrder(id);
        return new ResponseEntity<>(order,  HttpStatus.OK);
    }

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        return new ResponseEntity<>(service.createOrder(order),HttpStatus.CREATED);
    }
    // Update an existing order
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id,@RequestBody Order updatedOrder) throws Exception {
        Order order = service.updateOrder(id,updatedOrder);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    //Delete an Order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id)throws  Exception{
        service.deleteOrder(id);// Deletes the order from the repository
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Returns a 204 No Content response
    }

}
