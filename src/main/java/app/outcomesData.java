package app;

public class outcomesData {
    private int code;
    private String name;
    private String indigenousStatus;
    private String sex;
    private String uniqueAttribute;
    private int count;
    private boolean incomeTable;

    
    public outcomesData(int code, String name, String indigenousStatus, String uniqueAttribute, int count){
        this.code = code;
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.uniqueAttribute = uniqueAttribute;
        this.incomeTable = true;
        this.count = count;
    }

   
    public outcomesData(int code, String name, String indigenousStatus, String sex, String uniqueAttribute, int count){
        this.code = code;
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.sex = sex;
        this.uniqueAttribute = uniqueAttribute;
        this.count = count;
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

    public boolean isIncomeTable(){
        return incomeTable;
    }
}
