package com.cnsseftstudy.kma.models;

import jakarta.persistence.*;
import org.w3c.dom.Text;

@Entity
@Table(name = "tblBrand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = true, length = 512)
    private String brandName;
    @Lob
    private String desc;

    public Brand() {
    }

    public Brand( String brandName, String desc) {
        this.brandName = brandName;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
