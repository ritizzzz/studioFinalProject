package app;

public class outcomesProportion {
    private int code;
    private String name;
    private String indigenousStatus;
    private String sex;
    private String uniqueAttribute;
    private int count;
    private int total;
    private double proportion;
    private boolean incomeTable;

    
    public outcomesProportion(int code, String name, String indigenousStatus, String uniqueAttribute, int count, int total, double proportion){
        this.code = code;
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.uniqueAttribute = uniqueAttribute;
        this.incomeTable = true;
        this.count = count;
        this.total = total;
        this.proportion = proportion;
    }

   
    public outcomesProportion(int code, String name, String indigenousStatus, String sex, String uniqueAttribute, int count, int total, double proportion){
        this.code = code;
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.sex = sex;
        this.uniqueAttribute = uniqueAttribute;
        this.count = count;
        this.total = total;
        this.proportion = proportion;
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
    public int getCount(){
        return count;
    }

    public int getTotal(){
        return total;
    }

    public double getProportion(){
        return proportion;
    }

    public boolean isIncomeTable(){
        return incomeTable;
    }    
}
