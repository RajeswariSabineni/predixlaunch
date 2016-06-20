package com.ge.predixlaunch.dao;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ge.predixlaunch.data.*;
import com.ge.predixlaunch.controllers.PredixLaunchController;
import com.ge.predixlaunch.dao.*;

import org.springframework.stereotype.Repository;
import com.ge.predixlaunch.utils.PredixLaunchConstants;





@Repository
public class PredixLaunchDao implements PredixLaunchDaoIfc{
	
	@Autowired
	private JdbcTemplate primaryJdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PredixLaunchDao.class);

	String DT_SCHEMA = PredixLaunchConstants.DT_SCHEMA;
	String PROJECT_DETAILS_TABLE = PredixLaunchConstants.PROJECT_DETAILS_TABLE;
	String url = PredixLaunchConstants.url;
	String  title = PredixLaunchConstants.title;
	String imageName = PredixLaunchConstants.imageName;
	String projectName = PredixLaunchConstants.projectName;
	String projectDescription = PredixLaunchConstants.projectDescription;
	String image = PredixLaunchConstants.image;
	String docname = PredixLaunchConstants.docname;
	String doc = PredixLaunchConstants.doc;
	String projectId = PredixLaunchConstants.projectId;
	
	public String saveProject(final ProjectVO projectVo) {
		LOGGER.info("------------Save Project Method dao");
		try{
		HashMap docMap = new HashMap();
		 HashMap imageMap = process(projectVo.getImage());
		final ByteArrayOutputStream imageStream =  (ByteArrayOutputStream)imageMap.get("byteoutputstream");
	    final   long imagelength = (long)imageMap.get("length");
	    LOGGER.info("first file read");	
	    if(projectVo.getDocName() != null && projectVo.getDocName().length()>0) {
	        docMap = process(projectVo.getDoc());
	       }
	    final ByteArrayOutputStream docStream = docMap.size()>0 && docMap.containsKey("byteoutputstream")?
	    		(ByteArrayOutputStream)docMap.get("byteoutputstream") :null;
        final long doclength = docMap.size()>0 && docMap.containsKey("length")?
        		 (long)docMap.get("length"):0;
        		 
        LOGGER.info("second file read");	
        String sql = "";
		if(doclength >0) {
				 sql = "INSERT INTO "+DT_SCHEMA+"."+PROJECT_DETAILS_TABLE+" ("+url+","+title+","+imageName+","+projectName+","+projectDescription+","+image+","+docname+","+doc+") "
				  + " VALUES " 
				  + " (?, ?, ? , ?, ?, ?, ? ,?)";
		 }else {
			  sql = "INSERT INTO "+DT_SCHEMA+"."+PROJECT_DETAILS_TABLE+" ("+url+","+title+","+imageName+","+projectName+","+projectDescription+","+image+") "
					  + " VALUES " 
					  + " (?, ?, ? , ?, ?, ?)";
		 }
		LOGGER.info("SQL Query-->" +sql);
	int cnt =	primaryJdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, projectVo.getUrl());
				ps.setString(2, projectVo.getTitle());
				ps.setString(3,  projectVo.getImageName());
				ps.setString(4, projectVo.getProjectName());
				ps.setString(5, projectVo.getProjectDescription());
				ps.setBinaryStream(6, new ByteArrayInputStream(imageStream.toByteArray()), imagelength);
				if(doclength >0) {
				ps.setString(7,  projectVo.getDocName());
				ps.setBinaryStream(8, new ByteArrayInputStream(docStream.toByteArray()), doclength);
				}
				
				
			}
		});
		
			LOGGER.info("insert count" + cnt);
		
			//int cnt = primaryJdbcTemplate.update(sql, projectVo.getUrl(), projectVo.getTitle(), projectVo.getImageName(), projectVo.getProjectName(), projectVo.getProjectDescription() , new ByteArrayi());
			if(cnt >0){
				return "success";
			}
			return "failure";
		} catch(Exception ex) {
		   LOGGER.info(ex.getMessage());
		   return "failure";
		}
	
	}
	
	public String editProject(final ProjectVO projectVo) {
		LOGGER.info("-----------Edit project----------------------------");
		try {
		 HashMap docMap = new HashMap();
		 HashMap imageMap = new HashMap();
		 
		 if(projectVo.getDocName() != null && projectVo.getDocName().length()>0) {
			  imageMap = process(projectVo.getImage());
		       }
		
		final ByteArrayOutputStream imageStream =  imageMap.size()>0 && imageMap.containsKey("byteoutputstream")?
				(ByteArrayOutputStream)imageMap.get("byteoutputstream"):null;
	    final   long imagelength = imageMap.size()>0 && imageMap.containsKey("length")? 
	    		          (long)imageMap.get("length"):0;
		 
	    if(projectVo.getDocName() != null && projectVo.getDocName().length()>0) {
	        docMap = process(projectVo.getDoc());
	       }
	    final ByteArrayOutputStream docStream = docMap.size()>0 && docMap.containsKey("byteoutputstream")?
	    		(ByteArrayOutputStream)docMap.get("byteoutputstream") :null;
        final long doclength = docMap.size()>0 && docMap.containsKey("length")?
        		 (long)docMap.get("length"):0;
        	
        String sql = "UPDATE "+DT_SCHEMA+"."+PROJECT_DETAILS_TABLE+" set "+url+"=?,"+title+"=?, "+projectName+"=?, "+ projectDescription+" =? ";
      
        if(imagelength >0) {
			 sql = sql + " ,"+ imageName+"=? , "+image+"=? ";
		 }
		if(doclength >0) {
				 sql = sql + " , "+docname+"=? , "+doc+"=? ";
				 		
		 }
		 sql = sql+" where "+projectId+"=? ";
	
	LOGGER.info("update sql-->"+sql);	 
	int cnt =	primaryJdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, projectVo.getUrl());
				ps.setString(2, projectVo.getTitle());
				ps.setString(3, projectVo.getProjectName());
				ps.setString(4, projectVo.getProjectDescription());
				
				if( !(imagelength >0 || doclength >0) ) {
				ps.setInt(5, projectVo.getProjectId());
				}
				else {
				if(imagelength >0){
				ps.setString(5,  projectVo.getImageName());
				ps.setBinaryStream(6, new ByteArrayInputStream(imageStream.toByteArray()), imagelength);
				}
				if(imagelength >0 && doclength >0) {
				ps.setString(7,  projectVo.getDocName());
				ps.setBinaryStream(8, new ByteArrayInputStream(docStream.toByteArray()), doclength);
				ps.setInt(9, projectVo.getProjectId());
				}else if(doclength >0) {
				ps.setString(5,  projectVo.getDocName());
				ps.setBinaryStream(6, new ByteArrayInputStream(docStream.toByteArray()), doclength);
				ps.setInt(7, projectVo.getProjectId());
				}else  {
					ps.setInt(7, projectVo.getProjectId());
				}
			}
				
				
			}
		});
		
			LOGGER.info("Update count" + cnt);
		
			//int cnt = primaryJdbcTemplate.update(sql, projectVo.getUrl(), projectVo.getTitle(), projectVo.getImageName(), projectVo.getProjectName(), projectVo.getProjectDescription() , new ByteArrayi());
			if(cnt >0){
				return "success";
			}
			return "failure";
		}catch(Exception ex) {
			LOGGER.info(ex.getMessage());
			return "failure";
		}
	}

	public List<ProjectVO> getProjectData(){
		LOGGER.info("get project data method in dao");
		List<ProjectVO> projList = new ArrayList<>();
		String qry = "SELECT * FROM "+DT_SCHEMA+"."+PROJECT_DETAILS_TABLE +"  ORDER BY "+projectId;
		projList = primaryJdbcTemplate.query(qry, new RowMapper<ProjectVO>(){

			@Override
			public ProjectVO mapRow(ResultSet rs, int arg1) throws SQLException {
				ProjectVO projectVo = new ProjectVO();
				projectVo.setProjectId(rs.getInt("projectid"));
				projectVo.setProjectName(rs.getString("projectName"));
				projectVo.setProjectDescription(rs.getString("projectDescription"));
				projectVo.setTitle(rs.getString("title"));
				projectVo.setImageName(rs.getString("imageName"));
				projectVo.setDocName(rs.getString("docName"));
				projectVo.setUrl(rs.getString("url"));
				projectVo.setImgSrc(rs.getString("imgSrc"));
				projectVo.setBlobImage(rs.getBytes("image"));
				
				return projectVo;
			}
		});
		
	//	LOGGER.info("data size is "+projList.size());
		return projList;
	}
	
	public String  deleteProject(int projId){
		String qry = "DELETE FROM "+DT_SCHEMA+"."+PROJECT_DETAILS_TABLE+" WHERE "+ projectId+" =" + projId;
		int cnt = primaryJdbcTemplate.update(qry);
		LOGGER.info("Project ID :"+projectId+" deleted count -->"+ cnt);
		if(cnt >0){
			return "success";
		}
		return "failure";

	}
	/* Get project document */
	public ProjectVO getProjectDocument(int projId){
		List<ProjectVO> projList = new ArrayList<>();
		String qry = "SELECT * FROM "+DT_SCHEMA+"."+PROJECT_DETAILS_TABLE+" where "+ projectId+" =" +projId;
		projList = primaryJdbcTemplate.query(qry, new RowMapper<ProjectVO>(){

			@Override
			public ProjectVO mapRow(ResultSet rs, int arg1) throws SQLException {
				ProjectVO projectVo = new ProjectVO();
				projectVo.setDocName(rs.getString("docName"));
			    byte[] bytes = rs.getBytes("doc");
				projectVo.setBlobImage(bytes);
				projectVo.setDoc(rs.getBinaryStream("doc"));
				
				return projectVo;
			}
		});
		return projList!=null && projList.size()>0 ? projList.get(0):null;
	}
	
	public  HashMap process(InputStream is) {
		HashMap map = new HashMap();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		  long len1 = 0;
	        byte[] buffer = new byte[0xFFFF];
	        try {
	        for (int len; (len = is.read(buffer)) != -1;) {
	            os.write(buffer, 0, len);
	            len1 = len1+ len;
	        }
	        } catch(Exception ex) {
	        	
	        }
	      map.put("length", len1);
	      map.put("byteoutputstream", os);
	       return map;
	}
}
