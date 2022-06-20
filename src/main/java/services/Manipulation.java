package services;

import models.Analyst;
import models.Employee;
import models.Manager;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Manipulation {
    public static boolean getLogin(Employee employee, String key, String password) {
        return (employee.getLogin().equals(key) && employee.passwordValued(password));
    }

    public static boolean getLogin(Manager manager, String key, String password, String keyG) {
        return (manager.getLogin().equals(key) && manager.passwordValued(password) && manager.keyGValued(keyG));
    }

    public static boolean loginValidate(Employee aux, String key, String password) {
        Scanner scanner = new Scanner(System.in);

        if(aux instanceof Manager){
            System.out.println("Senha G: ");
            String keyG = scanner.next();
            if(!(Manipulation.getLogin((Manager) aux, key, password, keyG))){
                System.out.println("Dados incorretos. Tente novamente.");
                return false;
            }
        } else{
            if(!(Manipulation.getLogin(aux, key, password))){
                System.out.println("Dados incorretos. Tente novamente.");
                return false;
            }
        }
        return true;
    }

    public static void setTeam() {
        Scanner scanner = new Scanner(System.in);

        try{
            System.out.println("Chave do Analista");
            String key = scanner.next();
            Analyst aux = (Analyst) ProcessingService.returnEmployee(key);
            if(Objects.isNull(aux))
                System.out.println("Chave inexistente.");
            else{
                System.out.println("Determine o time: ");
                String team = scanner.next();
                aux.setTeam(team);
                System.out.println("Cadastrado.");
            }
        } catch (InputMismatchException e){
            throw new InputMismatchException("Entrada inválida.");
        }
    }
}