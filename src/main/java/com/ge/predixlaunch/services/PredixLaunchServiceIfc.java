package com.ge.predixlaunch.services;

import java.util.List;

import com.ge.predixlaunch.data.ProjectVO;

public interface PredixLaunchServiceIfc {
	
	public String saveProject(ProjectVO projectVo);
	public List<ProjectVO> getProjectData();
	public ProjectVO getProjectDocument(int projectId);
	public String deleteProject(int projectId);
	public String editProject(ProjectVO projectVo);
}
