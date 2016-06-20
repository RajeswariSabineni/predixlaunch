(function(){
  'use strict';

  angular.module('predixApp', [])

  .controller('PredixAppController', function(){
    this.projects = projects;
    this.services = services;
    this.genres = genres;
    this.showForm = false;
  })
  
  .directive('predixProjects', function(){
	  return{
		  restrict: 'E',
		  replace: true,
		  templateUrl:'assets/partials/predix-projects.html',
		  scope:{
			  projects: '='
		  }
	  }
  })
  
   .directive('predixServices', function(){
	  return{
		  restrict: 'E',
		  replace: true,
		  templateUrl:'assets/partials/predix-services.html',
		  scope:{
			  services: '='
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
				url : 'https://predixdigitalthread.run.aws-usw02-pr.ice.predix.io/digitalThread/#/mydashboards',
				title : 'Digital Thread',
				imgSrc : 'assets/img/predix/DigitalThread.png',
				projectName : 'Digital Thread',
				projectDescription : 'Digital Thread is a application which is used to customize the users view of data in the form of cards under the dashboard. He also has the ability to create a Personalized card based on the available cards. User can also create a Relation card which combines two application data based on relation and display the card data',
			},
			{
				url : 'https://predix-ge-scpapps.run.aws-usw02-pr.ice.predix.io/',
				title : 'Engineering New Processes and Practices',
				imgSrc : 'assets/img/predix/enpp.png',
				projectName : 'eNPP',
				projectDescription : 'The user can view and modify the Planned Orders. The Planned Orders could be filtered based on different criterias like Item Number, Buyer Name, Supplier Name or Business Unit. This also has a feature for exporting the data into an excel sheet.',
			},
			{
				url : 'https://predix-ge-scpapps.run.aws-usw02-pr.ice.predix.io/',
				title : 'Supplier Collaboration Portal',
				imgSrc : 'assets/img/predix/5.png',
				projectName : 'SCP',
				projectDescription : 'The user can view and modify the Planned Orders. The Planned Orders could be filtered based on different criterias like Item Number, Buyer Name, Supplier Name or Business Unit. This also has a feature for exporting the data into an excel sheet.',
			} ];
  
  var services = [
      			{
      				serviceName : 'Shop Visit Details',
      				serviceUrl : 'http://predix-alarmservice-sapan-km.grc-apps.svc.ice.ge.com/shopVisitDetailsByEsn',
      				serviceDescription : 'This service will return the Shop Visit detailed incurred (expenditure amount with shop visit Date) based on Model and Engine Number.',
      			},
      			{
      				serviceName : 'Overall Equipment Efficiency',
      				serviceUrl : 'http://predix-alarmservice-sapan-km.grc-apps.svc.ice.ge.com/getEquipmentOEEDetailsWithParam',
      				serviceDescription : 'Service will return data for overall equipment efficiency, avaibility, performance &amp; quality.',
      			},
      			{
      				serviceName : 'Fetch all Data',
      				serviceUrl : 'http://irev-dsv-backlog-service.grc-apps.svc.ice.ge.com/getAllDsvBackLogDetailsNew',
      				serviceDescription : 'This service can be used to fetch all the backlog details in database to be displayed in table. Also have a customized version for filter search.',
      			},
      			{
      				serviceName : 'Export to Excel',
      				serviceUrl : 'http://scp-searchdownload-excel-without-fliter.grc-apps.svc.ice.ge.com/view/getDemandDetailsWithOutFiltersExcel',
      				serviceDescription : 'Service to download excel report. Customized version also available for filtered search.',
      			},
      			{
      				serviceName : 'Login Service',
      				serviceUrl : 'http://predix-tracktraceservice-sm.grc-apps.svc.ice.ge.com/login',
      				serviceDescription : 'Service will validate the user login details.For valid user, it will allow the user to enter the Track &amp; Trace system. For invalid user it will redirect to Login page with specific error messages.',
      			},
      			{
      				serviceName : 'Register Service',
      				serviceUrl : 'http://predix-tracktraceservice-sm.grc-apps.svc.ice.ge.com/register',
      				serviceDescription : 'Service will register a new user. On successful registration, user will be redirected to the Home screen.',
      			},
      			{
      				serviceName : 'Notification Service',
      				serviceUrl : 'http://predix-tracktracedashboard.grc-apps.svc.ice.ge.com/getMachineNotification',
      				serviceDescription : 'Notify the user giving details of machine name, number, working status and other informations.',
      			}
      			];
})();
