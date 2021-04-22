package myapp.springstarter.exceptions;

public class ProjectIDExceptionresponse {
	

	private String projectIdentifier;
	
	public ProjectIDExceptionresponse(String projectIdentifier) {
		
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	
	

}
