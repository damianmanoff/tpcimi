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
	$scope.response 	= {};
	
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
           $scope.response = response;
        }).error(function(response){
            deferred.reject(response);
        });

     	return deferred.promise;	
	}
	
	/* --- */


	
}

