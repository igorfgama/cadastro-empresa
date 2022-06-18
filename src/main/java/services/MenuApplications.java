package services;

import models.Analyst;
import models.Employee;
import models.Manager;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

public class MenuApplications {
    static Scanner scanner = new Scanner(System.in);
    static List<Employee> employees = new ArrayList<>();
    public static void menu() {
        while (true) {
            selectionMenu();
            try {
                int c = scanner.nextInt();

                switch (c) {
                    case 1:
                        employees.add(getAnalyst());
                        break;
                    case 2:
                        employees.add(getManager());
                        break;
                    case 3:
                        getRegister();
                        break;
                    case 4:
                        getList();
                        break;
                    case 5:
                        setJobs();
                        break;
                    case 6:
                        login();
                        break;
                    case 7:
                        closeProgram();
                        break;
                    default:
                        System.out.println("Entrada inválida.");
                        break;
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

    public static Analyst getAnalyst() throws InputMismatchException {
        System.out.println("** CADASTRANDO ANALISTA **");

        try {
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
            System.out.println("Nome: ");
            String name = scanner.next();
            System.out.println("Senha: ");
            String password = scanner.next();
            return new Manager(name, password);
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Não foi possível efetuar o registro.");
        }
    }

    public static void getRegister() throws InputMismatchException {
        System.out.println("** PROCURANDO REGISTRO POR CHAVE **");

        try {
            System.out.println("Chave: ");
            String a = scanner.next();
            System.out.println("---------------------");

            Employee aux = returnEmployee(a);

            if(Objects.isNull(aux))
                System.out.println("Chave não encontrada.");
            else{
                System.out.println("   Nome: " + aux.getName());
                System.out.println("Salário: R$ " + aux.getIncome());
                System.out.println("  Chave: " + aux.getKey());

                if(aux instanceof Analyst){
                    System.out.println("   Time: " + ((Analyst) aux).getTeam());
                }
                System.out.println("---------------------");
            }
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Não foi possível efetuar o registro.");
        }
    }

    public static void getList() {
//        for (Employee employee : employees) {
//            System.out.printf(employee.getName());
//            if (employee instanceof Analyst)
//                System.out.print(" -> Analista\n");
//            else
//                System.out.print(" -> Gerente\n");
//        }

//        employees.forEach(employee -> System.out.println(employee.getName()));

        employees.sort(Comparator.comparing(Employee::getName));
//        System.out.println(employees);
    }

    public static void setJobs() {
        System.out.println("*** ADICIONANDO TRABALHOS **");

        try {
            System.out.println("Chave da conta: ");
            String key = scanner.next();

            Employee aux = returnEmployee(key);

            if(Objects.isNull(aux)){
                System.out.println("Chave não encontrada.");
            }
            else {
                System.out.println("Descreva a tarefa: ");
                String job = scanner.next();
                aux.setJobs(key, job);
                System.out.println("Registrado");
            }
            menu();                                                  // <<-- VOLTANDO AO MENU PRINCIPAL
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Entrada inválida.");
        }
    }

    public static void login(){
        System.out.println("///// LOGIN /////");

        try{
            System.out.println("Chave");
            String key = scanner.next();
            System.out.println("Senha: ");
            String password = scanner.next();

            Employee aux = returnEmployee(key);
            if(Objects.isNull(aux))
                System.out.println("Chave invalida.");
            else {
                if (Manipulation.loginValidate(aux, key, password)) {
                    System.out.println("Olá, " + aux.getName());
                    screenLogin(aux);
                }
            }
            menu();
        } catch (InputMismatchException e){
            throw new InputMismatchException("Entrada inválida.");
        }
    }

    private static void screenLogin(Employee aux) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("/// Selecione ///");
        if(aux instanceof Manager){
            System.out.println("1. Registrar Atividade");
            System.out.println("2. Designar Time");
            System.out.println("3. Sair");
            System.out.println(" -> ");
            int c = scanner.nextInt();

            switch (c){
                case 1:
                    setJobs();
                case 2:
                    Manipulation.setTeam();
                case 3:
                    menu();
                default:
                    System.out.println("Entrada inválida.");
            }
        }

    }

    // MÉTODO DE BUSCA DE EMPREGADO
    public static Employee returnEmployee(String a) {
        for (Employee aux : employees) {
            if (aux.getKey().equals(a)) {
                return aux;
            }
        }
        return null;
    }

    private static void closeProgram() throws IOException {
        OutputStream fos = new FileOutputStream("dados.csv");
        Writer osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);

        for(Employee employee : employees){
            if(employee instanceof Manager){
                Manager aux = (Manager) employee;
                bw.write("" + employee.getName() + "," + employee.getKey() + "," + aux.getManagerKey() + "," + employee.getIncome());
            } else
              bw.write("" + employee.getName() + "," + employee.getKey() + "," + employee.getIncome());
            bw.newLine();
        }
        bw.close();
        exit(0);
    }
}
