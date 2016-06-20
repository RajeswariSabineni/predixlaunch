package com.ge.predixlaunch.data;

import java.io.File;
import java.io.InputStream;
import java.io.PushbackInputStream;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
public class ProjectVO {
	
	
	private String url;
	private String title;
	private String imgSrc;
	private String projectName;
	private String projectDescription;
	private InputStream image;
	private String imageName;
	private String docName;
	private InputStream doc;
	private int projectId;
	private byte[] blobImage;
	
	



	public byte[] getBlobImage() {
		return blobImage;
	}



	public void setBlobImage(byte[] blobImage) {
		this.blobImage = blobImage;
	}



	public int getProjectId() {
		return projectId;
	}



	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}



	public InputStream getImage() {
		return image;
	}



	public void setImage(InputStream image) {
		this.image = image;
	}



	public String getImageName() {
		return imageName;
	}



	public void setImageName(String imageName) {
		this.imageName = imageName;
	}



	public String getDocName() {
		return docName;
	}



	public void setDocName(String docName) {
		this.docName = docName;
	}



	public InputStream getDoc() {
		return doc;
	}



	public void setDoc(InputStream doc) {
		this.doc = doc;
	}



	public ProjectVO() {
		
	}
	


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	

}
