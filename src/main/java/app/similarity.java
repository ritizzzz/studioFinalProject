package app;

public class similarity {
    private int code;
    private int year;
    private int population;
    private int targetPopulation;
    private double difference;

    public similarity(int code, int year, int population, int targetPopulation, double difference) {
        this.code = code;
        this.year = year;
        this.population = population;
        this.targetPopulation = targetPopulation;
        this.difference = difference;
    }

    public int getCode(){
        return code;
    }

    public int getYear(){
        return year;
    }

    public int getPopuplation(){
        return population;
    }

    public int getTargetPopulation(){
        return targetPopulation;
    }

    public double getDifference(){
        return difference;
    }

}
