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
		$scope.servers.values[i].id = i;
		$http.post($scope.servers.values[i].url, $scope.servers.values[i]).success(function(data, status) {
			// replace values in array with server values
			for ( var j = 0 ; j < $scope.servers.values.length ; j++ ) { 
				if (j == data.id) {
					$scope.servers.values[j] = data;
					break;
				}
			}
	    }).error(function(data, status, headers, config) {
	        console.log(data);
	    });
		
		/*$http.post($scope.servers.values[i].url,{data: $scope.servers.values[i]}).success(function(data) {
			alert(data);
		}).error(function(data, status) {
			alert('ERROR! ' + status);
			console.log(data);
		});*/
	}
});