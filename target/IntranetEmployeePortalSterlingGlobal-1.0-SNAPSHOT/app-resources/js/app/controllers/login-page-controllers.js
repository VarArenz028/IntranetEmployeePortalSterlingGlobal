var app = angular.module('sterling.controllers', ['ui.router', 'ngAnimate', 'ngCookies', 'ngMessages', 'naif.base64', 'LocalStorageModule']).config(function($httpProvider)
{

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
var direct = angular.module('sterling.directives', []);

app.controller('logTemplateController', ['$scope', '$rootScope', function($scope, $rootScope)
{

    $rootScope.$on('logTemplateEvent', function(event, data)
    {

        $scope.showLog = data.showLog;
        $scope.showCamp = data.hideCamp;

    });
    $rootScope.$on('campTemplateEvent', function(event, data)
    {

        $scope.showLog = data.hideLog;
        $scope.showCamp = data.showCamp;

    });

}]).config(function($httpProvider)
{

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

// login controller

app.controller('loginController', ['$scope', 'LoginServces',
 '$timeout', '$rootScope', '$cookies', '$window', '$state', '$http',
 function($scope, LoginServces, $timeout, $rootScope, $cookies, $window, $state, $http)
{

    $scope.showLog = true;

    $rootScope.$on('campTemplateEvent', function(event, data)
    {

        $scope.showLog = data.showLog;
        $scope.showCamp = data.showCamp;

    });


    $scope.username = null;
    $scope.password = null;
    $scope.logHeadMessage = true;
    $scope.errorMessage = false;
    $scope.logLoading = false;
    $scope.disableButton = false;


    $scope.login = function()
    {
      var urlBase = $window.location.origin + $window.location.pathname;

      var config = {
      params: {
       username: $scope.username,
       password: $scope.password,
      },
      ignoreAuthModule: 'ignoreAuthModule'
   };

      $http.post('authenticate', '', config).then(function(response)
      {
          var locationUrl = $window.location.origin + $window.location.pathname;
          var responsed = response.data;
          $rootScope.results = responsed;
          $rootScope.role = responsed.role;
          $scope.logLoading = true
          if(responsed.role === 'System Admin')
          {
              $window.location.href = locationUrl + 'system-admin';
              LoginServces.setCredentials($scope.username, $scope.password);
          }
          else if(responsed.role === 'Campaign Admin')
          {
              LoginServces.setCredentials($scope.username, $scope.password);
              var id = response.data.userId;
              $rootScope.userId = id;
              LoginServces.getAccounts(id, function(result)
              {
                  $rootScope.accounts = result.data;
                  $rootScope.avatar = result.data;
                  $scope.showLog = false;

                  $('.logView').css({

                      'display' : 'none',

                  });
                  $state.go('loginPage.campaign');
              });

          }
          else if(responsed.role === 'Agent')
          {
              LoginServces.setCredentials($scope.username, $scope.password);
              var id = response.data.userId;
              $rootScope.userId = id;
              LoginServces.getAccounts(id, function(result)
              {
                  $rootScope.accounts = result.data;
                  $rootScope.avatar = result.data;
                  $scope.showLog = false;

                  $('.logView').css({

                      'display' : 'none',

                  });
                  $state.go('loginPage.campaign');
              });
          }

      });




    };
    $scope.next = function(logSteps)
    {

        $scope.direction = 1;
        $scope.logSteps = logSteps;

    };

    $scope.showPopUp = true;
    $scope.forgotPass = function()
    {
        $rootScope.$broadcast('logPopUpEvent', {

            pop: $scope.showPopUp

        });
    };

    $scope.$on('$viewContentLoaded', function()
    {

        angular.element(document).ready(function()
        {

            $('#forgotPass').on('click', function()
            {

                $('#logPopUpBackground').fadeIn();

            });

            $('#dismissButton').on('click', function()
            {

                $('#logPopUpBackground').fadeOut();

            });

        });

    });

}]).config(function($httpProvider)
{

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

app.controller('campaignController', ['$scope', '$state', '$rootScope', '$window', 'LoginServces', '$http', 'localStorageService', '$cookies',
 function($scope, $state, $rootScope, $window, LoginServces, $http, localStorageService, $cookies)
{
    var accounts = $rootScope.accounts;
    var avatar = $rootScope.avatar;
    $cookies.putObject('empName', accounts.empName);

    localStorageService.set('avatar', avatar.userAvatar);

    $scope.camps = accounts.accounts;
    $scope.showCamp = true;

    $scope.showCamp = true;
    var locationUrl = $window.location.origin + $window.location.pathname;

    var role = $rootScope.role;

    if(role === 'Campaign Admin')
    {
        $scope.selectCampaign = function(campaignName)
        {
            LoginServces.setCampaignCredential(campaignName);
            $window.location.href = locationUrl + 'campaign-admin';


        };
    }
    else if(role === 'Agent')
    {
        $scope.selectCampaign = function(campaignName)
        {
            LoginServces.setCampaignCredential(campaignName);
            $window.location.href = locationUrl + 'agent';


        };
    }



}]);



// .config(function($httpProvider)
// {
//
//     $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
//
// });

// pop-up controller
app.controller('popUpController', ['$scope', function($scope)
{
    $scope.showPopUp = false;
    $scope.$on('logPopUpEvent', function(event, data)
    {

        $scope.showPopUp = data.pop;

    });

    $scope.dismiss = function()
    {
        $scope.showPopUp = false;
    };

}]);

app.controller('yourDetailsController', ['formParams', '$scope', '$http', '$window', '$timeout', function(formParams, $scope, $http, $window, $timeout)
{
    // $scope.formParams = new formParams();
    $scope.formParams = {

        empDetailsAvatarWrapper:
        {

            employeeDetails:
            {
                email: '',
                position: '',
                contactNumber: '',
                age: '',
                gender: ''
            },
            user:
            {
                password: ''
            },
            userAvatar: []

        }

    };

    // if($scope.formParams.userAvatar == null)
    // {
    //     $scope.disableButton = true;
    // }
    // else if($scope.formParams.userAvatar != null)
    // {
    //     $scope.disableButton = false;
    // }
    $scope.next = function (details)
    {

      $scope.direction = 1;
      $scope.details = details;
      console.log($scope.formParams);

    };
    $scope.back = function (details)
    {

      $scope.direction = 0;
      $scope.details = details;

    };

    $scope.submit = function(details)
    {

        $scope.logLoading = true;
        $scope.disableButton = true;

        $timeout(function()
        {
            $scope.logLoading = false;
            $scope.direction = 1;
            $scope.details = details;
            var urlBase = $window.location.origin + $window.location.pathname + 'employee/details/';

            $http({
             method: 'POST',
             url: urlBase,
             data: $scope.formParams,
              headers: {
                  'Content-Type': 'application/json'
            }});

        }, 3000);


        // $window.location.href = locationUrl + 'campaign-admin';
        // $http.defaults.headers.post["Content-Type"] = "application/json";
        // formParams.save(function(response)
        // {
        //     console.log($scope.formParams);
        // });
    };

    $scope.proceed = function()
    {
        var locationUrl = $window.location.origin + $window.location.pathname;
        $window.location.href = locationUrl + 'campaign-admin';

    };

}]);

// login route configuration

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider)
{
    $urlRouterProvider.otherwise('');
    $stateProvider.state('loginPage', {

       url: '',

       views: {

           '': {

               templateUrl: 'login-page-layout',
               // controller: 'logTemplateController'

           },
           'login@loginPage': {

               templateUrl: 'userLoginTemplate',
               controller: 'loginController'


           }


        }

    }).state('loginPage.campaign', {

       url: '',

       views: {

           'campaign@loginPage': {

               templateUrl: 'userCampaignTemplate',
               controller: 'campaignController'


           }

        }

    }).state('yourDetails', {

        url: '/employee/details',

        views: {

            'yourDetails': {

                templateUrl: 'yourDetails',
                controller: 'yourDetailsController'
            }

        }

    });

    // .state('loginPage.login', {
    //
    //    url: '',
    //
    //    views: {
    //
    //        // 'login@loginPage': {
    //        //
    //        //     templateUrl: 'userLoginTemplate',
    //        //     controller: 'loginController'
    //        //
    //        //
    //        // }
    //
    //     }
    //
    // })

}]).config(function($httpProvider)
{

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
