package org.group4.base.test;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;
import org.group4.base.books.BookItem;
import org.group4.base.database.BookItemDatabase;
import org.jetbrains.annotations.NotNull;

public class TestMemberFunction {
    public static void main(String[] args) {
        Person person1 = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn");
        Member member1 = new Member("22022171", "123", person1);

        testGetDateOfMembership(member1);
        testFetchMemberDetails("22022171");
        testReserveBookItem(member1);
        testCancelReservation(member1);
        testLendBookItem(member1);
        testRenewBookItem(member1);
        testReturnBookItem(member1);
        testViewLendingHistory(member1);
        testViewReservations(member1);
        testViewNotifications(member1);
        testUpdateInformation(member1);
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
        BookItem bookItem = BookItemDatabase.getBookItems().getFirst();
        member.reserveBookItem(bookItem);
    }

    public static void testCancelReservation(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getBookItems().getFirst();
        member.cancelReservation(bookItem);
    }

    public static void testLendBookItem(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getBookItems().get(1);
        member.lendBookItem(bookItem);
    }

    public static void testRenewBookItem(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getBookItems().get(1);
        member.renewBookItem(bookItem);
    }

    public static void testReturnBookItem(@NotNull Member member) {
        BookItem bookItem = BookItemDatabase.getBookItems().get(1);
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
        Person person = new Person("Nguyen Tuan Anh", "22022168@vnu.edu.vn");
        member.updateInformation(person);
    }
}