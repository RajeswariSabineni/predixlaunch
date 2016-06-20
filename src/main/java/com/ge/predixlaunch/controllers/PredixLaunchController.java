package com.ge.predixlaunch.controllers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ge.predixlaunch.dao.PredixLaunchDaoIfc;
import com.ge.predixlaunch.data.ProjectVO;
import com.ge.predixlaunch.services.PredixLaunchServiceIfc;
import com.ge.predixlaunch.utils.PredixLaunchConstants;



@RestController
@RequestMapping("/springBoot")
public class PredixLaunchController {
	
	@Autowired
	private PredixLaunchServiceIfc predixLaunchServiceIfc;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PredixLaunchController.class);
	
	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public @ResponseBody String saveProject(@RequestParam(name="file" , required=false) MultipartFile uploadfile, @RequestParam(name="docfile", required=false) MultipartFile docFile,  @RequestParam("url") String url,@RequestParam("title") String title,
			@RequestParam("projectName") String projectName,@RequestParam("projectDescription") String projectDescription, 
			@RequestParam(name="action", required=false) String action,
			@RequestParam(name="projectId", required=false) String projectId, HttpServletRequest request){	
		String returnVal = "fail";
		try{
			LOGGER.info("...save project..");
			ProjectVO projectvo = new ProjectVO();
			projectvo.setUrl(url);
			projectvo.setProjectDescription(projectDescription);
			projectvo.setTitle(title);
			projectvo.setImageName(uploadfile!=null ? uploadfile.getOriginalFilename():"");
			projectvo.setProjectName(projectName); 
			projectvo.setImage(uploadfile!=null ? uploadfile.getInputStream():null);
			projectvo.setDocName(docFile!=null ? docFile.getOriginalFilename():"");
			projectvo.setDoc(docFile!=null ? docFile.getInputStream():null);
			LOGGER.info(projectvo.getUrl()+ projectvo.getTitle()+projectvo.getImgSrc()+projectvo.getProjectDescription());
			if(action !=null && action.equalsIgnoreCase("edit")) {
				projectvo.setProjectId(Integer.parseInt(projectId));
				returnVal = predixLaunchServiceIfc.editProject(projectvo);
			} else {
			   returnVal = predixLaunchServiceIfc.saveProject(projectvo);
			   }
			
	      }  catch (Exception e) {
	    	  return "{ \"response\" : \"" + "fail"+ "\" }";
		  }
		return "{ \"response\" : \"" + returnVal+ "\" }";
	}
	
	
	@RequestMapping(value = "/editProject", method = RequestMethod.POST)
	public @ResponseBody String saveProject(@RequestBody ProjectVO projectvo){
		LOGGER.info(":::::::Edit project ::::::::::::: [START]");
		String returnVal = "fail";
		try{
			if(projectvo !=null && projectvo.getImgSrc()!=null) {
				projectvo.setImgSrc(PredixLaunchConstants.FILE_UPLOAD_PARENT_PATH+projectvo.getImgSrc()) ;
			}
					returnVal = predixLaunchServiceIfc.saveProject(projectvo);
					LOGGER.info(":::::::end project ::::::::::::: [END]"+returnVal); 
		} catch(Exception e){
			e.printStackTrace();
		}
		return "{ \"response\" : \"" +returnVal+ "\" }";
	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.GET)
	 public String deleteProject(@RequestParam("projectId") int projectId){
		LOGGER.info("deleteProject");
		String returnVal="fail";
			try{
				returnVal = predixLaunchServiceIfc.deleteProject(projectId);
			//	LOGGER.info("projectlist::::::Size()::::::" + projectlist.size()); 
			} catch(Exception e){
				e.printStackTrace();
			}
			
			return "{ \"response\" : \"" +returnVal+ "\" }";
	 }

	
	@RequestMapping(value = "/getProjectData", method = RequestMethod.GET)
	 public List<ProjectVO> getProjectData(){
		 List<ProjectVO> projectlist = null;
			try{
				projectlist = predixLaunchServiceIfc.getProjectData();
				LOGGER.info("projectlist size:" + projectlist.size()); 
			} catch(Exception e){
				e.printStackTrace();
			}
			return projectlist;
	 }
	
	@RequestMapping(value="/pocdocumentdownload", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<InputStreamResource>  pocDocumentDownload(@RequestParam("projectId") String projectId,HttpServletResponse response) {

		// Get your file stream from wherever.
		try{
			//@RequestParam("projectId") int projectId
			//int projectId =26;
			
			LOGGER.info("project id-->"+ projectId);
			ProjectVO projectvo = predixLaunchServiceIfc.getProjectDocument(Integer.parseInt(projectId));
			response.addHeader("Content-disposition", "attachment;filename="+projectvo.getDocName());
			
			if (projectvo.getDocName().indexOf(".doc") != -1){
				LOGGER.info("entered doc cond");
				response.setContentType("application/msword");
			}else if (projectvo.getDocName().indexOf(".pdf") != -1){
				response.setContentType("application/pdf");
			}
			else if (projectvo.getDocName().indexOf(".txt") != -1){
				response.setContentType("txt/plain");
			}else {
				response.setContentType("image/jpeg");
			}
		 return ResponseEntity.ok().body(new InputStreamResource(projectvo.getDoc()));
	
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
			}
	

}
