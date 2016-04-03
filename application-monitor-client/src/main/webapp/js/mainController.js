angular.module('app').controller('MainCtrl', function($scope, $window, $interval, $rootScope, $http) {
	
	$scope.refreshTime = 15;
	$scope.remainingTime = $scope.refreshTime;
	
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
						$scope.servers.values[j].serverStatus = status;
						$scope.servers.values[j].errMsg = null;
						break;
					}
				}
		    }).error(function(data, status, headers, config) {
		    	// replace values in array with server values
		    	for ( var j = 0 ; j < $scope.servers.values.length ; j++ ) { 
		    		if (j == config.data.id) {
						$scope.servers.values[j].status = 3; // red alert
						$scope.servers.values[j].serverStatus = status;
						$scope.servers.values[j].errMsg = data; // error message from the server
						$scope.servers.values[j].services = [];
						
						break;
					}
		    	}
		    });
		}
	}
	
	$scope.checkServers();
	
	$interval(function(){
		$scope.checkServers();
	}, $scope.refreshTime * 1000);
	
	$interval(function(){
		if ($scope.remainingTime == 1) {
			$scope.remainingTime = $scope.refreshTime + 1;
		}
		$scope.remainingTime = $scope.remainingTime - 1;
	}, 1000);
	
});