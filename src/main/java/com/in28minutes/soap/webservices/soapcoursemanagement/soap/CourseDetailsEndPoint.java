package com.in28minutes.soap.webservices.soapcoursemanagement.soap;

import com.in28minutes.courses.*;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.bean.Course;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.service.CourseDetailsService;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.service.CourseDetailsServiceImpl.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndPoint {

    @Autowired
    CourseDetailsService service;

    // method
    // input  - request  GetCourseDetailsRequest
    // output - response GetCourseDetailsResponse

    // http://in28minutes.com/courses
    // GetCourseDetailsRequest
    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetails
            (@RequestPayload GetCourseDetailsRequest request) {

        Course course = service.findById(request.getId());

        return mapCourseDetails(course);
    }

    private static GetCourseDetailsResponse mapCourseDetails(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();

        if (course != null) {
            response.setCourseDetails(mapCourse(course));
        }

        return response;
    }

    private static GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for (Course course: courses) {
            CourseDetails mapCourse = mapCourse(course);
            response.getCourseDetails().add(mapCourse);
        }
        return response;
    }

    private static CourseDetails mapCourse(Course course) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetails
            (@RequestPayload GetAllCourseDetailsRequest request) {

        List<Course> courses = service.findAll();

        return mapAllCourseDetails(courses);
    }

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCourseDetailsRequest
            (@RequestPayload DeleteCourseDetailsRequest request) {
        Status status = service.deleteById(request.getId());
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(status));

        return response;
    }

    private com.in28minutes.courses.Status mapStatus(Status status) {
        if (status==Status.FAILURE) {
            return com.in28minutes.courses.Status.FAILURE;
        } else {
            return com.in28minutes.courses.Status.SUCCESS;
        }
    }


}
