package uni.models;

public class GradeReport {
    private Student student;
    private Course course;
    private double grade;

    public GradeReport(Student student, Course course) {
        this.student = student;
        this.course = course;
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

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
