package com.example.wineshop.models;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Winery {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    @OneToMany(mappedBy = "winery" , cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Wine> wineList;

    public Winery() {}

    public Winery(String name, List<Wine> wineList) {
        this.name = name;
        this.wineList = wineList;
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
