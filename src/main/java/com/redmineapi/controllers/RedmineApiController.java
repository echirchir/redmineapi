package com.redmineapi.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.TimeEntryManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Membership;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.TimeEntry;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RedmineApiController{

    private String API_KEY = "c886284e44fbec5b6a9c5326503809a339fa9974";
    private String URI = "http://localhost:8090/";

    private final RedmineManager manager = RedmineManagerFactory.createWithApiKey(URI, API_KEY);

    @GetMapping("/")
    public String home(){

        return "home";
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {

        manager.setObjectsPerPage(100);

        List<Project> projects = new ArrayList<>();

        try {

            projects = manager.getProjectManager().getProjects();

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/projects/{identifier}")
    public ResponseEntity<Project> getProjectByIdentifier(@PathVariable("idenfier") String idenfier) {


        manager.setObjectsPerPage(100);

        Project project = null;

        try {

            project = manager.getProjectManager().getProjectByKey(idenfier);

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(project);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Integer id) {


        manager.setObjectsPerPage(100);

        Project project = null;

        try {

            project = manager.getProjectManager().getProjectById(id);

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(project);
    }

    @GetMapping("/projects/{id}/members")
    public ResponseEntity<List<Membership>> getProjectMembers(@PathVariable("id") Integer id) {


        manager.setObjectsPerPage(100);

        List<Membership> members = null;

        try {

            members = manager.getProjectManager().getProjectMembers(id);

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(members);
    }

    @GetMapping("/issues/{project_key}")
    public ResponseEntity<List<Issue>> getAllIssues(@PathVariable("project_key") String project_key) {

        manager.setObjectsPerPage(100);

        List<Issue> issues = null;

        try {

            issues = manager.getIssueManager().getIssues(project_key, null);
            
        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(issues);
    }

    @GetMapping("/time_entries/{project_id}")
    public ResponseEntity<List<TimeEntry>> getTimeEntriesByProjectId(@PathVariable("project_id") Integer projectId){

        TimeEntryManager timeEntryManager = manager.getTimeEntryManager();
        
        final Map<String, String> params = new HashMap<>();

        params.put("project_id", String.valueOf(projectId));

        List<TimeEntry> timeEntries = null;

        try{

            timeEntries = timeEntryManager.getTimeEntries(params).getResults();

        }catch( RedmineException ex ){

            ex.printStackTrace();
        }


        return ResponseEntity.ok(timeEntries);


    }

    @GetMapping("/time_entries/{project_id}")
    public ResponseEntity<List<TimeEntry>> getTimeEntriesByUserId(@PathVariable("user_id") Integer user_id){

        TimeEntryManager timeEntryManager = manager.getTimeEntryManager();
        
        final Map<String, String> params = new HashMap<>();

        params.put("user_id", String.valueOf(user_id));

        List<TimeEntry> elements = new ArrayList<>();

        try {
            elements = timeEntryManager.getTimeEntries(params).getResults();
        } catch (RedmineException e) {
            
            e.printStackTrace();
        }

        return ResponseEntity.ok(elements);

        
    }


    @GetMapping("/issues/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable("id") Integer id) {

        RedmineManager manager = RedmineManagerFactory.createWithApiKey(URI, API_KEY);

        manager.setObjectsPerPage(100);

        Issue issue = null;

        try {

            issue = manager.getIssueManager().getIssueById(id);

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(issue);
    }

}