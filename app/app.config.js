'use strict';

angular.
module('myApp').
config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');

        $routeProvider.
        when('/active', {
            template: '<active-list></active-list>'
        }).
        when('/history', {
            template: '<history-list></history-list>'
        }).
        when('/examine', {
            template: '<examine-list></examine-list>'
        }).

        otherwise('/active');
    }
]);
