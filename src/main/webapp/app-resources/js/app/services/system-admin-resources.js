var app = angular.module('sterling.services', ['ngResource', 'ngCookies']);

app.factory('Dashboard', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/dashboard/';

    return $resource(urlBase + ':dashboard', {dashboard: '@dashboard'}, {

        query:  {
            method : 'GET', isArray : false
          }

     });

}]);

app.factory('Campaign', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/campaigns/';

    return $resource(urlBase + ':campaignId', {campaignId: '@campaignId'}, {

      query:  {
          method : 'GET', isArray : false
        },
        updateCampaign: { method: 'PUT' }

    });

}]);
app.factory('EditUser', ['$http', function($http)
{

    var editUser = {};

    editUser.addCampaign = function(campaign, userId, urlBase, cb)
    {
        $http({

            url: urlBase,
            method: 'POST',
            params: {campaign: campaign, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });
    }
    editUser.updateUsername = function(username, userId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {username: username, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    editUser.updatePassword = function(password, userId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {password: password, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    editUser.updateRole = function(role, userId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {role: role, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    editUser.updateLastname = function(lastName, userId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {lastName: lastName, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    editUser.updateFirstname = function(firstName, userId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {firstName: firstName, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    editUser.updateState = function(account, userId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {account: account, userId: userId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    return editUser;

}]);
app.factory('User', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/people/';

    return $resource(urlBase + ':userId', {userId: '@userId'}, {
      query:  {
          method : 'GET', isArray : false
        }
        // ,
        // updateUser: { method: 'PUT' }

    });

}]);


app.factory('LoginServces', ['$http', '$window', '$rootScope', '$cookies', function($http, $window, $rootScope, $cookies)
{
    // this will get the absolute url
    var urlBase = $window.location.origin + $window.location.pathname;

    var loginServices = {};

    loginServices.setCredentials = setCredentials;
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

    function setCredentials(username, password)
    {


        var authenticationData = Base64.encode(username + ':' + password);


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
