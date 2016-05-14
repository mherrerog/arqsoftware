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
app.controller('signinCtrl', function($scope, $timeout) {

  $scope.error=getQueryVariable('error');

  // Muestra el error durante 5s. y lo oculta
  $timeout(function () {
      $scope.error = "OK";
  }, 3500);


});
