package Movie.MovieCommunity.domain.ranking;

public enum MovieNationType {

    All("전체"), KOREA("국내"), FOREIGN("외국");
    private final String description;

    MovieNationType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
