package com.example.craft;

import com.example.craft.domain.Customer;
import com.example.craft.domain.CustomerType;
import com.example.craft.domain.Order;
import com.example.craft.domain.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void student_customer_gets_discount_and_receipt_contains_total() {
        Customer customer = new Customer("Ada", "ada@example.com", "07123456789", CustomerType.STUDENT);
        Order order = new Order("ORD-1", customer, "STANDARD", "CARD");
        order.addItem(new OrderItem("Book", 1, 1000));

        String receipt = processor.process(order);

        assertTrue(receipt.contains("Order: ORD-1"));
        assertTrue(receipt.contains("Discount: £1.50"));
        assertTrue(receipt.contains("Delivery: £3.99"));
        assertTrue(receipt.contains("Total: £12.49"));
    }

    @Test
    void staff_discount_applied_correctly() {

        Customer kola = new Customer(
            "Kola",
            "kola@gmail.com",
            "07556612341",
            CustomerType.STAFF
        );

        Order order1 = new Order("ORD-2", kola, "NEXT_DAY", "CARD");

        order1.addItem(new OrderItem("Book", 1, 1000));
        order1.addItem(new OrderItem("Pen", 2, 3000));

        String receipt = processor.process(order1);

        assertTrue(receipt.contains("Order: ORD-2"));
        assertTrue(receipt.contains("Discount: £14.00"));
        assertTrue(receipt.contains("Delivery: £7.99"));
        assertTrue(receipt.contains("Total: £63.99"));
    }

    @Test
    void staff_discount_applied_correctly_two() {

        Customer kola = new Customer(
                "Kola",
                "kola@gmail.com",
                "07556612341",
                CustomerType.STAFF
        );

        Order order1 = new Order("ORD-3", kola, "NEXT_DAY", "CARD");

        order1.addItem(new OrderItem("Book", 1, 1000));

        String receipt = processor.process(order1);

        assertTrue(receipt.contains("Order: ORD-3"));
        assertTrue(receipt.contains("Discount: £2.00"));
        assertTrue(receipt.contains("Delivery: £7.99"));
        assertTrue(receipt.contains("Total: £15.99"));
    }
}
