import exception.WrongEmailException;
import model.*;
import service.CustomerService;
import service.DishService;
import service.OrderItemService;
import service.OrderService;
import util.CheckEmailUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static model.Status.*;

public class RestaurantOrderSystem implements Commands {

    private static final CustomerService customerService = new CustomerService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final OrderService orderService = new OrderService();
    private static final DishService dishService = new DishService();
    private static final OrderItemService orderItemService = new OrderItemService();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printMainManu();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_DISH:
                    addDish();
                    break;
                case REMOVE_DISH:
                    removedDish();
                    break;
                case CHANGE_DISH:
                    changDish();
                    break;
                case ADD_CUSTOMER:
                    addCustomer();
                    break;
                case PRINT_CUSTOMERS:
                    printCustomers();
                    break;
                case CREAT_NEW_ORDER:
                    creatNewOrder();
                    break;
                case PRINT_ALL_ORDERS:
                    printAllOrders();
                    break;
                case PRINT_ALL_ORDERS_BY_CUSTOMER:
                    printAllOrderByCustomer();
                    break;
                case ORDER_INFORMATION:
                    printAllOrderInformation();
                    break;
                case CHANGE_ORDER_STATUS:
                    updateOrderStatus();
                    break;
                case PRINT_MENU_BY_CATEGORY:
                    printRestaurantMenuByCategory();
                    break;
                default:
                    System.out.println("Invalid command" + command);
            }
        }
    }

    private static void printRestaurantMenuByCategory() {
        Commands.printManuCategory();
        String command = scanner.nextLine();
        List<Dish> dishesByCategory = dishService.getDishesByCategory(Category.getCategoryByCode(command));
        System.out.println(dishesByCategory);
    }

    private static void updateOrderStatus() {
        List<Order> orders = orderService.getOrders();
        if (!orders.isEmpty()) {
            System.out.println("Please input order id: ");
            System.out.println(orders);
            int orderId = Integer.parseInt(scanner.nextLine());
            Order orderById = orders.get(orderId);
            Status status = orderById.getStatus();
            switch (status) {
                case PENDING:
                    orderById.setStatus(PREPARING);
                    break;
                case PREPARING:
                    orderById.setStatus(READY);
                    break;
                case READY:
                    orderById.setStatus(DELIVERED);
                    break;
                case DELIVERED:
                    System.out.println("Order has been successfully delivered!");
                    break;
            }
            orderService.changeOrderStatus(orderById);
            System.out.println("Status updated to: " + orderById.getStatus());
        } else {
            System.out.println("Orders is empty!");
        }
    }

    private static void printAllOrderInformation() {
        List<Order> orders = orderService.getOrders();
        if (!orders.isEmpty()) {
            System.out.println("Please input order id: ");
            printAllOrders();
            int orderId = Integer.parseInt(scanner.nextLine());
            Order orderById = orderService.getOrderById(orderId);
            OrderItem orderItem = orderItemService.getOrderItem(orderId);
            System.out.println("Order information: " + orderById);
            System.out.println("Item: " + orderItem);
        }
    }

    private static void printAllOrderByCustomer() {
        List<Customer> customers = customerService.getAllCustomers();
        if (!customers.isEmpty()) {
            System.out.println("Please input order id: ");
            System.out.println(customers);
            int id = Integer.parseInt(scanner.nextLine());
            List<Order> ordersByCustomer = orderService.getOrdersByCustomer(customers.get(id));
            if (!ordersByCustomer.isEmpty()) {
                for (Order order : ordersByCustomer) {
                    System.out.println(order);
                }
            } else {
                System.out.println("Orders is empty!");
            }
        } else {
            System.out.println("Customers is empty!");
        }
    }

    private static void printAllOrders() {
        List<Order> orders = orderService.getOrders();
        if (!orders.isEmpty()) {
            System.out.println(orders);
        } else {
            System.out.println("No orders found");
        }
    }

    private static void creatNewOrder() {
        List<Customer> customers = customerService.getAllCustomers();
        List<Dish> allDishes = dishService.getAllDishes();
        if (!customers.isEmpty() && !allDishes.isEmpty()) {
            System.out.println(customers);
            System.out.println("Please enter the customer ID");
            int customerId = Integer.parseInt(scanner.nextLine());
            Order order = new Order();
            order.setCustomer(customerService.getCustomerById(customerId));
            order.setTotalPrice(0);
            boolean addMore = true;
            double totalPrice = 0;
            while (addMore) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(orderService.addOrder(order));
                System.out.println("Please input dish id: ");
                int dishId = Integer.parseInt(scanner.nextLine());
                Dish dish = dishService.getDishById(dishId);
                orderItem.setDish(dish);
                System.out.println("Please input quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                orderItem.setPrice(dish.getPrice());
                orderItemService.addOrderItem(orderItem);
                totalPrice += (quantity * dish.getPrice());
                System.out.println("Do you want to add another dish? y/n");
                if (scanner.nextLine().equalsIgnoreCase("n")) {
                    addMore = false;
                }
            }
            order.setTotalPrice(totalPrice);
            orderService.updateOrder(order);
            System.out.println("Order has been added!");
        } else {
            System.out.println("Customer or Dish not found");
        }
    }

    private static void printCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (!customers.isEmpty()) {
            System.out.println(customers);
        } else {
            System.out.println("There are no customers in the system");
        }
    }

    private static void addCustomer() {
        System.out.println("Please input customer name: ");
        String name = scanner.nextLine();
        System.out.println("Please input customer phone: ");
        String phone = scanner.nextLine();

        String email = "";
        boolean isValidEmail = false;
        do {
            System.out.println("Please input email email: ");
            email = scanner.nextLine();
            try {
                CheckEmailUtil.isValidEmail(email);
                isValidEmail = true;
            } catch (WrongEmailException e) {
                System.out.println(e.getMessage());
            }
        } while (!isValidEmail);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customerService.addCustomer(customer);
    }

    private static Optional<Dish> getDishById() {
        List<Dish> allDishes = dishService.getAllDishes();
        if (allDishes.isEmpty()) {
            return Optional.empty();
        }
        System.out.println(allDishes);
        System.out.println("Please enter id:");
        try {
            int dishId = Integer.parseInt(scanner.nextLine());
            return Optional.ofNullable(dishService.getDishById(dishId));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static void changDish() {
        Optional<Dish> dishById = getDishById();
        dishById.ifPresentOrElse(
                dish -> {
                    dishService.changeDish(dish);
                    System.out.println("Dish has been changed!");
                },
                () -> System.out.println("Invalid dish id!"));
    }

    private static void removedDish() {
        Optional<Dish> dishById = getDishById();
        dishById.ifPresentOrElse(
                dish -> {
                    dishService.deleteDish(dish);
                    System.out.println("Dish has been deleted!");
                },
                () -> System.out.println("Invalid dish id!"));
    }

    private static void addDish() {
        Commands.printManuCategory();
        String category = scanner.nextLine();
        System.out.println("Please input name. ");
        String name = scanner.nextLine();
        System.out.println("Please input price.");
        double price = Double.parseDouble(scanner.nextLine());
        Dish dish = new Dish();
        dish.setName(name);
        dish.setCategory(Category.getCategoryByCode(category));
        dish.setPrice(price);
        dish.setAvailable(true);
        dishService.addDish(dish);
    }

}

