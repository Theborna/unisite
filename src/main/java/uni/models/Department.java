package uni.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Department {

    private String name, id;
    private static Set<Department> departments = new HashSet<Department>();

    public Department(String name) {
        this.name = name;
        departments.add(this);
    }

    public static Set<Department> getDepartments() {
        return departments;
    }

    public List<Student> getStudent() {
        // TODO: kos
        return null;
    }

    public List<Professor> getFaculty() {
        // TODO: kos
        return null;
    }

    @Override
    public boolean equals(Object obj) {
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
