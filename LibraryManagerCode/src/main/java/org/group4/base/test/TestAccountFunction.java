package org.group4.base.test;

import org.group4.base.entities.Person;
import org.group4.base.users.Account;
import org.group4.base.database.AccountDatabase;

public class TestAccountFunction {

    public static void main(String[] args) {
        testRegister1();
        testRegister2();
        testLogin();
        testChangePassword();
        testLogin();
    }

    public static void testLogin() {
        String id = "22022171";
        String password = "123";

        Account account = AccountDatabase.getInstance().getItems().stream()
            .filter(acc -> acc.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (account != null) {
            boolean loginSuccess = account.login(id, password);
            if (loginSuccess) {
                System.out.println("Login successful for member ID: " + id);
            } else {
                System.out.println("Login failed for member ID: " + id);
            }
        } else {
            System.out.println("Member not found with ID: " + id);
        }
    }

    public static void testRegister1() {
        String id = "22022189";
        String password = "123";
        String rePassword = "123";
        Person person = new Person("Tran Dog Ahn", "22022189@vnu.edu.vn");

        boolean registerSuccess = Account.register(id, password, rePassword, person);

        if (registerSuccess) {
            System.out.println("Registration successful for member ID: " + id);
        } else {
            System.out.println("Registration failed for member ID: " + id);
        }
    }

    public static void testRegister2() {
        String id = "22022168";
        String password = "234";
        String rePassword = "234";
        Person person = new Person("Nguyen Tuan Anh", "22022168@vnu.edu.vn");

        boolean registerSuccess = Account.register(id, password, rePassword, person);

        if (registerSuccess) {
            System.out.println("Registration successful for member ID: " + id);
        } else {
            System.out.println("Registration failed for member ID: " + id);
        }
    }

    public static void testChangePassword() {
        String id = "22022171";
        String oldPassword = "123";
        String newPassword = "234";
        String reNewPassword = "234";

        Account account = AccountDatabase.getInstance().getItems().stream()
            .filter(acc -> acc.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (account != null) {
            boolean changePasswordSuccess = account.changePassword(oldPassword, newPassword, reNewPassword);
            if (changePasswordSuccess) {
                System.out.println("Change password successful for member ID: " + id);
            } else {
                System.out.println("Change password failed for member ID: " + id);
            }
        } else {
            System.out.println("Member not found with ID: " + id);
        }
    }



}
