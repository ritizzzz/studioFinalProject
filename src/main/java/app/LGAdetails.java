package app;

public class LGAdetails {
    private int code;
    private int year;
    private String stateAbbr;
    private String lgaName;
    private String lgaType;
    private double areaSqkm;
    private double latitude;
    private double longitude;

    public LGAdetails(int code, int year, String stateAbbr, String lgaType, String lgaName, double areaSqkm, double latitude, double longitude){
        this.code = code;
        this.year = year;
        this.stateAbbr = stateAbbr;
        this.lgaType = lgaType;
        this.lgaName = lgaName;
        this.areaSqkm = areaSqkm;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LGAdetails() {
    }

    public int getCode(){
        return code;
    }

    public int getYear(){
        return year;
    }

    public String getStateAbbr(){
        return stateAbbr;
    }

    public String getLgaType(){
        return lgaType;
    }

    public String getLgaName(){
        return lgaName;
    }

    public double getAreaSqkm(){
        return areaSqkm;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

}
