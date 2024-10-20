package org.group4.base.entities;

public record Address(String city,
                      String district,
                      String ward,
                      String street,
                      int number) {
}