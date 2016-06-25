'use strict';

// Declare app level module which depends on views, and components
// angular.module('myApp', [
//   'ngRoute',
//   'myApp.view1',
//   'myApp.view2',
//   'myApp.version'
// ]).
// config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
//   $locationProvider.hashPrefix('!');

//   $routeProvider.otherwise({redirectTo: '/view1'});
// }]);

// my code
var wordsListModel = [{
    value: "word-1",
    addDate: "1465516800000",
    repeatDate: "1466035200000",
    inArchive: true,
    class: "warning"
}, {
    value: "word-2",
    addDate: "1465516800000",
    repeatDate: "1466208000000",
    inArchive: false,
    class: "danger"
}, {
    value: "word-3",
    addDate: "1465516800000",
    repeatDate: "1466035200000",
    inArchive: false,
    class: "success"
}];
var userModel = {
    name: "SillyUser"
}
angular.module('myApp', [])
    .filter('archive', function() {
        return function(input, inArchive) {
            input = input || '';
            var out = [];
            for (var i = 0; i < input.length; i++) {
                if (input[i].inArchive == inArchive) {
                    out.push(input[i]);
                }
            }
            return out;
        };
    })
    // .filter('needRepeat', function() {
    //     return function(input, repeat) {
    //     	checkReapet = function (input) {
    //     		// body...
    //     	}
    //         input = input || '';
    //         if (repeat == true) {
    //             var out = [];
    //             // for (var i = 0; i < input.length; i++) {
    //             //     if (input[i].inArchive == inArchive) {
    //             //         out.push(input[i]);
    //             //     }
    //             // }
    //             for (var i = 0; i < input.length; i++) {
    //                 if (input[i].inArchive == false) {
    //                     if ($scope.checkReapet(input[i].repeatDate) == true) {
    //                         out.push(input[i]);
    //                     }
    //                     if ($scope.getDays(input) >= 3) {
    //                         return true;
    //                     }
    //                 }
    //             }
    //             return out;
    //         }
    //         return input;
    //     };
    // })
    .controller('MyAppCtrl', function MyAppCtrl($scope) {
        $scope.words = wordsListModel;
        $scope.user = userModel;
        $scope.inArchive = true;

        $scope.addWord = function() {
            var dateNow = new Date();
            var value = {
                value: $scope.query,
                addDate: dateNow,
                repeatDate: dateNow,
                inArchive: false
            };
            $scope.words.push(value);
            $scope.query = "";
        }

        $scope.getDays = function(someDate) {
            var dateNow = new Date();
            var days = Math.floor((dateNow - someDate) / 8.64e+7);

            return days;
        }

        $scope.getClass = function(item) {
            /*
             * 3 days - success
             * 6 daus - warning
             * 10 days - danger
             */
            //var dateNow = new Date();
            var days = $scope.getDays(item.repeatDate);

            if (days < 3) {
                return "success";
            } else if (days >= 3 && days <= 6) {
                return "warning";
            } else {
                return "danger";
            }

            return "default";
        }
        $scope.getUnrepeatedDaysNum = function(item) {
            return $scope.getDays(item.repeatDate);
            /*отдавать фразу; если будет месяц?*/
        }

        $scope.getUnrepeatedWordsCount = function() {
            var count = 0,
                days = 0;
            for (var i = 0; i < $scope.words.length; i++) {
                if ($scope.words[i].inArchive == false) {
                    if ($scope.checkReapet($scope.words[i].repeatDate) == true) {
                        count++;
                    }

                }
            }
            return count;
        }
        $scope.checkReapet = function(input) {
            if ($scope.getDays(input) >= 3) {
                return true;
            }
        }
        $scope.getInArchiveWordsCount = function() {
            var count = 0;
            for (var i = 0; i < $scope.words.length; i++) {
                if ($scope.words[i].inArchive == true) {
                    count++;
                }
            }
            return count;
        }
        $scope.getActiveWordsCount = function() {
            var count = 0;
            for (var i = 0; i < $scope.words.length; i++) {
                if ($scope.words[i].inArchive == false) {
                    count++;
                }
            }
            return count;
        }
        $scope.inArchive = function() {
            return false;
        }

        $scope.showActive = function() {
            $scope.inArchive = function() {
                return false;
            }
        }
        $scope.showArchive = function() {
            $scope.inArchive = function() {
                return true;
            }
        }
        $scope.needRepeat = function() {
            return true;
        }
        $scope.wordsNeedRepeat = function() {
            return true;
        }
        $scope.showUnrepited = function() {
            $scope.wordsNeedRepeat = function() {
                return false;
            }
        }
        $scope.showTranslate = function (className) {
        	console.log("className", className);
        	if(!className | className == "hide"){
        		className = "show";
        	}
        }
    })
