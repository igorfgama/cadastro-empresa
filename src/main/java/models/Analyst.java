package models;

import java.math.BigDecimal;

public class Analyst extends Employee {
    private String team;

    public Analyst(String name, String password){
        super(name, password);
    }

    public void setTeam(String team){
        this.team = team;
    }

    public String getTeam(){
        return team;
    }

    @Override
    public void setIncome() {
        this.income = income.multiply(new BigDecimal("1.15"));
    }
}
