package app;

public class personas {
    private String name;
    private String imageFilePath;
    public personas(String name, String imageFilePath) {
        this.name = name;
        this.imageFilePath = imageFilePath;
    }
    public String getName(){
        return name;
    }
    public String getImgFilePath(){
        return imageFilePath;
    } 
}
