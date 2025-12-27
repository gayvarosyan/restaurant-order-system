package service;

import db.DBConnectionProvider;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer(name,phone,email) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                customer.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setPhone(resultSet.getString("phone"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return customers;
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("Id"));
                customer.setName(resultSet.getString("name"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
                return customer;
            }
        } catch (SQLException e) {
            System.err.println("Error while getting Customer" + e.getMessage());
        }
        return null;
    }
}

