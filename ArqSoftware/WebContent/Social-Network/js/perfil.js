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
var val = getQueryVariable("user");
app.controller('perfilCtrl', function($scope, $http, $location) {
  $http.get("/ArqSoftware/ObtenerPublicaciones?page=profile&user=" + val).then(
    function(response) {
      $scope.myUser = response.data.usuario;
      $scope.myData = response.data.publicaciones;
      // Recorrer valor de cookies
      var rastro = document.cookie.split('=');
      for (var i = 0; i < rastro.length; i++) {
        var par = rastro[i];
        if (par == 'userId'){
            $scope.propio = rastro[i+1];
        }
        // Avanzar otro valor de i
        i++;
      }
    });
});
