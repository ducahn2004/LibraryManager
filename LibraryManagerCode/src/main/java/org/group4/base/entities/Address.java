package org.group4.base.entities;

/**
 * Dai dien cho dia chi cua nguoi dung hoac thu vien.
 */
public class Address {
  private String streetAddress; // Dia chi nha.
  private String city; // Thanh pho.
  private String state; // Tinh.
  private String zipcode; // Ma buu chinh.
  private String country; // Quoc gia.

  /**
   * Tao dia chi moi.
   * @param streetAddress Dia chi nha.
   * @param city Thanh pho.
   * @param state Tinh.
   * @param zipcode Ma buu chinh.
   * @param country Quoc gia.
   */
  public Address(String streetAddress, String city, String state, String zipcode, String country) {
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
    this.country = country;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
