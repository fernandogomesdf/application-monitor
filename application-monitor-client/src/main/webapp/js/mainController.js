angular.module('app').controller('MainCtrl', function($scope, $window, $interval, $rootScope, $http) {
	
	// three servers with same values for testing purposes. It should be changed with real values.
	$scope.servers = {
		"values" : [
			{
				"name" : "localhost 1",
				"url" : "http://localhost:8080/application-monitor-server/services/status/serverStatus"
			},
			{
				"name" : "localhost 2",
				"url" : "http://localhost:8080/application-monitor-server/services/status/serverStatus"
			},
			{
				"name" : "localhost 3",
				"url" : "http://localhost:8080/application-monitor-server/services/status/serverStatus"
			}]
	};
	
	$scope.checkServers = function() {
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
		    	// replace values in array with server values
		    	for ( var j = 0 ; j < $scope.servers.values.length ; j++ ) { 
		    		if (j == config.data.id) {
						$scope.servers.values[j].status = 3; // red alert
						$scope.servers.values[j].serverStatus = status; 
						$scope.servers.values[j].data = data; // error message from the server
						break;
					}
		    	}
		    });
		}
	}
	
	$scope.checkServers();
	$interval(function(){
		$scope.checkServers();
	}, 500);
	
});