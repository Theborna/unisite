package uni.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Professor {
    private String name;
    private LocalDate birthDate;
    private Department department;
    private AcademicRank rank;

    private static Set<Professor> professors = new LinkedHashSet<>();

    public Professor(String name) {
        this.name = name;
    }

    public void add() {
        professors.add(this);
    }

    public static Set<Professor> getProfessors() {
        return professors;
    }

    public Set<Course> getCourse() {
        Set<Course> courses = new LinkedHashSet<>();
        for (Course course : Course.getCourses())
            if (course.getInstructor().equals(this))
                courses.add(course);
        return courses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
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
        Professor other = (Professor) obj;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (rank != other.rank)
            return false;
        return true;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Department getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public AcademicRank getRank() {
        return rank;
    }

    public Professor setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Professor setDepartment(Department department) {
        if (department == null)
            throw new IllegalArgumentException("no such department");
        this.department = department;
        return this;
    }

    public Professor setName(String name) {
        this.name = name;
        return this;
    }

    public Professor setRank(AcademicRank rank) {
        this.rank = rank;
        return this;
    }

    public static Professor get(String text) {
        for (Professor professor : professors)
            if (professor.getName().equalsIgnoreCase(text))
                return professor;
        return null;
    }

}
