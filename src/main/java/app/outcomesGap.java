package app;

public class outcomesGap {
    private int code;
    private String name;
    private String indigenousStatus;
    private String sex;
    private String uniqueAttribute;
    private double indigenousProportion;
    private double nonIndigenousProportion;
    private double gap;
    private boolean incomeTable;

    
    public outcomesGap(int code, String name, String uniqueAttribute, double indigenousProportion, double nonIndigenousProportion, double gap){
        this.code = code;
        this.name = name;
        this.uniqueAttribute = uniqueAttribute;
        this.incomeTable = true;
        this.indigenousProportion = indigenousProportion;
        this.nonIndigenousProportion = nonIndigenousProportion;
        this.gap = gap;
    }

   
    public outcomesGap(int code, String name, String sex, String uniqueAttribute, double indigenousProportion, double nonIndigenousProportion, double gap){
        this.code = code;
        this.name = name;
        this.sex = sex;
        this.uniqueAttribute = uniqueAttribute;
        this.indigenousProportion = indigenousProportion;
        this.nonIndigenousProportion = nonIndigenousProportion;
        this.gap = gap;
        this.incomeTable = false;
    }

    public int getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public String getIndigenousStatus(){
        return indigenousStatus;
    }
    public String getSex(){
        return sex;
    }
    public String getUniqueAttribute(){
        return uniqueAttribute;
    }
    public double getIndigenousProportion(){
        return indigenousProportion;
    }

    public double getNonIndigenousProportion(){
        return nonIndigenousProportion;
    }

    public double getGAP(){
        return gap;
    }

    public boolean isIncomeTable(){
        return incomeTable;
    }   
}
