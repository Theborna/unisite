package uni.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course {
    private String name;
    private int credits;
    private Department department;
    private Professor instructor;

    private static List<Course> courses = new ArrayList<>();

    public Course(String name) {
        this.name = name;
    }

    public static void add(Course course) {
        if (courses.add(course))
            System.out.println("gooz");
    }

    public Course setCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (credits != other.credits)
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (instructor == null) {
            if (other.instructor != null)
                return false;
        } else if (!instructor.equals(other.instructor))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public Course setDepartment(Department department) {
        if (department == null)
            throw new IllegalArgumentException("no such department");
        this.department = department;
        return this;
    }

    public Course setInstructor(Professor instructor) {
        if (instructor == null)
            throw new IllegalArgumentException("no such instructor");
        this.instructor = instructor;
        return this;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public Department getDepartment() {
        return department;
    }

    public Professor getInstructor() {
        return instructor;
    }

    public String getName() {
        return name;
    }
}
