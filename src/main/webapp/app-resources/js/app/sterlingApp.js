var app = angular.module('sterling', ['sterling.controllers', 'sterling.services', 'sterling.directives'])
.config(function($httpProvider)
{

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
