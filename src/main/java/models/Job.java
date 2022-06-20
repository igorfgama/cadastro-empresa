package models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Job {
    protected Map<String, String> jobs = new HashMap<>();

    public void setJobs(String login, String job) throws IOException {
        jobs.put(login, job);
        saveJobs(login, job);
    }

    private void saveJobs(String login, String job) throws IOException {
        OutputStream fos = new FileOutputStream("jobs.csv");
        Writer osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(login + "," + job);
        bw.newLine();
        bw.close();
    }

    public void loadJobs() throws FileNotFoundException {
        Scanner scannerFile = new Scanner(new File("jobs.csv"));
        while(scannerFile.hasNextLine()){
            String line = scannerFile.nextLine();
            Scanner readLine = new Scanner(line);
            readLine.useDelimiter(",");

            String key = readLine.next();
            String value = readLine.next();

            jobs.put(key, value);

            readLine.close();
        }
    }

    public void getJobs() throws FileNotFoundException {
        Scanner scannerFile = new Scanner(new File("jobs.csv"));
        while(scannerFile.hasNextLine()){
            String line = scannerFile.nextLine();
            Scanner readLine = new Scanner(line);
            readLine.useDelimiter(",");

            String key = readLine.next();
            String value = readLine.next();

            System.out.println("[" + key + ", " + value + "]");
        }
    }
}
