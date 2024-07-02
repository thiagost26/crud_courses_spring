package com.home.crud_spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.home.crud_spring.dto.CourseDTO;
import com.home.crud_spring.dto.CoursePageDTO;
import com.home.crud_spring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins="*", allowedHeaders="*")
public class CoursesController {

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public CoursePageDTO list(@RequestParam(defaultValue= "0") @PositiveOrZero int page,
     @RequestParam(defaultValue= "10") @Positive @Max(100) int pageSize) {
        return courseService.list(page, pageSize);
    }

    // @GetMapping
    // public List<CourseDTO> list() {
    //     return courseService.list();
    // }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(@RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.create(course);
    }


    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id,
        @RequestBody @Valid @NotNull CourseDTO course) {
            return courseService.update(id, course);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        courseService.delete(id);
    }

}
