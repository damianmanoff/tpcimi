var app = angular.module('cimModule',[]);

app.controller('cim', cim);
cim.$inject = [
	'$scope',
	'$q',
	'$http'
];

function cim($scope, $q, $http){	

	$scope.api_ip 	= "localhost";
	$scope.api_port	= "8081";
	
	$scope.userID 	= 0;
	$scope.mechantID 	= 0;
	$scope.result 	= 0;
	
	/* --- */

	$scope.getProb = function() {

		var url = "http://" + $scope.api_ip + ':' + $scope.api_port + '/user_mechant/' + $scope.userID + "/" + $scope.mechantID;
        var deferred = $q.defer();
        var request = $http({
            method: "GET",
            url: url,
            async:false,
            crossDomain:true,
        }).success(function(response) {
        	
           $scope.result = (response * 100).toFixed(2);
           //$scope.result = response ;
        }).error(function(response){
            deferred.reject(response);
        });

     	return deferred.promise;	
	}
	
	/* --- */


	
}

