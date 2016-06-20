package com.ge.predixlaunch.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.predixlaunch.dao.PredixLaunchDaoIfc;
import com.ge.predixlaunch.data.ProjectVO;

@Service
public class PredixLaunchServicesImpl implements PredixLaunchServiceIfc{
	
	@Autowired
	PredixLaunchDaoIfc predixLaunchDaoIfc;
	
	@Override
	public String saveProject(ProjectVO projectVo) {
	 return predixLaunchDaoIfc.saveProject(projectVo);
	}
	@Override
	public List<ProjectVO> getProjectData() {
		 return predixLaunchDaoIfc.getProjectData();
	}
	@Override
	public ProjectVO getProjectDocument(int projectId) {
		 return predixLaunchDaoIfc.getProjectDocument(projectId);
	}
	@Override
	public String deleteProject(int projectId) {
	 return predixLaunchDaoIfc.deleteProject(projectId);
	}
	@Override
	public String editProject(ProjectVO projectVo) {
		return predixLaunchDaoIfc.editProject(projectVo);
	}
}
