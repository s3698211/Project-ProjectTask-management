package myapp.springstarter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import myapp.springstarter.domain.Backlog;
import myapp.springstarter.domain.Project;
import myapp.springstarter.domain.ProjectTask;
import myapp.springstarter.exceptions.ProjectNotFoundException;
import myapp.springstarter.repositories.BacklogRespositories;
import myapp.springstarter.repositories.ProjectRepository;
import myapp.springstarter.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRespositories backlogRespositories;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		
		
		
			//PTs to be added to a specific project, project != null, BkLog exists
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
			
			
			//set the BkLog to pt
			projectTask.setBacklog(backlog);
			// we want our project sequence to be like : IDPRO-1, IDPRO-2 .... 100 101
			Integer BacklogSeQuence = backlog.getPTSequence();
			//Update the Bklog Sequence
			BacklogSeQuence++;
			backlog.setPTSequence(BacklogSeQuence);
			
			//Add Sequence to projectTask
			projectTask.setProjectSequence(projectIdentifier+ "-"+ BacklogSeQuence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//INITIAL priority when priority null
			if(projectTask.getPriority()== null || projectTask.getPriority() == 0) {
				projectTask.setPriority(3);
			}
			//INITIAL status when status null
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);
		
		
	}

	public Iterable<ProjectTask> findBackLogById(String id, String username) {
		
		projectService.findProjectByIdentifier(id, username);
		
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
		//Make sure we are searching on an existing backlog
		projectService.findProjectByIdentifier(backlog_id, username).getBacklog();
		
		//Make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task with id " +pt_id+ " does not exist" );
		}
		//Make sure that the backlog/project id in the path corresponding to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project task " + pt_id + " does not exist in project: "+  backlog_id);
		}
		
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
		//find the projectTask
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		
		//Update the task
		projectTask = updatedTask;
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		
//		Backlog backlog = projectTask.getBacklog();
//		List<ProjectTask> pts = backlog.getProjectTasks();
//		pts.remove(projectTask);
//		backlogRespositories.save(backlog);
		
		projectTaskRepository.delete(projectTask);
	}
	
}
