package service;

import db.DBConnectionProvider;
import model.OrderItem;

import java.sql.*;

public class OrderItemService {

    private final Connection connection = DBConnectionProvider.getInstance().getConnection();
    private final DishService dishService = new DishService();
    private final OrderService orderService = new OrderService();

    public void addOrderItem(OrderItem orderItem) {
        String query = "INSERT INTO order_items (order_id, dish_id,quantity,price) VALUES (?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, orderItem.getOrder().getId());
            preparedStatement.setInt(2, orderItem.getDish().getId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setDouble(4, orderItem.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                orderItem.setId(resultSet.getInt(1));
            }
        }catch (SQLException e) {
            System.out.println("Error while inserting OrderItem" + e.getMessage());
        }
    }
    public OrderItem getOrderItem(int orderId) {
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(resultSet.getInt(1));
                orderItem.setOrder(orderService.getOrderById(resultSet.getInt(2)));
                orderItem.setDish(dishService.getDishById(resultSet.getInt(3)));
                orderItem.setQuantity(resultSet.getInt(4));
                orderItem.setPrice(resultSet.getDouble(5));
                return orderItem;
            }
        }catch (SQLException e) {
            System.out.println("Error while getting OrderItem" + e.getMessage());
        }
        return null;
    }
}
