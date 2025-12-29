package service;

import db.DBConnectionProvider;
import model.Category;
import model.Dish;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishService {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void addDish(Dish dish) {
        String query = "INSERT INTO dish (name, category, price, available) VALUES (?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getCategory().name());
            preparedStatement.setDouble(3, dish.getPrice());
            preparedStatement.setBoolean(4, dish.isAvailable());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                dish.setId(resultSet.getInt(1));
            }
            System.out.println("Dish was added");
        } catch (SQLException e) {
            System.err.println("Error while inserting Dish" + e.getMessage());
        }
    }

    public void deleteDish(Dish dish) {
        String query = "DELETE FROM dish WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, dish.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting Dish" + e.getMessage());
        }
    }

    public void changeDish(Dish dish) {
        String query = "UPDATE dish SET name=?, category=?,price=?, available=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getCategory().name());
            preparedStatement.setDouble(3, dish.getPrice());
            preparedStatement.setBoolean(4, dish.isAvailable());
            preparedStatement.setInt(5, dish.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while changing Dish" + e.getMessage());
        }
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishList = new ArrayList<>();
        String query = "SELECT * FROM dish";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setCategory(Category.valueOf(resultSet.getString("category")));
                dish.setPrice(resultSet.getDouble("price"));
                dish.setAvailable(resultSet.getBoolean("available"));
                dishList.add(dish);
            }
            return dishList;
        }catch (SQLException e){
            System.err.println("Error while getting Dishes" + e.getMessage());
        }
        return dishList;
    }

    public List<Dish> getDishesByCategory(Category category) {
        List<Dish> dishList = new ArrayList<>();
        String query = "SELECT * FROM dish WHERE category = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setCategory(category);
                dish.setPrice(resultSet.getDouble("price"));
                dish.setAvailable(resultSet.getBoolean("available"));
                dishList.add(dish);
            }
        }catch (SQLException e) {
            System.out.printf("Error while getting Dish By Category: %s\n", e.getMessage());
        }
        return dishList;
    }

    public Dish getDishById(int id) {
        String query = "SELECT * FROM dish WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setCategory(Category.valueOf(resultSet.getString("category")));
                dish.setPrice(resultSet.getDouble("price"));
                dish.setAvailable(resultSet.getBoolean("available"));
                return dish;
            }
        } catch (SQLException e) {
            System.err.println("Error while getting Dish" + e.getMessage());
        }
        return null;
    }

}
