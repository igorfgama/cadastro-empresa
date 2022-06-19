package services;

import models.Analyst;
import models.Employee;
import models.Job;
import models.Manager;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

public class MenuApplications {
    static Scanner scanner = new Scanner(System.in);
    static List<Employee> employees = new ArrayList<>();

    public static void init() throws FileNotFoundException {
        loadFile();
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
                    case 1 -> employees.add(getAnalyst());
                    case 2 -> employees.add(getManager());
                    case 3 -> getRegister();
                    case 4 -> getList();
                    case 5 -> recordJobs();
                    case 6 -> login();
                    case 7 -> closeProgram();
                    case 8 -> new Job().getJobs();
                    default -> System.out.println("Entrada inválida.");
                }
            } catch (InputMismatchException | IOException e) {
                throw new InputMismatchException("Entrada inválida.");
            }
        }
    }

    private static void loadFile() throws FileNotFoundException {
        Scanner scannerFile = new Scanner(new File("dados.csv"));
        while(scannerFile.hasNextLine()){
            String line = scannerFile.nextLine();
            Scanner readLine = new Scanner(line);
            readLine.useDelimiter(",");

            String name = readLine.next();
            String key = readLine.next();

            employees.add(new Analyst(name, key));

            readLine.close();
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

    public static void getList() {
        employees.forEach(employee -> System.out.println(employee.getLogin()));

//        employees.sort(Comparator.comparing(Employee::getName));
    }

    private static void recordJobs() {
        System.out.println("*** ADICIONANDO TRABALHOS **");

        try {
            System.out.println("Chave da conta: ");
            String login = scanner.next();

            Employee aux = returnEmployee(login);

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
            menu();                                                  // <<-- VOLTANDO AO MENU PRINCIPAL
        } catch (RuntimeException | IOException e) {
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
                    System.out.println("Olá, " + aux.getLogin());
                    screenLogin(aux);
                }
            }
            menu();
        } catch (InputMismatchException | FileNotFoundException e){
            throw new InputMismatchException("Entrada inválida.");
        }
    }

    private static void screenLogin(Employee aux) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("/// Selecione ///");
        if(aux instanceof Manager){
            System.out.println("1. Registrar Atividade");
            System.out.println("2. Designar Time");
            System.out.println("3. Sair");
            System.out.println(" -> ");
            int c = scanner.nextInt();

            switch (c){
                case 1 -> recordJobs();
                case 2 -> Manipulation.setTeam();
                case 3 -> menu();
                default -> System.out.println("Entrada inválida.");
            }
        }
    }

    // MÉTODO DE BUSCA DE EMPREGADO
    public static Employee returnEmployee(String a) {
        for (Employee aux : employees) {
            if (aux.getLogin().equals(a)) {
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
            bw.write("" + employee.getLogin() + "," + employee.getPassword());
            bw.newLine();
        }

        bw.close();
        exit(0);
    }
}
