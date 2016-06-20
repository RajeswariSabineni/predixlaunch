(function(){
  'use strict';

  angular.module('predixApp', [])

  .controller('PredixAppController', function($scope, $http, restHttpService){
    this.projects = projects;
    $scope.services = services;
   
    this.genres = genres;
    this.showForm = false;
    //added by rajeswari
    $scope.poctitle ="";
    $scope.pocurl ="";
    $scope.pocdesc ="";
    $scope.projectName="";
    $scope.pocMessage=null;
    $scope.pocMessageClass="";
    $scope.projectSelId = "";
    $scope.imageName =""
    $scope.docName="";
    var urlPrefix = '/predixLaunch';
    
  //added by rajeswari
    restHttpService.getProjectData(function(response, responseData){
    	console.log('project data details here-->'+responseData.data);
    	 $scope.projects = responseData.data;
    	});
  
    $scope.clearPOC = function () {
    	console.log('reset function called');
    	    $scope.poctitle ="";
    	    $scope.pocurl ="";
    	    $scope.pocdesc ="";
    	    $scope.projectName="";
    	    $scope.pocMessage=null;
    	    $scope.imageName =""
    	    $scope.docName="";
    	    $scope.projectSelId = "";
    	    $scope.$apply();
    	   
    }
    $scope.populateProject = function () {
    //	clearPOC();
    	console.log('populateProject'+ $scope.projectSelId.projectId);
    	for(var i=0; i<$scope.projects.length; i++) {
    		console.log($scope.projects[i].projectId);
    		if($scope.projectSelId.projectId == $scope.projects[i].projectId) {
    			    $scope.poctitle =$scope.projects[i].title;
    			    $scope.pocurl =$scope.projects[i].url;
    			    $scope.pocdesc =$scope.projects[i].projectDescription;
    			    $scope.projectName=$scope.projects[i].projectName;
    			    $scope.imageName = $scope.projects[i].imageName;
    			    $scope.docName =$scope.projects[i].docName;
    			  
    		}
    	}
    }
    
    $scope.uploadedFile = function(element) {
    	 $scope.$apply(function($scope) {
    	   $scope.files = element.files;  
    	   console.log('files set'+element.files.length);
    	 });
    	}
    $scope.uploadedFile1 = function(element) {
   	 $scope.$apply(function($scope) {
   	   $scope.docfile = element.files;  
   	   console.log('files set in pdf'+element.files.length);
   	 });
   	}
    $scope.deletePoc = function() {
    	console.log('delete poc from scope');
    	
    	restHttpService.deletePoc($scope.projectSelId.projectId, 
    	    	   function(response, responseData) 
    	    			   {
    	    		   if(response == "success" &&  responseData.data.response == "success") {
    	    			   $scope.pocMessage ="Project deleted successfully.";
    	    			   $scope.pocMessageClass ="alert alert-success";
    	    		   }else {
    	    			   $scope.pocMessage ="Project is not deleted.";
    	    			   $scope.pocMessageClass ="alert alert-danger";
    	    		   }
    	    	    	  });
    }
    
    $scope.addPoc = function(param) {
    	var projectData = new FormData();
    	$scope.pocMessageClass ="alert alert-danger";
    	if(param !=null && param =='edit') {
    		projectData.append('action' , param);
    		projectData.append('projectId' , $scope.projectSelId.projectId);
    	}
    	//validations pending for url and uploaded documents.
    	if($scope.projectName == null || $scope.projectName =="") {
    		 $scope.pocMessage ="Please enter Project Name";
    		 return;
    	} else if($scope.poctitle == null || $scope.poctitle =="") {
   		 $scope.poctitle =$scope.projectName;
	     }
    	if($scope.pocurl.indexOf('http') == -1) {
    		$scope.pocurl ='http://' +$scope.pocurl;
    	}
    	//end
    	if($scope.files !=null) {
    		console.log($scope.files[0].type);
    		if($scope.files[0].type.indexOf('image') == -1) {
    			 $scope.pocMessage = "Please upload only image file for Project Image.";
    			 return;
    		}
    	projectData.append('file', $scope.files[0]);
    	}else if(param==null || !(param!=null && param == "edit")){
    		//projectData.append('file', Arrays.stream();
    		 $scope.pocMessage = "Please upload Project Image";
    		 return;
    	}
    	if($scope.docfile !=null) {
    		console.log($scope.docfile[0].type +" "+ $scope.docfile[0].type.indexOf('document'));
    		if( $scope.docfile[0].type.indexOf('word') == -1 && $scope.docfile[0].type.indexOf('document') == -1 && $scope.docfile[0].type.indexOf('pdf') == -1 ) {
    			 $scope.pocMessage = "Please upload only .doc/.docx/.pdf file for Project Document";
    			 return;
    		}
    		projectData.append('docfile', $scope.docfile[0]);
    	} 
    	projectData.append('url' , $scope.pocurl );
    	projectData.append('title' , $scope.poctitle);
    	projectData.append('projectName' , $scope.projectName );
    	projectData.append('projectDescription' , $scope.pocdesc);
    	
    	console.log('Save method starts');
    	
    	 restHttpService.uploadfile(projectData,  
    	   function(response, responseData) 
    			   {
    		   if(response == "success" && responseData.data.response == "success") {
    			   $scope.pocMessage ="POC saved successfully.";
    			   $scope.pocMessageClass ="alert alert-success";
    		   }else {
    			   $scope.pocMessage ="Some internal error while creating new POC.";
    		   }
    	    	  });
     	console.log('Save method ends');
    	}
    
    $scope.downLoad = function (projectId) {
    	console.log('downloading started' + projectId);
    	 restHttpService.pocDocumentDownload(projectId,
    	    	   function(response, responseData, status, headers, config) 
    	    			   {
    			       
    		 //console.log("download response-->" +responseData.data);
    	    		   if(response == "success") {
    	    			 //console.log("The data is ",data);
    	    			   console.log('download success enter')
    	    			   console.log(responseData.headers());
    	    	 	    	var data = responseData.data;
    	    	 	    	var type = responseData.headers('content-type').split(';');
    	    	 	    	var fileName = responseData.headers('content-disposition').split(';')[1].split('=')[1];
    	    	 	    	console.log(fileName);
    	    	 	    	console.log("type"+type[0]);
    	    	 	        var blob = new Blob([data], {type: type[0]});
    	    	 	        var objectUrl = URL.createObjectURL(blob);
    	    	 	        var a = document.createElement("a");
    	    	 	    	a.href = objectUrl;
    	    	 	        a.download = fileName;
    	    	 	        document.body.appendChild(a);
    	    	 	        a.click();
    	    	 			}else {
    	    		//	
    	    		   }
    	    	    	  });
    	    	}
    
    // end by rajeswari.
  })
  
 
.service('restHttpService', ['$http', function ($http) {
	 var urlPrefix = '/predixLaunch';
   console.log('rest http service--> add method called');
	 this.uploadfile = function(projectData ,  handler){
    var uploadUrl =urlPrefix + '/springBoot/saveProject';
    	$http.post(uploadUrl, projectData, 
    			{
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .then(function successCallback(response){
        	console.log("successcallback create new poc-->" +JSON.stringify(response));
        	handler("success",response);
		 }, function errorCallback(response) {
			 console.log("errorCallback response-->" +response);
		    handler("error",response);
	  });
      
    }
    this.getProjectData = function(handler) {
    	//console.log('get Project data');
    	 $http({
  			method : 'GET',
  			url : urlPrefix + '/springBoot/getProjectData',
  		}).then(function successCallback(response){
  				handler("success",response);
  		 }, function errorCallback(response) {
			handler("error",response);
		  });
    }
    
    this.pocDocumentDownload = function(projectId , handler){
    	console.log('download');
    	var projectData = new FormData();
    	projectData.append('projectId', projectId);
   	 $http( {
 			method : 'GET',
 			params: {projectId:projectId} ,
 			url : urlPrefix + '/springBoot/pocdocumentdownload',
 			responseType: 'arraybuffer',
 		}).then(function successCallback(response){
 			handler("success",response);
 		 }, function errorCallback(response) {
 			 console.log(response);
			handler("error",response);
		  });
     
   }
    
    this.deletePoc = function(projectId , handler){
    	console.log('delete POC');
    	
   	 $http( {
 			method : 'GET',
 			params: {projectId:projectId} ,
 			url : urlPrefix + '/springBoot/deleteProject',
 		  }).then(function successCallback(response){
 			handler("success",response);
 		 }, function errorCallback(response) {
 			 console.log(response);
			handler("error",response);
		  });
     
   }
    
}])

.directive('predixProjectsnew', function(){
	 console.log('predix new project directive called');
	  return{
		  restrict: 'E',
		  replace: true,
		  templateUrl:'assets/partials/predix-newproject.html',
		 
	  }
  })
  
  .directive('predixProjects', function(){
	 
	  return{
		  restrict: 'E',
		  replace: true,
		  templateUrl:'assets/partials/predix-projects.html',
		  /*scope:{
			  projects: '='
		  }*/
	  }
  })
  
   .directive('predixServices', function(){
	   console.log('*** SERVICES directive called');
	  return{
		  restrict: 'E',
		  replace: true,
		  templateUrl:'assets/partials/predix-services.html',
		 scope:{
			  services: '=services'
		  }
	  }
  })

  .directive('bookGenres', function(){
    return {
      restrict: 'E',
      templateUrl: 'partials/book-genres.html',
      scope: {
        genres: '='
      },
      
    }
  })
  
  .directive('bookCover', function(){
    return {
      restrict: 'E',
      templateUrl: 'partials/book-cover.html',
      replace: true
    }
  })

  .directive('reviewForm', function(){
    return {
      restrict: 'E',
      templateUrl: 'partials/review-form.html',
      replace: true,
      controller: function(){
        this.book = {genres:{}};

        this.addReview = function(form){
          books.push(this.book);
          this.book = {genres:{}};
          form.$setPristine();
        }
      },
      controllerAs: 'reviewFormCtrl',
      scope: {
        books: '=',
        genres: '='
      }
    }
  });

  var genres = [ 'fable', 'fantasy', 'fiction', 'folklore', 'horror', 'humor', 'legend', 'metafiction', 'mystery', 'mythology', 'non-fiction', 'poetry' ];

  
  var projects = [
			{
				url : 'https://digitalthreadpredix.run.aws-usw02-pr.ice.predix.io/digitalThread/#/mydashboards',
				title : 'Digital Thread',
				imgSrc : 'assets/img/predix/DigitalThread.png',
				projectName : 'Digital Thread',
				projectDescription : 'Digital Thread is a application which is used to customize the users view of data in the form of cards under the dashboard. He also has the ability to create a Personalized card based on the available cards. User can also create a Relation card which combines two application data based on relation and display the card data',
			},
			{
				url : '#',
				title : 'Engineering New Processes and Practices',
				imgSrc : 'assets/img/predix/enpp.png',
				projectName : 'eNPP',
				projectDescription : 'The user can view and modify the Planned Orders. The Planned Orders could be filtered based on different criterias like Item Number, Buyer Name, Supplier Name or Business Unit. This also has a feature for exporting the data into an excel sheet.',
			},
			{
				url : '#',
				title : 'To be Added',
				imgSrc : 'assets/img/predix/5.png',
				projectName : 'To be Added',
				projectDescription : 'To be Added',
			} ];
  
  var services = [
      			{
      				serviceName : 'Shop Visit Details',
      				serviceUrl : 'http://predix-alarmservice-sapan-km.grc-apps.svc.ice.ge.com/shopVisitDetailsByEsn',
      				serviceDescription : 'This service will return the Shop Visit detailed incurred (expenditure amount with shop visit Date) based on Model and Engine Number.',
      				serviceClass:'fa fa-bar-chart',
      			},
      			{
      				serviceName : 'Overall Equipment Efficiency',
      				serviceUrl : 'http://predix-alarmservice-sapan-km.grc-apps.svc.ice.ge.com/getEquipmentOEEDetailsWithParam',
      				serviceDescription : 'Service will return data for overall equipment efficiency, avaibility, performance &amp; quality.',
      				serviceClass:'fa fa-tachometer',
      			},
      			{
      				serviceName : 'Fetch all Data',
      				serviceUrl : 'http://irev-dsv-backlog-service.grc-apps.svc.ice.ge.com/getAllDsvBackLogDetailsNew',
      				serviceDescription : 'This service can be used to fetch all the backlog details in database to be displayed in table. Also have a customized version for filter search.',
      				serviceClass:'fa fa-search-plus',
      			},
      			{
      				serviceName : 'Export to Excel',
      				serviceUrl : 'http://scp-searchdownload-excel-without-fliter.grc-apps.svc.ice.ge.com/view/getDemandDetailsWithOutFiltersExcel',
      				serviceDescription : 'Service to download excel report. Customized version also available for filtered search.',
      				serviceClass:'fa fa-archive',
      			},
      			{
      				serviceName : 'Login Service',
      				serviceUrl : 'http://predix-tracktraceservice-sm.grc-apps.svc.ice.ge.com/login',
      				serviceDescription : 'Service will validate the user login details.For valid user, it will allow the user to enter the Track &amp; Trace system. For invalid user it will redirect to Login page with specific error messages.',
      				serviceClass:'fa fa-user ',
      			},
      			{
      				serviceName : 'Register Service',
      				serviceUrl : 'http://predix-tracktraceservice-sm.grc-apps.svc.ice.ge.com/register',
      				serviceDescription : 'Service will register a new user. On successful registration, user will be redirected to the Home screen.',
      				serviceClass:'fa fa-user-plus',
      			},
      			{
      				serviceName : 'Notification Service',
      				serviceUrl : 'http://predix-tracktracedashboard.grc-apps.svc.ice.ge.com/getMachineNotification',
      				serviceDescription : 'Notify the user giving details of machine name, number, working status and other informations.',
      				serviceClass:'fa fa-exclamation-triangle',
      			}
      			];
})();
