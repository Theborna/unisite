package uni.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Student {
    private String id, name;
    private LocalDate birthDate;
    private Department department;

    private static Set<Student> students = new LinkedHashSet<Student>();

    public Student(String id) {
        this.id = id;
    }

    public void addStudent() {
        students.add(this);
    }

    public static Set<Student> getStudents() {
        return students;
    }

    public void takeCourse(Course course) {
        new GradeReport(this, course).addGrade();
    }

    public void finishCourse(Course course, double score) {
        if (score < 0 || score > 20)
            throw new IllegalArgumentException("score must be between 0 and 20");
        for (GradeReport report : GradeReport.getGrades())
            if (report.getStudent().equals(this) && report.getCourse().equals(course))
                report.setGrade(score);
    }

    public List<GradeReport> getGradeReport() {
        ArrayList<GradeReport> report = new ArrayList<GradeReport>();
        for (GradeReport grade : GradeReport.getGrades())
            if (grade.getStudent().equals(this))
                report.add(grade);
        return report;
    }

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<Course>();
        List<GradeReport> reports = getGradeReport();
        for (GradeReport report : reports)
            courses.add(report.getCourse());
        return courses;
    }

    public String getStudent() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Student setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public Student setDepartment(Department department) {
        this.department = department;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
