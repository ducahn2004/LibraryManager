package org.group4.base.entities;

public record Address(String streetAddress,
                      String city,
                      String state,
                      String zipcode,
                      String country) {
}