package com.home.crud_spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.home.crud_spring.dto.CourseDTO;
import com.home.crud_spring.dto.CoursePageDTO;
import com.home.crud_spring.dto.mapper.CourseMapper;
import com.home.crud_spring.exception.RecordNotFoundException;
import com.home.crud_spring.model.Course;
import com.home.crud_spring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Validated
@Service
public class CourseService {
    
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(int page, @Positive @Max(100) int pageSize) {
        Page<Course> pageCourse = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());
        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }    

    // public CoursePageDTO list() {
    //     // Page<Course> page = courseRepository.findAll(PageRequest.of(0, 10));
    //     Page<Course> page = courseRepository.findAll(PageRequest.of(0, 10));
    //     
    //     return new CoursePageDTO(courses, page.getTotalElements(), page.getTotalPages());
    // }


    // public List<CourseDTO> list() {
    //     return courseRepository.findAll()
    //         .stream()
    //         .map(courseMapper::toDTO)
    //         .collect(Collectors.toList());
    // }    

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

     public CourseDTO create(@Valid @NotNull CourseDTO courseDTO) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
    }


    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id)
            .map(recordFound -> {
                Course course = courseMapper.toEntity(courseDTO);
                recordFound.setName(courseDTO.name());
                recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                recordFound.getLessons().clear();
                course.getLessons().forEach(recordFound.getLessons()::add);
                return courseMapper.toDTO(courseRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
