package com.cnsseftstudy.kma.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.security.auth.Subject;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tblCategory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 300, unique = false)
    private String categoryName;
    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledCategories")
    private Set<Product> products = new HashSet<>();
}
