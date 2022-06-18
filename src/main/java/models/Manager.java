package models;

import java.math.BigDecimal;

public class Manager extends Employee {
    private String managerKey;
    private int totalManager;

    public Manager(String name, String password) {
        super(name, password);
        managerKey = setManagerKey();
        System.out.println("Chave G: " + managerKey);
    }

    @Override
    public void setIncome() {
        this.income = income.multiply(new BigDecimal("1.6"));
    }

    private String setManagerKey(){
        return "" +
                Manager.total++ +
                ((int) (Math.random() * 100)) +
                ((int) (Math.random() * 100)) + "";
    }

    public String getManagerKey(){
        return managerKey;
    }

    public boolean keyGValued(String keyG){
        return this.managerKey.equals(keyG);
    }
}
