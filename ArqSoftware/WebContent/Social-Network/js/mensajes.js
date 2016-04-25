/* Adquiere el valor de los parametros de la peticion GET */
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
app.controller('mensajeCtrl', function($scope, $http, $location) {
  $http.get("/ArqSoftware/ObtenerMensajes").then(
    function(response) {
      $scope.myData = response.data.usuarios;
      $scope.myMens = response.data.mensajes;
    });
});
