package models;

public class Manager extends Employee {
    private String managerKey;

    public Manager(String name, String password) {
        super(name, password);
        managerKey = setManagerKey();
        System.out.println("Chave G: " + managerKey);
    }

    private String setManagerKey(){
        return "" +
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
