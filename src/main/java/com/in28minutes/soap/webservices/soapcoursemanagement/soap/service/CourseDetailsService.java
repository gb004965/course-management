package com.in28minutes.soap.webservices.soapcoursemanagement.soap.service;

import com.in28minutes.soap.webservices.soapcoursemanagement.soap.bean.Course;

import java.util.List;

public interface CourseDetailsService {
    // 3 services
    // course - 1
    // findById()
    public Course findById(int id);

    // courses
    // findAll()
    public List<Course> findAll();

    // delete course
    // deleteById
    public int deleteById(int id);
}
