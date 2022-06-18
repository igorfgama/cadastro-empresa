package models;

import java.math.BigDecimal;
import java.util.*;

public abstract class Employee {
    protected String name;
    private String key;
    private String password;
    protected BigDecimal income = new BigDecimal("3334.95");
    static int total = 0;
    protected Map<String, String> jobs = new HashMap<>();

    public Employee(String name, String password) {
        this.name = name;
        Employee.total++;
        key = makeKey();
        System.out.println("Chave: " + key);
        this.password = password;
    }

    public boolean passwordValued(String password){
        return this.password.equals(password);
    }

    private String makeKey() {
        return "" +
                Employee.total +
                ((int) (Math.random() * 10)) +
                ((int) (Math.random() * 10)) +
                ((int) (Math.random() * 10)) + "";
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public BigDecimal getIncome(){
        return income;
    }

    public Map getJobs(){
        return Collections.unmodifiableMap(jobs);
    }

    public void setJobs(String key, String job){
        jobs.put(key, job);
    }

    public abstract void setIncome();

    @Override
    public String toString(){
        return this.name + " | " + this.key + " | R$ " + this.income;
    }
}
