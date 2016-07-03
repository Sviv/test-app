'use strict';

var activeModel = [{
    value: "word-1",
    addDate: "1465516800000",
    repeatDate: "1466035200000",
    inArchive: true
}, {
    value: "word-2",
    addDate: "1465516800000",
    repeatDate: "1466208000000",
    inArchive: false
}, {
    value: "word-3",
    addDate: "1465516800000",
    repeatDate: "1466035200000",
    inArchive: false
}];

angular.module('myApp.active', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/active', {
    templateUrl: 'active/active.html',
    controller: 'ActiveCtrl'
  });
}])

.controller('ActiveCtrl', function() {
	var words = activeModel;
});