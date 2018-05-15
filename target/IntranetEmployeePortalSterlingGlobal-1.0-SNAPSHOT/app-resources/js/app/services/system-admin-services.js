
app.factory('saServices', ['$http', '$q', '$window', function($http, $q, $window)
{
    
    var urlBase = $window.location.origin + $window.location.pathname;
    
    var saUserServices = {
        
    };
    
    saUserServices.getAllCampaigns = function(cb)
    {
      
        $http({
            
           url: urlBase + '/people',
           method: 'GET',
            
        }).then(function(result)
        {
           
            cb(result);
            
        });
        
    };
    
    
    return saUserServices;
    
}]);


//app.factory('AuthService', ['$http', '$q', '$window', function($http, $q, $window)
//{
//    var urlBase = $window.location.origin + $window.location.pathname;
//    
//    var authService = {};
//    
//    authService.logout = function(cb)
//    {
//        $http({
//            
//            url: urlBase + '/logout',
//            
//            
//        }).then(function(result)
//        {
//            cb(result);    
//        });
//    };
//    
//    return authService;
//    
//}]);




















