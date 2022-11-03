package step.learning.oop;

public abstract class Literature {
    private String title ;

    public String getTitle() {
        return title;
    }

    public Literature setTitle(String title) {
        this.title = title;
        return this;
    }

}
