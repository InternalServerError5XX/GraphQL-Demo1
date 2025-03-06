package org.example.Models;

public class Country
{
    private int id;
    private String name;
    private int countryCode;

    public Country() {}
    public Country(int id, String name, int countryCode)
    {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCountryCode() { return countryCode; }
}
