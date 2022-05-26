package uni.models;

import java.util.ArrayList;
import java.util.List;

public class GradeReport {
    private Student student;
    private Course course;
    private double grade;

    private static List<GradeReport> grades = new ArrayList<GradeReport>();

    public GradeReport(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public static List<GradeReport> getGrades() {
        return grades;
    }

    public void addGrade() {
        grades.add(this);
    }

    @Override
    public String toString() {
        return "GradeReport [course=" + course + ", grade=" + grade + ", student=" + student + "]";
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    public GradeReport setGrade(double grade) {
        this.grade = grade;
        return this;
    }
}
