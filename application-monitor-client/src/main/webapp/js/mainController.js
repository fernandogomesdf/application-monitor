angular.module('app').controller('MainCtrl', function($scope, $window, $interval, $rootScope, $http) {
	
	// three servers with same values for testing purposes. It should be changed with real values.
	$scope.servers = {
		"values" : [
				{
					"name" : "localhost",
					"url" : "http://localhost:8080/application-monitor-server/services/status/serverStatus"
				},
				{
					"name" : "localhost",
					"url" : "http://localhost:8080/application-monitor-server/services/status/serverStatus"
				},
				{
					"name" : "localhost",
					"url" : "http://localhost:8080/application-monitor-server/services/status/serverStatus"
				}]
	};
	
	for ( var i = 0 ; i < $scope.servers.values.length ; i++ ) {
		
		$http.post($scope.servers.values[i].url, $scope.servers.values[i]).success(function(data, status) {
			alert(data);
	    }).error(function(data, status, headers, config) {
	        
	    });
		
		
		/*$http.post($scope.servers.values[i].url,{data: $scope.servers.values[i]}).success(function(data) {
			alert(data);
		}).error(function(data, status) {
			alert('ERROR! ' + status);
			console.log(data);
		});*/
	}
});