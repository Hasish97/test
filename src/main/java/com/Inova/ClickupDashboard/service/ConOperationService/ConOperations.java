package com.Inova.ClickupDashboard.service.ConOperationService;

import com.Inova.ClickupDashboard.entity.*;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectFolders.Con_Categories;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectFolders.Con_FolderSharedMembers;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectFolders.Con_ProjectFolders;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectFolders.Con_Subcategories;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectLIstTasks.Con_Assignees;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectLIstTasks.Con_ProjectTasks;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectLIstTasks.Con_Tasks;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers.Con_MemberP;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers.Con_ProjectMembers;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers.Con_Projects;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectSubTasks.Con_SubTaskArray;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectSubTasks.Con_subtasks;
import com.Inova.ClickupDashboard.dtos.ForConOperations.TaskTimeTrackedDetails.Con_Interval;
import com.Inova.ClickupDashboard.dtos.ForConOperations.TaskTimeTrackedDetails.Con_MainTime;
import com.Inova.ClickupDashboard.dtos.ForConOperations.TaskTimeTrackedDetails.Con_data;
import com.Inova.ClickupDashboard.repository.*;
import com.Inova.ClickupDashboard.utils.CalendarUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.mortbay.log.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import static com.google.gson.internal.bind.util.ISO8601Utils.format;

@Service
public class ConOperations {
    @Value("${include_teams}")
    private  String include_teams;
    @Value("${recaptchaV3}")
    private  String  recaptchaV3;
    @Value("${CLICKUP.username}")
    private  String Clickupusername;
    @Value("${CLICKUP.password}")
    private  String Clickuppassword;

    @Value("${clickup_host}")
    private  String clickup_host;
    @Autowired
    Gson gson;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectTasksTrackedIntervalsRepository projectTasksTrackedIntervalsRepository;

    @Autowired
    ProjectTasksAssignerRepository projectTasksAssignerRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectListRepository projectListRepository;

    @Autowired
    ProjectFolderSharedMemberRepository projectFolderSharedMemberRepository;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    public String getToken(Boolean isAccessTokenNeed) throws IOException, InterruptedException, JSONException {

        var httpClient = HttpClient.newBuilder().build();

        HashMap<String, String> params = new HashMap<>();
        params.put("include_teams", include_teams);
        params.put("recaptchaV3", recaptchaV3);

        var query = params.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        var host = clickup_host;
        var pathname = "/v1/login";
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + pathname + '?' + query))
                .header("Authorization", getBasicAuthenticationHeader(Clickupusername, Clickuppassword))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject= new JSONObject(response.body());
        String token= (String) jsonObject.get("token");
//        RestTemplate restTemplate=new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+token);
        HttpEntity entity = new HttpEntity(headers);

        String result = restTemplate.postForObject(clickup_host+"/core/v1/devKey",entity,String.class);
        if(isAccessTokenNeed){
            return result;
        }else {
            return token;
        }
    }
    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
    public Con_ProjectMembers saveProjectsAndMembers() throws IOException, InterruptedException{

        var httpClient = HttpClient.newBuilder().build();

        HashMap<String, String> params = new HashMap<>();
        params.put("include_teams", include_teams);
        params.put("recaptchaV3", recaptchaV3);

        var query = params.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        var host = clickup_host;
        var pathname = "/v1/login";
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + pathname + '?' + query))
                .header("Authorization", getBasicAuthenticationHeader(Clickupusername, Clickuppassword))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Con_ProjectMembers conProjectMembers = gson.fromJson(response.body(), Con_ProjectMembers.class);
        Organization organization=new Organization();
        organization.setId(conProjectMembers.getTeams().get(0).getId());
        organization.setName(conProjectMembers.getTeams().get(0).getName());
        organizationRepository.saveAndFlush(organization);
        for(Con_Projects conProjects : conProjectMembers.getTeams().get(0).getProjects()){
            Date date = new Date();
            Project project=new Project();
            project.setId(conProjects.getId());
            project.setName(conProjects.getName());
            project.setDatecreated(conProjects.getDate_created());
            project.setOrganization(organizationRepository.getOne(conProjects.getTeam().getId()));
            project.setModifiedDate(date);
            projectRepository.save(project);
            Log.info("The project {} insert to the table", conProjects.getId());
        }

        for(Con_MemberP memberP : conProjectMembers.getTeams().get(0).getMembers()){
           Member member=new Member();
           Date date = new Date();
           member.setId(memberP.getUser().getId());
           member.setEmail(memberP.getUser().getEmail());
           member.setName(memberP.getUser().getUsername());
           member.setModifiedDate(date);
           member.setOrganization(organizationRepository.getOne(memberP.getOrganization_id()));
           memberRepository.saveAndFlush(member);
           Log.info("The memberP {} insert to the table",memberP.getUser().getId());
        }
        Log.info("thread 1 start");

        return conProjectMembers;
    }
    public List<Project> getProjectFoldersAndList(){

        final List<Project> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                               \n");
        SQL.append("FROM project ");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<Project>>() {

            @Nullable
            @Override
            public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Log.info("started the entering project folder details");
                while (rs.next()){
              Project project=new Project();
              project.setId(rs.getNString("id"));
              result.add(project);
           }
               for (Project project:result){
//                   RestTemplate restTemplate=new RestTemplate();
                   HttpHeaders headers = new HttpHeaders();
                   headers.setContentType(MediaType.APPLICATION_JSON);
                   try {
                       headers.set("Authorization", "Bearer "+getToken(false));
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }

                   Map<String, String> map = new HashMap<>();
                   map.put("id",project.getId());
                   HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                   ResponseEntity<String> response = restTemplate.exchange(
                           clickup_host+"/hierarchy/v1/project/{id}/category", HttpMethod.GET, requestEntity, String.class, map);

                   Con_ProjectFolders projectfolder = gson.fromJson(response.getBody(), Con_ProjectFolders.class);

                   ProjectFolder projectfolder1=new ProjectFolder();

                   for(Con_Categories con_projectFolders:projectfolder.getCategories())
                   {
                       Date date = new Date();
                       Log.info("entered folder details for project {}",con_projectFolders.getProject_id());
                   projectfolder1.setId(con_projectFolders.getId());
                   projectfolder1.setName(con_projectFolders.getName());
                   projectfolder1.setModifiedDate(date);
                   projectfolder1.setProject(projectRepository.getProjectById(con_projectFolders.getProject_id()));
                   folderRepository.saveAndFlush(projectfolder1);

                       ProjectList projectlist=new ProjectList();
                       for (Con_Subcategories con_subcategories:con_projectFolders.getSubcategories()){
                           Date date1 = new Date();
                           projectlist.setId(con_subcategories.getId());
                           projectlist.setName(con_subcategories.getName());
                           projectlist.setProjectFolder(folderRepository.getProjectFolderById(con_projectFolders.getId()));
                           projectlist.setModifiedDate(date1);
                           projectListRepository.saveAndFlush(projectlist);
                           Log.info("project list details entered for project {}",con_subcategories.getProject_id());
                       }

                       ProjectFolderSharedmember projectfoldersharedmembers=new ProjectFolderSharedmember();
                       for(Con_FolderSharedMembers user:con_projectFolders.getMembers()){

                           Date date2 = new Date();
                           if(projectFolderSharedMemberRepository.getProjectFolderSharedmemberByProjectFolderAndMember(folderRepository.getProjectFolderById(con_projectFolders.getId()),memberRepository.getMemberById(user.getUser().getId()))!=null){
                               projectfoldersharedmembers=projectFolderSharedMemberRepository.getProjectFolderSharedmemberByProjectFolderAndMember(folderRepository.getProjectFolderById(con_projectFolders.getId()),memberRepository.getMemberById(user.getUser().getId()));
                               projectfoldersharedmembers.setProjectFolder(folderRepository.getProjectFolderById(con_projectFolders.getId()));
                               projectfoldersharedmembers.setMember(memberRepository.getMemberById(user.getUser().getId()));
                           }else {
                               projectfoldersharedmembers.setMember(memberRepository.getMemberById(user.getUser().getId()));
                               projectfoldersharedmembers.setProjectFolder(folderRepository.getProjectFolderById(con_projectFolders.getId()));
                           }
                           projectfoldersharedmembers.setModifiedDate(date2);
                           projectFolderSharedMemberRepository.saveAndFlush(projectfoldersharedmembers);
                           Log.info("enter the project folder shared member details {}",con_projectFolders.getId());

                       }

                   }

               }
                return null;
            }

        });
    }
    public List<ProjectList> getProjectListTasksAndAssigners(){
        final List<ProjectList> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                               \n");
        SQL.append("FROM project_list");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectList>>() {

            @Nullable
            @Override
            public List<ProjectList> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()){
                    ProjectList projectlist=new ProjectList();
                     projectlist.setId(rs.getNString("id"));
                    result.add(projectlist);
                }

                for (ProjectList projectlist:result){

                    var host = clickup_host;
                    var listId = projectlist.getId();
                    var pathname = "/api/v2/list/" + listId + "/task";
                    HttpRequest request = null;

                    String token1= null;
                    try {
                        token1 = getToken(true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String token2=token1.substring(7,token1.length());
                    String token=token2.substring(1,token2.length()-2);
                        request = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(host + pathname))
                                .header("Content-Type", "application/json")
                                .header("Authorization", token)
                                .build();


                    HttpResponse<String> response = null;
                    var httpClient = HttpClient.newBuilder().build();
                    try {
                        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Con_ProjectTasks conProjectTasks = gson.fromJson(response.body(), Con_ProjectTasks.class);

                    ProjectTask projecttask=new ProjectTask();
                    ProjectTaskAssigner projectTaskAssigner=new ProjectTaskAssigner();
                    if(conProjectTasks.getTasks()!=null){
                    for (Con_Tasks conProjectTasks1:conProjectTasks.getTasks()){
                        Date date=new Date();

                        projecttask.setId(conProjectTasks1.getId());
                        projecttask.setDatecreated(conProjectTasks1.getDate_created());

                        long date_created= Long.parseLong(conProjectTasks1.getDate_created());
                        double date_created1=date_created/1000;
                        java.util.Date time=new java.util.Date((long) date_created1*1000);
                        Date date_created2 = Date.from(time.toInstant());
                        Timestamp date_created3=new Timestamp(date_created2.getTime());

                        projecttask.setUtpdatecreated(date_created3);
                        projecttask.setDuedate(conProjectTasks1.getDue_date());

                        long due_date= Long.parseLong(conProjectTasks1.getDate_created());
                        double due_date1=due_date/1000;
                        java.util.Date time6=new java.util.Date((long) due_date1*1000);
                        Date due_date2 = Date.from(time.toInstant());
                        Timestamp due_date3=new Timestamp(due_date2.getTime());
                        projecttask.setUtpdatecreated(due_date3);

                        projecttask.setLevel(1);
                        projecttask.setModifiedDate(date);
                        projecttask.setName(conProjectTasks1.getName());
                        projecttask.setParendtadkid("0");
                        projecttask.setStatus(conProjectTasks1.getStatus().getStatus());
                        projecttask.setProjectList(projectListRepository.getById(projectlist.getId()));
                        projecttask.setTime_spent(conProjectTasks1.getTime_spent());
                        projecttask.setProject(projectRepository.getProjectById(conProjectTasks1.getSpace().getId()));
                        taskRepository.saveAndFlush(projecttask);
                        Log.info("task details enterd for task {}",conProjectTasks1.getId());

                        for(Con_Assignees con_assignees:conProjectTasks1.getAssignees()){
                            if(projectTasksAssignerRepository.getProjectTaskAssignerByProjectTaskAndMember(taskRepository.getById(conProjectTasks1.getId()),memberRepository.getMemberById(con_assignees.getId()))!=null)
                            {
                                projectTaskAssigner=projectTasksAssignerRepository.getProjectTaskAssignerByProjectTaskAndMember(taskRepository.getById(conProjectTasks1.getId()),memberRepository.getMemberById(con_assignees.getId()));
                            }
                            projectTaskAssigner.setMember(memberRepository.getMemberById(con_assignees.getId()));
                            projectTaskAssigner.setProjectTask(taskRepository.getById(conProjectTasks1.getId()));
                            projectTasksAssignerRepository.saveAndFlush(projectTaskAssigner);
                            Log.info("task assigners added for task {}",conProjectTasks1.getId());
                        }

                    }}else {
                        Log.info("there are no tasks associate with that list");
                    }


                }
                Log.info("end of the thread adding main task details");
                return null;
            }
        });
    }
    public List<ProjectTask> getTrackedTimeIntervalsForTasks(){

        final List<ProjectTask> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                               \n");
        SQL.append("FROM project_task");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectTask>>() {

            @Nullable
            @Override
            public List<ProjectTask> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()){
                    ProjectTask projecttask=new ProjectTask();
                    projecttask.setId(rs.getNString("id"));
                    result.add(projecttask);
                }

                for (ProjectTask projecttask:result){

                    var httpClient = HttpClient.newBuilder().build();

                    HashMap<String, String> params = new HashMap<>();
                    params.put("custom_task_ids", "true");
                    params.put("team_id","37287189");

                    var query = params.keySet().stream()
                            .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                            .collect(Collectors.joining("&"));

                    var host = clickup_host;
                    var taskId = projecttask.getId();
                    var pathname = "/api/v2/task/" + taskId + "/time";
                    String token1=null;
                    try {
                        token1 = getToken(true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String token2=token1.substring(7,token1.length());
                    String token=token2.substring(1,token2.length()-2);
                    var request = HttpRequest.newBuilder()
                            .GET()
                            .uri(URI.create(host + pathname + '?' + query))
                            .header("Content-Type", "String")
                            .header("Authorization", token)
                            .build();

                    HttpResponse<String> response = null;
                    try {
                        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(response!=null){
                    Con_MainTime con_mainTime = gson.fromJson(response.body(), Con_MainTime.class);
                    ProjectTrackIntervel projectTrackIntervel=new ProjectTrackIntervel();
                    if(con_mainTime.getData()!=null){
                    for(Con_data con_data:con_mainTime.getData()){
                        if(con_data!=null){
                        for(Con_Interval con_interval:con_data.getIntervals()){
                            if(con_interval!=null){
                            projectTrackIntervel.setProjectTask(taskRepository.getById(projecttask.getId()));
                            projectTrackIntervel.setDateadded(con_interval.getStart());
                                long time2= Long.parseLong(con_interval.getStart());
                                double time3=time2/1000;
                                java.util.Date time=new java.util.Date((long) time3*1000);
                                Date date = Date.from(time.toInstant());
                                Timestamp ts=new Timestamp(date.getTime());

                            projectTrackIntervel.setUtpdateadded(ts);
                            projectTrackIntervel.setId(con_interval.getId());
                            projectTrackIntervel.setTracktime(con_interval.getTime());
                            projectTrackIntervel.setUtptracktime(CalendarUtil.milisocondsToHours(con_interval.getTime()));
                            Date date1=new Date();
                            projectTrackIntervel.setMember(memberRepository.getMemberById(con_data.getUser().getId()));
                            projectTrackIntervel.setModified_date(date1);
                            projectTasksTrackedIntervalsRepository.saveAndFlush(projectTrackIntervel);
                            Log.info("added tracked time intervals details for task {}",projecttask.getId());
                        }}}
                    }}
                }}
                return null;
            }
        });
    }
    public List<ProjectTask> getSubtasksForMainTasks() {

        final List<ProjectTask> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                               \n");
        SQL.append("FROM project_task WHERE level=1");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectTask>>() {

            @Nullable
            @Override
            public List<ProjectTask> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()){
                    ProjectTask projecttask=new ProjectTask();
                    projecttask.setId(rs.getNString("id"));
                    result.add(projecttask);
                }

                for (ProjectTask projecttask:result){

                    Log.info("getting subtasks for task {}",projecttask.getId());
                    Log.info(String.valueOf(projecttask));
                      getSubtaskRecursively(projecttask.getId(),2);
                    }

                Log.info("-----------------------------------------------------------------------------------------------");
                return null;
            }

        });
    }
    public String getSubtaskRecursively(String projecttask,int level){
//        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            headers.set("Authorization", "Bearer "+getToken(false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> map = new HashMap<>();
        map.put("id",projecttask);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response=null;
        try {
             response = restTemplate.exchange(
                    clickup_host+"/tasks/v1/task/{id}?include_groups=true&field_backlinks=true&" +
                            "fields%5B%5D=content&fields%5B%5D=assignees&fields%5B%5D=parent_task&fields%5B%5D=" +
                            "subtask_parent_task&fields%5B%5D=totalTimeSpent&fields%5B%5D=subtasks&fields%5B%5D=" +
                            "position&fields%5B%5D=customFields&fields%5B%5D=members&fields%5B%5D=rolledUpTimeSp" +
                            "ent&fields%5B%5D=rolledUpTimeEstimate&fields%5B%5D=rolledUpPoint" +
                            "sEstimate&fields%5B%5D=linkedTasks&clear_nested_subtasks=true", HttpMethod.GET, requestEntity, String.class, map);
            } catch (Exception e){
            Log.info("the task can not be find");
        }

        if(response!=null){
        Con_SubTaskArray con_subTaskArray = gson.fromJson(response.getBody(), Con_SubTaskArray.class);
        ProjectTask projectTask=new ProjectTask();

        if(con_subTaskArray.getSubtasks()!=null){
        for(Con_subtasks con_subtasks:con_subTaskArray.getSubtasks()){
            Date date=new Date();
            projectTask.setId(con_subtasks.getId());
            projectTask.setTime_spent(con_subtasks.getTime_spent());
            projectTask.setStatus(con_subtasks.getStatus().getStatus());
            projectTask.setDatecreated(con_subtasks.getDate_created());
            projectTask.setDuedate(con_subtasks.getDue_date());
            projectTask.setModifiedDate(date);
            projectTask.setParendtadkid(con_subtasks.getParent());
            projectTask.setName(con_subtasks.getName());
            projectTask.setProject(projectRepository.getProjectById(con_subTaskArray.getProject_id()));
            projectTask.setLevel(level);
            taskRepository.saveAndFlush(projectTask);

            ProjectTaskAssigner projectTaskAssigner=new ProjectTaskAssigner();
            for(Con_Assignees con_assignees:con_subtasks.getAssignees()){
                if(projectTasksAssignerRepository.getProjectTaskAssignerByProjectTaskAndMember(taskRepository.getById(con_subtasks.getId()),memberRepository.getMemberById(con_assignees.getId()))!=null)
                {
                    projectTaskAssigner=projectTasksAssignerRepository.getProjectTaskAssignerByProjectTaskAndMember(taskRepository.getById(con_subtasks.getId()),memberRepository.getMemberById(con_assignees.getId()));
                }
                projectTaskAssigner.setMember(memberRepository.getMemberById(con_assignees.getId()));
                projectTaskAssigner.setProjectTask(taskRepository.getById(con_subtasks.getId()));
                projectTasksAssignerRepository.saveAndFlush(projectTaskAssigner);
                Log.info("task assigners added for sub task {}",con_subtasks.getId());

            }
            if(con_subtasks.getSubtasks_count()!=0){
                Log.info("getting subtask for the sub task{}",con_subtasks.getId());
                getSubtaskRecursively(con_subtasks.getId(),++level);
            }
        }}}
        return projecttask;
    }
}
