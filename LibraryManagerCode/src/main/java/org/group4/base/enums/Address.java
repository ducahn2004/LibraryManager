package org.group4.base.enums;

public class Address {
    private String provinceOrCity;
    private String district;
    private String ward;
    private String street;
    private String houseNumber;

    public Address(String provinceOrCity, String district, String ward, String street, String houseNumber) {
        this.provinceOrCity = provinceOrCity;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getProvinceOrCity() {
        return provinceOrCity;
    }

    public void setProvinceOrCity(String provinceOrCity) {
        this.provinceOrCity = provinceOrCity;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return houseNumber + " " + street + ", " + ward + ", " + district + ", " + provinceOrCity;
    }
}