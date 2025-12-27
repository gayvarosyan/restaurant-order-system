package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private int id;
    private Order order;
    private Dish dish;
    private int quantity;
    private double price;
}
