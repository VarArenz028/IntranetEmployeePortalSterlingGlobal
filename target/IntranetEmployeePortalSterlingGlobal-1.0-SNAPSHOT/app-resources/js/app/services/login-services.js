var app = angular.module('sterling.services', ['ngResource', 'ngCookies']).config(function($httpProvider)
{

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

app.factory('formParams', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + 'employee/details/';

    return $resource(urlBase + ':id', {id: '@id'}, {

      'save': {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          }
      }

     });

}]);

// app.factory('User', ['$resource', '$window', function($resource, $window)
// {
//
//     var urlBase = $window.location.origin + $window.location.pathname + '/';
//
//     return $resource(urlBase + ':user', {user: '@user'}, {
//
//         query:  {
//             method : 'GET', isArray : false
//           }
//
//      });
//
// }]);

app.factory('LoginServces', ['$http', '$window', '$rootScope', '$cookies', function($http, $window, $rootScope, $cookies)
{
    // this will get the absolute url
    var urlBase = $window.location.origin + $window.location.pathname;

    var loginServices = {};

    loginServices.setCredentials = setCredentials;
    loginServices.setCampaignCredential = setCampaignCredential;
    loginServices.clearCredentials = clearCredentials;


    loginServices.login = function(username, password, cb)
    {

        $http({

           url: urlBase + username + '/' + password,
           method: 'GET'

        }).then(function(result)
        {

            cb(result);

        });

    };

    loginServices.getAccounts = function(userId, cb)
    {

        $http({

            url: urlBase,
            method: 'GET',
            params: {userId: userId}

        }).then(function(response)
        {

            cb(response);

        });

    };
    
    function setCredentials(username, password)
    {


        var authenticationData = Base64.encode(username + ':' + password);

        $rootScope.authenticationData = authenticationData;

        $rootScope.globals = {

            currentUser: {

                username: username,
                authenticationData: authenticationData

            }

        };


        $http.defaults.headers.common['Authorization'] = 'Basic ' + authenticationData;

        var cookieExpire = new Date();

        cookieExpire.setDate(cookieExpire.getDate + 7);
        $cookies.putObject('globals', $rootScope.globals, { expires: cookieExpire});



    };

    function setCampaignCredential(campaignName)
    {
        var authenticationCampId = campaignName;
        $rootScope.campGlobals = {

            currentCampaign: {

                campaignName: campaignName,
                authenticationCampId: authenticationCampId

            }

        };

        $http.defaults.headers.common['Authorization'] = 'Basic ' + authenticationCampId;
        var cookieExpire = new Date();
        cookieExpire.setDate(cookieExpire.getDate + 7);
        $cookies.putObject('campGlobals', $rootScope.campGlobals, { expires: cookieExpire});
        $cookies.putObject('currentCampaign', campaignName);

    };

    function clearCredentials()
    {

        $rootScope.globals = {};
        $cookies.remove('globals');
        $http.defaults.headers.common.Authorization = 'Basic';

    };


    // Base64 encoding service used by AuthenticationService
    var Base64 = {

        keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',

        encode: function (input)
        {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            do
            {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2))
                {
                    enc3 = enc4 = 64;
                }
                else if (isNaN(chr3))
                {
                    enc4 = 64;
                }

                output = output +
                    this.keyStr.charAt(enc1) +
                    this.keyStr.charAt(enc2) +
                    this.keyStr.charAt(enc3) +
                    this.keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            }
            while (i < input.length);

            return output;
        },

        decode: function (input)
        {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input))
            {
                window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            do
            {
                enc1 = this.keyStr.indexOf(input.charAt(i++));
                enc2 = this.keyStr.indexOf(input.charAt(i++));
                enc3 = this.keyStr.indexOf(input.charAt(i++));
                enc4 = this.keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64)
                {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64)
                {
                    output = output + String.fromCharCode(chr3);
                }

                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";

            }
            while (i < input.length);

            return output;
        }
    };

    return loginServices;

}]);
