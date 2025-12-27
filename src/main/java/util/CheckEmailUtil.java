package util;

import exception.WrongEmailException;

public abstract class CheckEmailUtil {

    public static String isValidEmail(String email) throws WrongEmailException {
        if(checkEmail(email)) {
            return email;
        }
        throw new WrongEmailException("Wrong email, please input try again !!!");
    }

    private static boolean checkEmail(String email) {
        if (email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

}
