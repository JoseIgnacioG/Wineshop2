package com.example.wineshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
class Winery {

  private @Id @GeneratedValue Long id;
  private String name;


  public Winery() {}

  public Winery(String name) {
  this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Winery winery = (Winery) o;
        return Objects.equals(id, winery.id) && Objects.equals(name, winery.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Winery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
