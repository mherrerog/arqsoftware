var app = angular.module('myApp', []);
app.controller('homeCtrl', function($scope, $http, $location) {
  $http.get("/ArqSoftware/ObtenerPublicaciones?page=home").then(
    function(response) {
      $scope.myData = response.data.publicaciones;
    });
});

function showImg(id){
  alert('Prueba');
  document.getElementById(id).style.display = "block";
}
