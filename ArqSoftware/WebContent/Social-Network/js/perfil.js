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
			// Paginacion de miembros del equipo
			$scope.curPage = 0;
			$scope.pageSize = 4;

      $scope.myUser = response.data.usuario;
			$scope.myId = $scope.myUser[0].Id;
			$scope.jugadores = response.data.jugadores;
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
    })

		// Id inicial negativo, todos son mayor de 0
		$scope.hoverEdit = -1;

		$scope.numberOfPages = function() {
			return Math.ceil($scope.jugadores.length / $scope.pageSize);
		};

		//Funciones para mostrar y ocultar un close
		$scope.hoverIn = function(i){
			$scope.hoverEdit = i;
		};

		$scope.hoverOut = function(){
				$scope.hoverEdit = -1;
		};
});

angular.module('myApp').filter('pagination', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	};
});
