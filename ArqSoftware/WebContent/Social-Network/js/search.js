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
  var val = getQueryVariable("userSearch");
  $http.get("/ArqSoftware/BuscaUsuario?busca=" + val).then(
      function(response) {
        $scope.myData = response.data.usuarios;

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
