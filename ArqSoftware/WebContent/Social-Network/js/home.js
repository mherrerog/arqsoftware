/* Funciones para mostrar y ocultar elementos */
function mostrareldiv(name) {
	document.getElementById(name).style.display = "block";
}

function ocultareldiv(name) {
	document.getElementById(name).style.display = "none";
}

function mostrar_y_ocultar_div(mostrar1, mostrar2, ocultar) {
	document.getElementById(mostrar1).style.display = "block";
	document.getElementById(mostrar2).style.display = "block";
	document.getElementById(ocultar).style.display = "none";
}

function ocultar_y_mostrar_div(mostrar1, mostrar2, ocultar) {
	document.getElementById(mostrar1).style.display = "none";
	document.getElementById(mostrar2).style.display = "none";
	document.getElementById(ocultar).style.display = "block";
}

/* Carga de publicaciones */
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
