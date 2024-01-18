package org.learning.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pizzas")


public class Pizza {
    // ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private String photo;
    @NotNull
    @Min(1)
    @Column(nullable = false)
    private BigDecimal price;

    // ATTRIBUTO CHE RAPPRESENTA LE OFFERTE (RELAZIONE UNO A MOLTI)
    @OneToMany(mappedBy = "pizza")
    private List<Offerta> OffertaList;

    // ATTRIBUTO CHE RAPPRESENTA GLI INGREDIENTI (RELAZIONE MOLTI A MOLTI)
    @ManyToMany
    private List<Ingredienti> ingredients;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Offerta> getOffertaList() {
        return OffertaList;
    }

    public void setOffertaList(List<Offerta> offertaList) {
        OffertaList = offertaList;
    }

    public List<Ingredienti> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredienti> ingredients) {
        this.ingredients = ingredients;
    }
}