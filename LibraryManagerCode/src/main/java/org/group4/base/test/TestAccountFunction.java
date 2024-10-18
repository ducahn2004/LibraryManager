package org.group4.base.test;

import org.group4.base.users.Account;
import org.group4.base.users.Member;
import org.group4.base.database.AccountDatabase;

public class TestAccountFunction {
    public static void main(String[] args) {
        testMemberLogin();
    }

    public static void testMemberLogin() {
        String id = "22022171";
        String password = "123";

        Account account = AccountDatabase.getAccounts().stream()
                .filter(acc -> acc.getId().equals(id) && acc instanceof Member)
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
}