package uni.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Student {
    private String id, name;
    private LocalDate birthDate;
    private Department department;

    private static Set<Student> students = new HashSet<Student>();

    public Student(String id) {
        this.id = id;
        students.add(this);
    }

    public static Set<Student> getStudents() {
        return students;
    }

    public void takeCourse(Course course) {
        // TODO: kos
    }

    public void finishCourse(Course course, double score) {
        // TODO: kos
    }

    public List<GradeReport> getGradeReport() {
        // TODO: kos
        return null;
    }

    public List<Course> getCourses() {
        // TODO: kos
        return null;
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
    public boolean equals(Object obj) {
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
