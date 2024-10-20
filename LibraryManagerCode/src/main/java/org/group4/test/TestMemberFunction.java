package org.group4.test;

import org.group4.base.entities.Address;
import org.group4.base.users.Member;
import org.group4.base.entities.Person;
import org.group4.base.books.BookItem;
import org.group4.database.BookItemDatabase;
import org.jetbrains.annotations.NotNull;

public class TestMemberFunction {
    public static void main(String[] args) {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person person1 = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn", address);
        Member member1 = new Member("22022171", "123", person1);

        System.out.println("Test Get Date Of Membership");
        testGetDateOfMembership(member1);
        System.out.println();

        System.out.println("Test Fetch Member Details");
        testFetchMemberDetails("22022171");
        System.out.println();

        System.out.println("Test Reserve Book Item");
        testReserveBookItem(member1);
        System.out.println();

        System.out.println("Test Cancel Reservation");
        testCancelReservation(member1);
        System.out.println();

        System.out.println("Test Lend Book Item");
        testLendBookItem(member1);
        System.out.println();

        System.out.println("Test Renew Book Item");
        testRenewBookItem(member1);
        System.out.println();

        System.out.println("Test Return Book Item");
        testReturnBookItem(member1);
        System.out.println();

        System.out.println("Test View Lending History");
        testViewLendingHistory(member1);
        System.out.println();

        System.out.println("Test View Reservations");
        testViewReservations(member1);
        System.out.println();

        System.out.println("Test View Notifications");
        testViewNotifications(member1);
        System.out.println();

        System.out.println("Test Update Information");
        testUpdateInformation(member1);
        System.out.println();
    }

    public static void testGetDateOfMembership(@NotNull Member member) {
        System.out.println("Date Of Membership: " + member.getDateOfMembership());
    }

    public static void testFetchMemberDetails(String id) {
        Member member = Member.fetchMemberDetails(id);
        if (member != null) {
            member.printDetails();
        } else {
            System.out.println("Member with ID: " + id + " not found.");
        }
    }

    public static void testReserveBookItem(@NotNull Member member) {
        member.setTotalBooksCheckedOut(5);
        BookItem bookItem = BookItemDatabase.getInstance().getItems().getFirst();
        member.reserveBookItem(bookItem);
    }

    public static void testCancelReservation(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getInstance().getItems().getFirst();
        member.cancelReservation(bookItem);
    }

    public static void testLendBookItem(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getInstance().getItems().get(1);
        member.lendBookItem(bookItem);
    }

    public static void testRenewBookItem(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getInstance().getItems().get(1);
        member.renewBookItem(bookItem);
    }

    public static void testReturnBookItem(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getInstance().getItems().get(1);
        member.returnBookItem(bookItem);
    }

    public static void testViewLendingHistory(@NotNull Member member) {
        member.viewLendingHistory();
    }

    public static void testViewReservations(@NotNull Member member) {
        member.viewReservationsHistory();
    }

    public static void testViewNotifications(@NotNull Member member) {
        member.viewNotifications();
    }

    public static void testUpdateInformation(@NotNull Member member) {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person person = new Person("Nguyen Tuan Anh", "22022168@vnu.edu.vn", address);
        member.updateInformation(person);
    }
}