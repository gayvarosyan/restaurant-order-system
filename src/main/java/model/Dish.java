package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private int id;
    private String name;
    private Category category;
    private double price;
    private boolean available;

}
