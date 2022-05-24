package uni.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Professor {
    private String name;
    private LocalDate birthDate;
    private Department department;
    private AcademicRank rank;

    private static Set<Professor> professors = new HashSet<>();

    public Professor(String name) {
        this.name = name;
        professors.add(this);
    }

    public static Set<Professor> getProfessors() {
        return professors;
    }

    public Set<Course> getCourse() {
        // TOOD: kos
        return null;
    }

    @Override
    public boolean equals(Object obj) {
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
        if (rank == null) {
            if (other.rank != null)
                return false;
        } else if (!rank.equals(other.rank))
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
        for(Professor professor : professors)
            if(professor.getName().equalsIgnoreCase(text))
                return professor;
        return null;
    }

}
