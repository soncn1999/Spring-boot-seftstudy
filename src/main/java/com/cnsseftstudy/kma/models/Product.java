package com.cnsseftstudy.kma.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tblProduct")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, unique = true, length = 300)
    private String productName;
    private int year;
    private Double price;
    private String url;
    @ManyToMany
    @JoinTable(
            name = "category_enrolled",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    public Set<Category> enrolledCategories = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Transient
    private int age;

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }
}
