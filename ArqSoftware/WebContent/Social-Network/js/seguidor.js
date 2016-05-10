function getQueryVariable(variable) {
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split("=");
    if (pair[0] == variable) {
      return pair[1];
    }
  }
  return (false);
}

var app = angular.module('myApp', []);
app.controller('searchCtrl', function($scope, $http, $location) {
	var metodo = getQueryVariable("metodo");
	var args = getQueryVariable("id");
  $http.get("/ArqSoftware/Seguidores?metodo=" + metodo + "&id=" + args).then(
      function(response) {
        $scope.myData = response.data.usuarios;
        
      });
});
