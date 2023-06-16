package app;

public class SEO {
    private int id;
    private String title;
    private String description;

    public SEO(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public int getID(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
}

