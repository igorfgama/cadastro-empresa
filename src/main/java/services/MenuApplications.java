package services;

import models.Employee;
import models.Job;
import models.Manager;

import java.io.*;
import java.util.*;

public class MenuApplications {
    static Scanner scanner = new Scanner(System.in);
    static List<Employee> employees = new ArrayList<>();

    public static void init() throws FileNotFoundException {
        FilesService.loadFile();
        Job job = new Job();
        job.loadJobs();
        menu();
    }

    public static void menu() throws FileNotFoundException {

        while (true) {
            selectionMenu();
            try {
                int c = scanner.nextInt();

                switch (c) {
                    case 1 -> employees.add(ProcessingService.getAnalyst());
                    case 2 -> employees.add(ProcessingService.getManager());
                    case 3 -> ProcessingService.getRegister();
                    case 4 -> getList();
                    case 5 -> ProcessingService.recordJobs();
                    case 6 -> login();
                    case 7 -> FilesService.closeProgram();
                    case 8 -> new Job().getJobs();
                    default -> System.out.println("Entrada inválida.");
                }
            } catch (InputMismatchException | IOException e) {
                throw new InputMismatchException("Entrada inválida.");
            }
        }
    }

    public static void selectionMenu() {
        System.out.println("1. Cadastrar Analista");
        System.out.println("2. Cadastrar Gerente");
        System.out.println("3. Consultar Cadastro por Chave");
        System.out.println("4. Listar registros");
        System.out.println("5. Registrar Tarefas");
        System.out.println("6. Login");
        System.out.println("7. Sair");
        System.out.print(" -> ");
    }



    public static void getList() {
        employees.forEach(employee -> System.out.println(employee.getLogin()));

//        employees.sort(Comparator.comparing(Employee::getName));
    }

    public static void login(){
        System.out.println("///// LOGIN /////");

        try{
            System.out.println("Chave");
            String key = scanner.next();
            System.out.println("Senha: ");
            String password = scanner.next();

            Employee aux = ProcessingService.returnEmployee(key);
            if(Objects.isNull(aux))
                System.out.println("Chave invalida.");
            else {
                if (Manipulation.loginValidate(aux, key, password)) {
                    System.out.println("Olá, " + aux.getLogin());
                    screenLoginManager(aux);
                }
            }
            menu();
        } catch (InputMismatchException | FileNotFoundException e){
            throw new InputMismatchException("Entrada inválida.");
        }
    }

    private static void screenLoginManager(Employee aux) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("/// Selecione ///");
        if(aux instanceof Manager){
            System.out.println("1. Registrar Atividade");
            System.out.println("2. Designar Time");
            System.out.println("3. Sair");
            System.out.println(" -> ");
            int c = scanner.nextInt();

            switch (c){
                case 1 -> ProcessingService.recordJobs();
                case 2 -> Manipulation.setTeam();
                case 3 -> menu();
                default -> System.out.println("Entrada inválida.");
            }
        }
    }
}
