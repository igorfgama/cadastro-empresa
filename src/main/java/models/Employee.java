package models;

public abstract class Employee {
    protected String login;
    private String password;


    public Employee(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean passwordValued(String password){
        return this.password.equals(password);
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString(){
        return this.login + " | " + this.password;
    }

    public String getPassword() {
        return password;
    }
}
