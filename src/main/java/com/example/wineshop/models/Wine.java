package com.example.wineshop.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Wine {

    int actualYear = LocalDate.now().getYear();

    private @Id @GeneratedValue Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "winery_id")
    @NotEmpty
    private Winery winery;

    @Min(1900) /*@Max(actualYear)*/ @NotEmpty
    private int year;

    @Min(0) @Max(5) @NotEmpty
    private float rating;

    @Min(0) @NotEmpty
    private int num_reviews;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @NotEmpty
    private Region region;

    @Min(0) @NotEmpty
    private float price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotEmpty
    private Type type;

    @Min(1) @Max(5) @NotEmpty
    private int body;

    @Min(1) @Max(5) @NotEmpty
    private int acidity;

    public Wine(){}

    public Wine(String name, Winery winery, int year, float rating, int num_reviews, String country, Region region, float price, Type type, int body, int acidity) {
        this.name = name;
        this.winery = winery;
        this.year = year;
        this.rating = rating;
        this.num_reviews = num_reviews;
        this.region = region;
        this.price = price;
        this.type = type;
        this.body = body;
        this.acidity = acidity;
    }

    public Winery getWinery() {
        return winery;
    }

    public void setWinery(Winery winery) {
        this.winery = winery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNum_reviews() {
        return num_reviews;
    }

    public void setNum_reviews(int num_reviews) {
        this.num_reviews = num_reviews;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getAcidity() {
        return acidity;
    }

    public void setAcidity(int acidity) {
        this.acidity = acidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wine wine = (Wine) o;
        return year == wine.year && Float.compare(wine.rating, rating) == 0 && num_reviews == wine.num_reviews && Float.compare(wine.price, price) == 0 && body == wine.body && acidity == wine.acidity && Objects.equals(id, wine.id) && Objects.equals(name, wine.name) && Objects.equals(region, wine.region) && Objects.equals(type, wine.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, rating, num_reviews, region, price, type, body, acidity);
    }

    @Override
    public String toString() {
        return "Wine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", num_reviews=" + num_reviews +
                ", region=" + region +
                ", price=" + price +
                ", type=" + type +
                ", body=" + body +
                ", acidity=" + acidity +
                '}';
    }
}
