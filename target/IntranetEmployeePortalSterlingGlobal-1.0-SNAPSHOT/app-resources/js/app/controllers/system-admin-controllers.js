// @Author Var Arenz G. Villarino

var app = angular.module('sterling.controllers', ['ui.router', 'ngAnimate', 'ngCookies', 'ngMessages', 'chart.js']);
//var logServ = angular.module('sterling.services', ['ngCookies']);


app.controller('systemAdminController', ['LoginServces', '$scope', '$state', '$rootScope', '$cookies', '$window', '$http', function(LoginServces, $scope, $state, $rootScope, $cookies, $window, $http)
{

    var cookie = $cookies.getObject('globals');
    $scope.username = cookie.currentUser.username;

    $scope.goToOverview = function()
    {
        $state.go('saDashboard');
    };

    $scope.goToCampaign = function()
    {
        $state.go('saCampaign');
    };

    $scope.goToPeople = function()
    {
        $state.go('saPeople.list');
    };

    $scope.goToFile = function()
    {
        $state.go('saFile');
    };

    $scope.goToArticle = function()
    {
        $state.go('saArticle');
    };

    $scope.logout = function()
    {
      var urlBase = $window.location.origin + $window.location.pathname;
      var replace = $window.location.pathname.replace('system-admin', '') ;

        $http({

           url: replace + 'logout',
           method: 'GET',

         }).then(function()
         {

             console.log(replace + 'logout');
             $window.location.href = replace + 'logout';
             LoginServces.clearCredentials();

         });

    };

}]);

app.controller('systemAdminDashboardController', ['Dashboard', '$scope', '$state', function(Dashboard, $scope, $state)
{
    $scope.dashboardDetails = [];
    $scope.dashboard = new Dashboard();

    $scope.campaignNameLabels = [];
    $scope.campaignUserSize = [];
    $scope.fetchAllDashbaordDetails = function()
    {
        $scope.dashboardDetails = Dashboard.query(function(result)
        {
             console.log(result);
            $scope.doughLabels = ['System Admin', 'Campaign Admin', 'Agent'];
            $scope.userStatistics = [result.numberOfSystemAdmins, result.numberOfCampaignAdmin, result.numberOfAgent];

            angular.forEach(result.campaignStatistics, function(value, key)
            {
                $scope.campaignNameLabels.push(value.campaignName);
                $scope.campaignUserSize.push(value.numberOfUser);
            });


        });

    };


    $scope.fetchAllDashbaordDetails();

//    $scope.pieLabels = ["Download Sales", "In-Store Sales", "Mail-Order Sales", "Test", "sales", "technical", "New Employee", "Yonko", "Shibukai", "Zoro", "Luffy", "Sanji", "Nami", "Yusopp", "Franky", "Brook", "Robin", "Chopper", "Jinbei", "Dragon"];
//    $scope.pieData = [300, 500, 100, 200, 100, 200, 300, 400, 500, 600, 700, 800, 900, 100, 200, 300, 400, 500, 600];
//
    $scope.dougnutLabels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    $scope.dougnutdata = [300, 500, 100];

}]);

app.controller('systemAdminCampaignController', ['$scope', 'Campaign', '$rootScope', function($scope, Campaign, $rootScope)
{

    $scope.showPopUp = false;
    $scope.campaign = new Campaign();
    $scope.campaigns = [];
    $scope.campaignName = '';


    $scope.fetchAllCampaigns = function()
    {

        $scope.response = Campaign.query(function(response)
        {
            if(response.hasCampaignList === 'OK')
            {
                $scope.campaigns = response.campaignList;
            }
            else if(response.hasCampaignList === 'NO_CONTENT')
            {
                console.log('No campaign yet');
            }
        });

    };

    $scope.fetchAllCampaigns();


    // $scope.showCampaignForm = function()
    // {
    //
    //     $scope.showPopUp = true;
    //
    // };

    // $scope.hideCampaignForm = function()
    // {
    //     $scope.showPopUp = false;
    // }

    $scope.showAddCampaign = function()
    {
        $('#saAddCampaign').fadeIn();
    };

    $scope.editCampaign = function(id)
    {

        $('#saEditCampaign').fadeIn();

        $scope.oneCampaign = Campaign.get({campaignId: id});


        $scope.updateCampaign = function()
        {

            if($scope.campaignForm.$valid)
            {
                $scope.oneCampaign.$updateCampaign(function(result)
                {

                    alert('Successfully updated');
                    $scope.showPopBackground = false;
                    $scope.showEditCampaign = false;
                    $scope.fetchAllCampaigns();
                });
            }

        };


    };

    $scope.hideAddCampaign = function()
    {
        $('#saAddCampaign').fadeOut();
        $scope.clearField();
    };

    $scope.hideEditCampaign = function()
    {
        $('#saEditCampaign').fadeOut();
        $scope.clearField();
    };

    $scope.disable = true;
    $scope.addCampaign = function()
    {
        if($scope.saCampaignForm.$valid)
        {

            $scope.campaign.$save(function(result)
            {

                $scope.clearField();
                $scope.fetchAllCampaigns();
                alert('Successfull added');
                $('#saAddCampaign').fadeOut();

            }, function(error)
            {

                if(error.status === 409)
                {

                    alert($scope.campaign.campaignName + ' name already exist, Please check the spelling or call for assistance');
                    $scope.clearField();
                    //$scope.saCampaignForm.campaignName.$error.campFormError = true;
                    return false;

                }

            });

        }

    };

    $scope.showCampaignEditBox = function(id)
    {

        $scope.oneCampaign = Campaign.get({campaignId: id});


        $scope.updateCampaign = function()
        {

            if($scope.campaignForm.$valid)
            {
                $scope.oneCampaign.$updateCampaign(function(result)
                {

                    alert('Successfully updated');
                    $scope.showPopBackground = false;
                    $scope.showEditCampaign = false;
                    $scope.fetchAllCampaigns();
                });
            }

        };

        $scope.cancelUpdate = function()
        {
            $scope.showEditCampaign = false;
            $scope.showPopBackground = false;
        };

    };



    $scope.clearField = function()
    {

        $scope.campaign.campaignName = null;

    };

}]);

app.controller('systemAdminPeopleController', ['User', '$scope', '$window', '$state', '$rootScope', '$timeout', '$http', function(User, $scope, $window, $state, $rootScope, $timeout, $http)
{

    // $scope.responsed = new User();
    $scope.getAllDetails = function()
    {

        $scope.responsed = User.query(function(response)
        {
            console.log(response);
            if(response.usersResponse === 'OK')
            {
                $scope.users = response.users;
            }
            else if(response.usersResponse === 'NO_CONTENT')
            {
                console.log('No user');
            }

            if(response.campaignResponse === 'OK')
            {
                $scope.campaigns = response.campaigns;
            }
            else if(response.campaignResponse === 'NO_CONTENT')
            {
                console.log('No campaign yet');
            }
        });

    };
    $scope.getAllDetails();

    $scope.formParams = {

        user:
        {
            username: '',
            password: '',
            role: '',
        },
        employeeDetails:
        {
            lastName: '',
            firstName: ''
        },
        campaign: ''

    }

    $scope.$watch('formParams.user.role', function(newValue, oldValue)
    {
        if(newValue === 'System Admin')
        {
            $scope.isSysteAdmin = true;
        }
        else if(newValue !== 'System Admin')
        {
            $scope.isSysteAdmin = false;
        }
    });

    $scope.submit = function()
    {
        $scope.logLoading = true;
        $scope.disable = true;

            $scope.logLoading = false;

            var urlBase = $window.location.origin + $window.location.pathname + '/people';

            $http({

             method: 'POST',
             url: urlBase,
             data: $scope.formParams,
              headers: {
                  'Content-Type': 'application/json'}

              }).then(function(response)
              {
                  $scope.getAllDetails();
                  var details = 'details5';
                  $scope.next(details);
                  // $scope.clearField();
              });


    };

    $scope.clearField = function()
    {
        $scope.formParams.user.username = null;
        $scope.formParams.user.password = null;
        $scope.formParams.user.role = '';

        $scope.formParams.employeeDetails.lastName = null;
        $scope.formParams.employeeDetails.firstName = null;
        $timeout(function()
        {
            var details = '';
            $scope.next(details);
        }, 2000);

    };

    $scope.editUserPage = function(userId)
    {

        $state.go('details', {

           userId: userId

        });

    };

    // $('#saAddButton').on('click', function()
    // {
    //
    //     $('#newUserPop').fadeIn();
    //
    // });

    $('#newUserEkis').on('click', function()
    {

        $('#newUserPop').fadeOut();

    });

    $scope.addUser = function()
    {
        $('#newUserPop').fadeIn();
    };

    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;

    };

    $scope.back = function (details)
    {

      $scope.direction = 0;
      $scope.details = details;

    };

    $scope.reset = function()
    {
        // $scope.getAllDetails();
        // $scope.userRegistration.username = '';
        // $scope.userRegistration.password = '';
        // $scope.userRegistration.role = $scope.priveleges[0].value;
        $scope.newUserForm.$setPristine();
        $scope.newUserForm.$setUntouched();
    }

}]);

app.controller('saEditUserController', ['User', 'EditUser', '$scope', '$state', '$stateParams', '$rootScope', '$window',
 function(User, EditUser, $scope, $state, $stateParams, $rootScope, $window)
{

      $scope.showEditusername = function()
      {
          $('#saEditUsernamePop').fadeIn();

      };

      $scope.showEditPassword = function()
      {
          $('#saEditPasswordPop').fadeIn();
      };

      $scope.showEditRole = function()
      {
          $('#saEditRolePop').fadeIn();
      };

      $scope.showEditLastname = function()
      {
          $('#saEditLastNamePop').fadeIn();
      };

      $scope.showEditFirstname = function()
      {
          $('#saEditFirstNamePop').fadeIn();
      };

      $scope.showAddAccount = function()
      {
          $('#saAddAccountPop').fadeIn();
      };

      $scope.cancelEdit = function()
      {
          $('#saEditUsernamePop').fadeOut();
          $('#saEditPasswordPop').fadeOut();
          $('#saEditRolePop').fadeOut();
          $('#saEditLastNamePop').fadeOut();
          $('#saEditFirstNamePop').fadeOut();
          $('#saAddAccountPop').fadeOut();
      }

    $scope.backToUserList = function()
    {
        $state.go('saPeople.list');
    };

    $scope.userId = 0;

    if($stateParams.userId)
    {
        $scope.userId = $stateParams.userId;
    }

    $scope.fetchOneUser = function()
    {
        $scope.response = User.get({userId: $scope.userId}, function(response)
        {
            if(response.user.role === 'System Admin')
            {
                $scope.user = response.user;
                $scope.empDetails = response.employeeDetails;
                console.log('System Admin');
            }
            else
            {
                $scope.user = response.user;
                $scope.empDetails = response.employeeDetails;
                $scope.accounts = response.accounts;
                $scope.campaigns = response.campaigns;
            }
        });
    }
    $scope.fetchOneUser();

    // models
    $scope.username = '';
    $scope.password = '';
    $scope.role = '';
    $scope.lastName = '';
    $scope.firstName = '';
    $scope.account = '';
    $scope.priveleges = [
        {
            value: 'System Admin',
            privelege: 'System Administration'
        },
        {
            value: 'Campaign Admin',
            privelege: 'Campaign Admin'
        },
        {
            value: 'Agent',
            privelege: 'Agent'
        }
    ];

    $scope.updateUsername = function()
    {
        var username = $scope.username;
        var userId = $scope.response.user.userId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.updateUsername(username, userId, urlBase, function(response)
        {
            if(response.data.usernameUpdated === 'OK')
            {
                $('#saEditUsernamePop').fadeOut();
                alert('Username Successfully Updated')
                $scope.fetchOneUser();
            }
        });
    };

    $scope.updatePassword = function()
    {
        var password = $scope.password;
        var userId = $scope.response.user.userId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.updatePassword(password, userId, urlBase, function(response)
        {
            if(response.data.passwordUpdated === 'OK')
            {
                $('#saEditPasswordPop').fadeOut();
                alert('Password Successfully Updated')
                $scope.fetchOneUser();
            }
        });
    };

    $scope.updateRole = function()
    {
        var role = $scope.role;
        var userId = $scope.response.user.userId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.updateRole(role, userId, urlBase, function(response)
        {
            if(response.data.roleUpdated === 'OK')
            {
                $('#saEditRolePop').fadeOut();
                alert('Role Successfully Updated')
                $scope.fetchOneUser();
            }
        });
    };

    $scope.updateLastname = function()
    {
        var lastName = $scope.lastName;
        var userId = $scope.response.user.userId;
        var empId = $scope.response.employeeDetails.empDetailsId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.updateLastname(lastName, userId, urlBase, function(response)
        {
            if(response.data.lastNameUpdated === 'OK')
            {
                $('#saEditLastNamePop').fadeOut();
                alert('Lastname Successfully Updated')
                $scope.fetchOneUser();
            }
        });
    };

    $scope.updateFirstname = function()
    {
        var firstName = $scope.firstName;
        var userId = $scope.response.user.userId;
        var empId = $scope.response.employeeDetails.empDetailsId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.updateFirstname(firstName, userId, urlBase, function(response)
        {
            if(response.data.firstNameUpdated === 'OK')
            {
                $('#saEditFirstNamePop').fadeOut();
                alert('Firstname Successfully Updated')
                $scope.fetchOneUser();
            }
        });
    };

    $scope.addCampaign = function()
    {

        var account = $scope.account;
        var userId = $scope.response.user.userId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.addCampaign(account, userId, urlBase, function(response)
        {
            $('#saAddAccountPop').fadeOut();
            alert('Campaign Successfully Added')
            $scope.fetchOneUser();
        });

    };

    $scope.updateState = function(accountName)
    {
        var account = accountName
        var userId = $scope.response.user.userId;
        var urlBase = $window.location.origin + $window.location.pathname + '/people/' + userId;
        EditUser.updateState(account, userId, urlBase, function(response)
        {
            $scope.fetchOneUser();
        });

    };

    $scope.backToUserList = function()
    {
        $state.go('saPeople.list');
    };

}]);

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider)
{
    $stateProvider.
    state('saDashboard', {

        url: '/dashboard',

        views: {

            'saDashboard': {

                templateUrl: 'saDashboard',
                controller: 'systemAdminDashboardController'

            }
        }

    }).state('saCampaign', {

        url: '/campaigns',

        views: {

            'saCampaign': {

                templateUrl: 'saCampaign',
                controller: 'systemAdminCampaignController',

            }
        }


    }).state('saPeople', {

        views: {

            'user-layout': {

                templateUrl: 'user-layouts'

            }
        }



    }).state('saPeople.list', {

        url: '/people',

        views: {

            'user-list@saPeople': {

                templateUrl: 'user-list',
                controller: 'systemAdminPeopleController'

            }
        }


    }).state('details', {

        parent: 'saPeople',
        url: '/people/{userId}',
        views: {

            'user-details': {

                templateUrl: 'saEditUser',
                controller: 'saEditUserController'
            }

        }


    }).state('saFile', {

        url: '/file',

        views: {

            'saFile': {

                templateUrl: 'saFile',
                controller: 'systemAdminFileController'

            }
        }


    }).state('saArticle', {

        url: '/article',

        views: {

            'saArticle': {

                templateUrl: 'saArticle',
                controller: 'systemAdminArticleController'

            }
        }


    });
    $urlRouterProvider.otherwise('/dashboard');

}]);
