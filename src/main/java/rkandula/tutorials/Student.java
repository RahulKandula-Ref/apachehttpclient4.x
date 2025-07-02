package rkandula.tutorials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

public class Student {
    private Integer studentId;
    private String fname;
    private String lname;


    public Student(Integer studentId, String fname, String lname) {
        this.studentId = studentId;
        this.fname = fname;
        this.lname = lname;
    }

    public Student() {
    }

    public static List<Student> parseFromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Student>>() {});
    }

    public static String toJson(Student student) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(student);
    }

    public static String dummyStudentAsJson() throws JsonProcessingException {
        return toJson(new Student(12, "rahu", "kaka"));
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId);
    }

    @Override
    public String toString() {
        return "Student {" +
                "studentId=" + studentId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
