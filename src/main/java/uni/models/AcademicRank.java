package uni.models;

public enum AcademicRank {
    PROFESSOR, ASSISTANT_PROFESSOR, ASSOCIATE_PROFESSOR;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
