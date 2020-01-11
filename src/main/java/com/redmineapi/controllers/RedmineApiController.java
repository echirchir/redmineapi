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
import com.taskadapter.redmineapi.bean.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/users")
    public ResponseEntity<List<User>> users(){

        manager.setObjectsPerPage(100);

        List<User> users = new ArrayList<>();

        try {

            users = manager.getUserManager().getUsers();

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){

        manager.setObjectsPerPage(100);

        User user = null;

        try {

            user = manager.getUserManager().getUserById(id);

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(user);
    }

    // GET ALL existing projects from the server visible to the authenticated user or public
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

    // GET  project from the server by project id
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {

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
    public ResponseEntity<List<Membership>> getProjectMembers(@PathVariable Integer id) {

        manager.setObjectsPerPage(100);

        List<Membership> members = null;

        try {

            members = manager.getProjectManager().getProjectMembers(id);

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(members);
    }

    //GET ALL TIME_ENTRIES for a single user (regardless of projects)
    @GetMapping("/time_entries/{user_id}")
    public ResponseEntity<List<TimeEntry>> getAllTimeEntriesByUserId(@PathVariable Integer user_id){

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

    //GET ALL TIME_ENTRIES for a single user for a single (given) project
    @GetMapping("/time_entries/{user_id}/{project_id}")
    public ResponseEntity<List<TimeEntry>> getTimeEntriesByUserIdAndByProjectId(@PathVariable Integer user_id, @PathVariable Integer project_id){

        TimeEntryManager timeEntryManager = manager.getTimeEntryManager();
        
        final Map<String, String> params = new HashMap<>();

        params.put("user_id", String.valueOf(user_id));
        params.put("project_id", String.valueOf(project_id));

        List<TimeEntry> elements = new ArrayList<>();

        try {

            elements = timeEntryManager.getTimeEntries(params).getResults();

        } catch (RedmineException e) {
            
            e.printStackTrace();
        }

        return ResponseEntity.ok(elements);

    }


    //GET TIME_ENTRIES for a single user (regardless of projects) for a given date range
    @GetMapping("/users/{user_id}/time_entries")
    public ResponseEntity<List<TimeEntry>> getTimeEntriesByUserIdByDateRange(@PathVariable Integer user_id, 
                                        @RequestParam("from") String from, @RequestParam("to") String to){

        TimeEntryManager timeEntryManager = manager.getTimeEntryManager();
        
        final Map<String, String> params = new HashMap<>();

        params.put("user_id", String.valueOf(user_id));
        params.put("from", from);
        params.put("to", to);

        List<TimeEntry> elements = new ArrayList<>();

        try {

            elements = timeEntryManager.getTimeEntries(params).getResults();

        } catch (RedmineException e) {
            
            e.printStackTrace();
        }

        return ResponseEntity.ok(elements);

    }

    @GetMapping("/projects/{id}/issues")
    public ResponseEntity<List<Issue>> getIssueById(@PathVariable Integer id) {

        manager.setObjectsPerPage(100);

        final Map<String, String> params = new HashMap<>();

        params.put("project_id", String.valueOf(id));

        List<Issue> issues = new ArrayList<>();

        try {

            issues = manager.getIssueManager().getIssues(params).getResults();

        } catch (RedmineException e) {
        
            e.printStackTrace();

        }

        return ResponseEntity.ok(issues);
    }

}