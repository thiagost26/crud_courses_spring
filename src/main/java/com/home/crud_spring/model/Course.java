package com.home.crud_spring.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.home.crud_spring.enums.Category;
import com.home.crud_spring.enums.Status;
import com.home.crud_spring.enums.converters.CategoryConverter;
import com.home.crud_spring.enums.converters.StatusConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "cursos")
@SQLDelete(sql = "UPDATE Cursos SET status = 'Inativo' WHERE id = ?")
@Where(clause= "status = 'Ativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @NotNull
    @NotBlank
    @Length(min= 5, max= 100)
    @Column(length = 100, name = "nome", nullable = false)    
    private String name;

    @NotNull
    @Convert(converter = CategoryConverter.class)
    @Column(length = 10, nullable = false)
    private Category category;

    @NotNull
    @Convert(converter = StatusConverter.class)
    @Column(length = 10, nullable = false)
    private Status status = Status.ACTIVE;

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Status getStatus() {
        return status;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    
    
    
}
