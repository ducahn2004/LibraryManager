package org.group4.base.catalog;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.books.BookItem;

/**
 * Interface ho tro tim kiem sach.
 * Tim kiem sach theo tieu de, tac gia, chu de, ngay xuat ban.
 */
public interface Search {

  /**
   * Tim kiem sach theo tieu de.
   * @param title Tieu de sach.
   * @return Danh sach cac sach co tieu de trung voi tieu de nhap vao.
   */
  List<BookItem> searchByTitle(String title);

  /**
   * Tim kiem sach theo tac gia.
   * @param author Tac gia cua sach.
   * @return Danh sach cac sach co tac gia trung voi tac gia nhap vao.
   */
  List<BookItem> searchByAuthor(String author);

  /**
   * Tim kiem sach theo chu de.
   * @param subject Chu de cua sach.
   * @return Danh sach cac sach co chu de trung voi chu de nhap vao.
   */
  List<BookItem> searchBySubject(String subject);

  /**
   * Tim kiem sach theo ngay xuat ban.
   * @param publicationDate Ngay xuat ban cua sach.
   * @return Danh sach cac sach co ngay xuat ban trung voi ngay xuat ban nhap vao.
   */
  List<BookItem> searchByPubDate(LocalDate publicationDate);
}
