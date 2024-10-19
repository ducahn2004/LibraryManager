package org.group4.base.test;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;
import org.group4.base.books.BookItem;
import org.group4.base.database.BookItemDatabase;

public class TestMemberFunction {
    public static void main(String[] args) {
        Person person1 = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn");
        Member member1 = new Member("22022171", "123", person1);

        testGetDateOfMembership(member1);
        testFetchMemberDetails("22022171");
        testReserveBookItem(member1);
    }

    public static void testGetDateOfMembership(Member member) {
        System.out.println("Date Of Membership: " + member.getDateOfMembership());
    }

    public static void testFetchMemberDetails(String id) {
        Member member = Member.fetchMemberDetails(id);
        if (member != null) {
            System.out.println("ID: " + id);
            System.out.println("Name: " + member.getPerson().getName());
            System.out.println("Email: " + member.getPerson().getEmail());
            System.out.println("Date Of Membership: " + member.getDateOfMembership());
        } else {
            System.out.println("Member with ID: " + id + " not found.");
        }
    }

    public static void testReserveBookItem(Member member) {
        member.setTotalBooksCheckedOut(5);
        // Fetch a sample BookItem from the database
        BookItem bookItem = BookItemDatabase.getBookItems().getFirst();
        member.reserveBookItem(bookItem);
    }
}