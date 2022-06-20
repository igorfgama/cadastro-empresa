package services;

import models.Analyst;
import models.Employee;
import models.Job;
import models.Manager;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static services.MenuApplications.employees;

public class ProcessingService {

    public static void getRegister() throws InputMismatchException {
        System.out.println("** PROCURANDO REGISTRO POR CHAVE **");

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Chave: ");
            String a = scanner.next();
            System.out.println("---------------------");

            Employee aux = ProcessingService.returnEmployee(a);

            if(Objects.isNull(aux))
                System.out.println("Chave não encontrada.");
            else{
                System.out.println("   Nome: " + aux.getLogin());

                if(aux instanceof Analyst){
                    System.out.println("   Time: " + ((Analyst) aux).getTeam());
                }
                System.out.println("---------------------");
            }
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Não foi possível efetuar o registro.");
        }
    }
    public static Analyst getAnalyst() throws InputMismatchException {

        System.out.println("** CADASTRANDO ANALISTA **");

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nome: ");
            String name = scanner.next();
            System.out.println("Senha: ");
            String password = scanner.next();
            return new Analyst(name, password);
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Não foi possível efetuar o registro.");
        }
    }

    public static Manager getManager() throws InputMismatchException {
        System.out.println("** CADASTRANDO GERENTE **");

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nome: ");
            String name = scanner.next();
            System.out.println("Senha: ");
            String password = scanner.next();
            return new Manager(name, password);
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Não foi possível efetuar o registro.");
        }
    }

    static void recordJobs() {
        System.out.println("*** ADICIONANDO TRABALHOS **");

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Chave da conta: ");
            String login = scanner.next();

            Employee aux = ProcessingService.returnEmployee(login);

            if(Objects.isNull(aux)){
                System.out.println("Chave não encontrada.");
            }
            else {
                System.out.println("Descreva a tarefa: ");
                String describe = scanner.next();
                Job job = new Job();
                job.setJobs(login, describe);
                System.out.println("Registrado");
            }
            MenuApplications.menu();                                                  // <<-- VOLTANDO AO MENU PRINCIPAL
        } catch (RuntimeException | IOException e) {
            throw new InputMismatchException("Entrada inválida.");
        }
    }

    public static Employee returnEmployee(String a) {
        for (Employee aux : employees) {
            if (aux.getLogin().equals(a)) {
                return aux;
            }
        }
        return null;
    }


}
