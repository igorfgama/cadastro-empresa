package services;

import models.Analyst;
import models.Employee;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.exit;
import static services.MenuApplications.employees;

public class FilesService {
    static void loadFile() throws FileNotFoundException {
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

    public static void closeProgram() throws IOException {
        OutputStream fos = new FileOutputStream("dados.csv");
        Writer osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);

        for(Employee employee : employees){
            bw.write(employee.getLogin() + "," + employee.getPassword());
            bw.newLine();
        }

        bw.close();
        exit(0);
    }
}
