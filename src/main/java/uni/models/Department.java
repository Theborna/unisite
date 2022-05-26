package uni.models;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Department {

    private String name, id;
    private static Set<Department> departments = new LinkedHashSet<Department>();

    public Department(String name) {
        this.name = name;
    }

    public void add() {
        departments.add(this);
    }

    public static Set<Department> getDepartments() {
        return departments;
    }

    public List<Student> getStudent() {
        // TODO: kos
        return null;
    }

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        for (Course course : Course.getCourses())
            if (course.getDepartment().equals(this))
                courses.add(course);
        return courses;
    }

    public List<Professor> getFaculty() {
        List<Professor> faculty = new ArrayList<>();
        for (Professor professor : Professor.getProfessors())
            if (professor.getDepartment().equals(this))
                faculty.add(professor);
        return faculty;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Department other = (Department) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public String getId() {
        return id;
    }

    public Department setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Department setName(String name) {
        this.name = name;
        return this;
    }

    public static Department get(String text) {
        for (Department d : departments)
            if (d.getName().equalsIgnoreCase(text))
                return d;
        return null;
    }

}
