package models;

import java.util.HashMap;
import java.util.Map;

public class Job {
    protected Map<String, String> jobs = new HashMap<>();

    public void setJobs(String login, String job){
        jobs.put(login, job);
    }
}
