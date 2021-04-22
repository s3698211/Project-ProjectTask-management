package myapp.springstarter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.ProjectingArgumentResolverRegistrar;
import org.springframework.stereotype.Service;

import myapp.springstarter.domain.Backlog;
import myapp.springstarter.domain.Project;
import myapp.springstarter.domain.User;
import myapp.springstarter.exceptions.ProjectIDExceptionresponse;
import myapp.springstarter.exceptions.ProjectIdException;
import myapp.springstarter.exceptions.ProjectNotFoundException;
import myapp.springstarter.repositories.BacklogRespositories;
import myapp.springstarter.repositories.ProjectRepository;
import myapp.springstarter.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRespositories backlogRespository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Project saveOrUpdateProject(Project project, String username) {
		
		if(project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account cant update");
			} 
			else if(existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because it doesn't exist");
			}
			
		} 
		
		try {
			//relationship
			User user  = userRepository.findByUsername(username);			
			project.setUser(user);
			project.setProjectLeader(username);			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			//create a backlog when a new project is created
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
				
			}
			
			if(project.getId() !=null)    {
				project.setBacklog(backlogRespository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			
			return projectRepository.save(project);
		}catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()+"' already exists");
		}		
		
	}
	
	public Project findProjectByIdentifier(String projectId, String username) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if (project == null) {
			throw new ProjectIdException("'Project ID '" + projectId+"' does not exist");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public void deleteProjectByIdentifier(String projectId, String username) {
		projectRepository.delete(findProjectByIdentifier(projectId, username));
	}
	
}
