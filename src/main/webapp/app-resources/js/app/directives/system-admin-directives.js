var direct = angular.module('sterling.directives', []);

direct.directive('campname', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.campname = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/campaign/';
                var value = modelValue || viewValue;
               // console.log(value);

                var def = $q.defer();
                scope.disable = true;
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {campaignName: value} }).then(function(result)
                    {

                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.saCampaignForm.campaignName.$error.campFormError = true;
                            // scope.campaignForm.campaignName.$setValidity('campFormError', false);
                            // scope.editUserForm.campaignName.$setValidity('cNameInputError', false);

                        }
                        else
                        {

                           def.resolve();
                           scope.disable = false;
                           scope.saCampaignForm.campaignName.$error.campFormError = false;
                           // scope.saCampaignForm.campaignName.$setValidity('cNameInputError', true);
                           // scope.editUserForm.campaignName.$setValidity('cNameInputError', true);
                        }

                    });


                }, 1000);


                return def.promise;


            };


        }

    };

}]);

direct.directive('editcampname', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.editcampname = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/campaign/';
                var value = modelValue || viewValue;
               // console.log(value);

                var def = $q.defer();
                scope.disable = true;
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {campaignName: value} }).then(function(result)
                    {

                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            // scope.editUserForm.campaignName.$setValidity('cNameInputError', false);

                        }
                        else
                        {

                           def.resolve();
                           scope.disable = false;
                           // scope.editUserForm.editCampaignName.$setValidity('cNameInputError', true);
                        }

                    });


                }, 1000);


                return def.promise;


            };


        }

    };

}]);

//    For new user form function
direct.directive('uname', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.uname = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/people/';
                var value = modelValue || viewValue;
               // console.log(value);

                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {username: value} }).then(function(result)
                    {

                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.newUserForm.username.$setValidity('unameInputError', false);
                        }
                        else
                        {

                           def.resolve();
                           scope.newUserForm.username.$setValidity('unameInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);

    //    For new user form function
direct.directive('pass', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.pass = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/people/';
                var value = modelValue || viewValue;
               // console.log(value);
                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {password: value} }).then(function(result)
                    {
                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.newUserForm.password.$setValidity('passInputError', false);

                        }
                        else
                        {

                            def.resolve();
                            scope.newUserForm.password.$setValidity('passInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);

direct.directive('eusername', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.eusername = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/people/' + scope.userId + '/';
                var value = modelValue || viewValue;


                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {username: value} }).then(function(result)
                    {
                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.saEditUsernameForm.username.$setValidity('unameInputError', false);

                        }
                        else
                        {

                            def.resolve();
                            scope.saEditUsernameForm.username.$setValidity('unameInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);


direct.directive('epassword', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.epassword = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/people/' + scope.userId + '/';
                var value = modelValue || viewValue;


                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {password: value} }).then(function(result)
                    {
                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.saEditPasswordForm.password.$setValidity('passInputError', false);

                        }
                        else
                        {

                            def.resolve();
                            scope.saEditPasswordForm.password.$setValidity('passInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);

direct.directive('account', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.account = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/people/' + scope.userId + '/';
                var value = modelValue || viewValue;


                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {account: value, userId: scope.userId} }).then(function(result)
                    {
                        console.log(result.data.validatingAccount);
                        if(result.data.validatingAccount === 'CONFLICT')
                        {

                            def.reject();
                            scope.saAddAccountForm.account.$setValidity('accountInputError', false);
                            scope.validAccount = false;
                            console.log('Invalid');
                        }
                        else if(result.data.validatingAccount === 'OK')
                        {

                            def.resolve();
                            scope.saAddAccountForm.account.$setValidity('accountInputError', true);
                            console.log('Valid');
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);
