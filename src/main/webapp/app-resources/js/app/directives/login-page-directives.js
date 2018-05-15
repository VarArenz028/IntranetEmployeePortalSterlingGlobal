var direct = angular.module('sterling.directives', []);

direct.directive('uname', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.uname = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/employee/details';
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
                            scope.newUserDetails.username.$setValidity('unameInputError', false);
                        }
                        else
                        {

                           def.resolve();
                           scope.newUserDetails.username.$setValidity('unameInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);

direct.directive('pass', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.pass = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/employee/details/';
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
                            scope.newUserDetails.password.$setValidity('unameInputError', false);
                        }
                        else
                        {

                           def.resolve();
                           scope.newUserDetails.password.$setValidity('unameInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);
