package org.group4.base.entities;

import java.util.List;

public record Author(String name, String description, List<Book> books) {

  public void setName(String text) {
    //TODO: Complete
  }
  //TODO: Write toString to print all author's name, delete description :))
}