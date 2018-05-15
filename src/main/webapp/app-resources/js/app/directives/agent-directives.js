var direct = angular.module('sterling.directives', []);

// app.directive('ngFileModel', ['$parse', function ($parse) {
//     return {
//         restrict: 'A',
//         link: function (scope, element, attrs) {
//             var model = $parse(attrs.ngFileModel);
//             var isMultiple = attrs.multiple;
//             var modelSetter = model.assign;
//             element.bind('change', function () {
//                 var values = [];
//                 angular.forEach(element[0].files, function (item) {
//                     var value = {
//                        // File Name
//                         name: item.name,
//                         //File Size
//                         size: item.size,
//                         //File URL to view
//                         url: URL.createObjectURL(item),
//                         // File Input Value
//                         _file: item,
//                     };
//                     values.push(value);
//                 });
//                 scope.$apply(function () {
//                     if (isMultiple) {
//                         modelSetter(scope, values);
//                     } else {
//                         modelSetter(scope, values[0]);
//                     }
//                 });
//             });
//         }
//     };
// }]);

direct.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;

          element.bind('change', function()
          {
              scope.$apply(function()
              {
                    modelSetter(scope, element[0].files[0]);
              });
          });
        }


    };
}]);

direct.directive('category', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.category = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/categories/';
                var value = modelValue || viewValue;
               // console.log(value);

                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {categoryName: value} }).then(function(result)
                    {

                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.categoriesForm.category.$setValidity('categoryInputError', false);
                        }
                        else
                        {

                           def.resolve();
                           scope.categoriesForm.category.$setValidity('categoryInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);

direct.directive('onecategory', ['$q', '$timeout', '$window', '$http', function($q, $timeout, $window, $http)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.onecategory = function(modelValue, viewValue)
            {
                var urlBase = $window.location.origin + $window.location.pathname + '/categories/';
                var value = modelValue || viewValue;
//                console.log(value);

                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {categoryName: value} }).then(function(result)
                    {

                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.editCategoryForm.cat.$setValidity('categoryInputError', false);
                        }
                        else
                        {

                           def.resolve();
                           scope.editCategoryForm.cat.$setValidity('categoryInputError', true);
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
                var urlBase = $window.location.origin + $window.location.pathname + '/my-profile';
                var value = modelValue || viewValue;


                var def = $q.defer();
                $timeout(function()
                {

                    return $http.get(urlBase, { params: {password: value} }).then(function(result)
                    {
                        if(result.data.errorCode === 409)
                        {

                            def.reject();
                            scope.caEditPasswordForm.password.$setValidity('passInputError', false);

                        }
                        else
                        {

                            def.resolve();
                            scope.caEditPasswordForm.password.$setValidity('passInputError', true);
                        }

                    });


                }, 2000);


                return def.promise;


            };


        }

    };

}]);

direct.directive('questlength', ['$q', function($q)
{

    return {


        require: 'ngModel',
        link: function(scope, elm, attr, ctrl)
        {
            ctrl.$asyncValidators.questmaxlength = function(modelValue, viewValue)
            {

                var value = modelValue || viewValue;
                var def = $q.defer();

                var max = 150;
                var min = 10;
                // scope.$watch('question.title', function(newValue, oldValue)
                // {
                //
                //     if(newValue != oldValue)
                //     {
                //         console.log('question has been change');
                //     }
                //
                // });
                if(value.length > max)
                {
                    scope.questionForm.questionArea.$setValidity('questMaxLength', false);
                    def.reject();
                }
                else
                {
                    scope.questionForm.questionArea.$setValidity('questMaxLength', true);
                    def.resolve();
                }

                if(value.length < min)
                {
                    scope.questionForm.questionArea.$setValidity('questMinLength', false);
                    def.reject();
                }
                else
                {
                    scope.questionForm.questionArea.$setValidity('questMinLength', true);
                    def.resolve();
                }

                return def.promise;

            };
        }

    };

}]);

direct.directive('comment', ['$q', '$timeout', function($q, $timeout)
{

    return {

        require: 'ngModel',
        link: function(scope, elm, attr, ctrl)
        {
            ctrl.$asyncValidators.questmaxlength = function(modelValue, viewValue)
            {

                var value = modelValue || viewValue;
                var def = $q.defer();

                var max = 150;
                var min = 10;

                scope.ansQuestionCommentForm.$invalid = false;

                $timeout(function()
                {
                    if(value.length > max)
                    {
                        scope.ansQuestionCommentForm.$invalid = true;
                        def.reject();
                    }
                    else
                    {
                        scope.disable = false;
                        // def.resolve();
                    }

                    if(value.length < min)
                    {
                        scope.ansQuestionCommentForm.$invalid = true;
                        def.reject();
                    }
                    else
                    {
                        scope.ansQuestionCommentForm.$invalid = false;
                    }
                }, 10);


                return def.promise;

            };
        }

    };

}]);
