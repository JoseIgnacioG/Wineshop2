package com.example.wineshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
class Winery {

  private @Id @GeneratedValue Long id;
  private String name;
  @OneToMany
  private List<Wine> wineList;

  Winery() {}

  Winery(String name, List<Wine> wineList) {
  this.name = name;
  this.wineList = wineList;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public List<Wine> getWineList() {
  return wineList;
 }

 public void setWineList(List<Wine> wineList) {
  this.wineList = wineList;
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  Winery winery = (Winery) o;
  return Objects.equals(name, winery.name) && Objects.equals(wineList, winery.wineList);
 }

 @Override
 public int hashCode() {
  return Objects.hash(name, wineList);
 }

 @Override
 public String toString() {
  return "Winery{" +
          "name='" + name + '\'' +
          ", wineList=" + wineList +
          '}';
 }
}
