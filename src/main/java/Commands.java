import static model.Category.*;

public interface Commands {
    String EXIT = "0";
    String ADD_DISH = "1";
    String REMOVE_DISH = "2";
    String CHANGE_DISH = "3";
    String ADD_CUSTOMER = "4";
    String PRINT_CUSTOMERS = "5";
    String CREAT_NEW_ORDER = "6";
    String PRINT_ALL_ORDERS = "7";
    String PRINT_ALL_ORDERS_BY_CUSTOMER = "8";
    String ORDER_INFORMATION = "9";
    String CHANGE_ORDER_STATUS = "10";
    String PRINT_MENU_BY_CATEGORY = "11";

    static void printMainManu() {
        System.out.println("Please input " + EXIT + " for exit.");
        System.out.println("Please input " + ADD_DISH + " for add dish.");
        System.out.println("Please input " + REMOVE_DISH + " for remove dish.");
        System.out.println("Please input " + CHANGE_DISH + " for change dish.");
        System.out.println("Please input " + ADD_CUSTOMER + " for add customer.");
        System.out.println("Please input " + PRINT_CUSTOMERS + " for print customer.");
        System.out.println("Please input " + CREAT_NEW_ORDER + " for create new order.");
        System.out.println("Please input " + PRINT_ALL_ORDERS + " for print all orders.");
        System.out.println("Please input " + PRINT_ALL_ORDERS_BY_CUSTOMER + " for print all orders by customer.");
        System.out.println("Please input " + ORDER_INFORMATION + " for order information.");
        System.out.println("Please input " + CHANGE_ORDER_STATUS + " for change order status.");
        System.out.println("Please input " + PRINT_MENU_BY_CATEGORY + " for print menu by category.");
    }
    static void printManuCategory() {
        System.out.println("Please select profession ");
        System.out.println("Please input " + APPETIZER.getCode() + " for " + APPETIZER);
        System.out.println("Please input " + MAIN.getCode() + " for " + MAIN);
        System.out.println("Please input " + DESSERT.getCode() + " for " + DESSERT);
        System.out.println("Please input " + DRINK.getCode() + " for " + DRINK);
    }
}
