// Lectura de cookies
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

// Funciones para carga de imagenes
function abrir(url) {
	window.open(url, '', 'top=' + ((screen.height - 500) / 2) + ',left=' + ((screen.width - 500) / 2) + ',width=500%,height=500%');
}

function PreviewImage() {
	var oFReader = new FileReader();
	oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);

	oFReader.onload = function(oFREvent) {
		document.getElementById("uploadPreview").src = oFREvent.target.result;
	};
};

function startTime() {
	today = new Date();
	h = today.getHours();
	m = today.getMinutes();
	m = checkTime(m);
	document.getElementById('reloj').innerHTML = h + ":" + m;
	t = setTimeout('startTime()', 500);
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
window.onload = function() {
	startTime();
}

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
app.controller('homeCtrl', function($scope, $http, $location, $sce) {
  $http.get("/ArqSoftware/ObtenerPublicaciones?page=home").then(
    function(response) {
      $scope.myData = response.data.publicaciones;

			// Leer cookies
			$scope.propio = getCookie('userId');
    });

	// Funcion para leer comentarios
	$scope.getComentarios = function(pub){
    $http.get("/ArqSoftware/ObtenerComentarios?pub=" + pub).then(function(res){
        $scope.myComments = res.data.comentarios;
				$scope.pubId = pub;
    });
  }

	// Funcion para leer comentarios
	$scope.getYoutube = function(url){
    $scope.videoURL = $sce.trustAsResourceUrl('http://www.youtube.com/embed/' + url);
		return $scope.videoURL;
  }

});
