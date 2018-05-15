var app = angular.module('sterling.controllers', ['ui.router', 'ngAnimate', 'ngCookies', 'ngMessages', 'chart.js']);

app.controller('campaignAdminArticleController', ['$scope', function($scope)
{
    
}]);

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider)
{
    $urlRouterProvider.otherwise('/article-writing');
    $stateProvider.
    state('article-writing', {
       
        url: '/article-writing',
        
        views: {
            'article-writing': {
                
                templateUrl: 'article',
                controller: 'articleController'
                
            }
        }
        
    });

}]);
