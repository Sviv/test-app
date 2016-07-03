'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.
module('activeList').
component('activeList', {
    templateUrl: 'active-list/active-list.template.html',
    controller: ['$http', function ActiveListController($http) {
        var self = this;
        // self.orderProp = 'age';

        $http.get('models/active.model.json').then(function(response) {
            self.list = response.data;
        });

        self.getUnrepeatedDaysNum = function(item) {
            return self.getDays(item.repeatDate);
            /*отдавать фразу; если будет месяц?*/
        }
        self.getDays = function(someDate) {
            var dateNow = new Date();
            var days = Math.floor((dateNow - someDate) / 8.64e+7);

            return days;
        }
        self.getClass = function(item) {
            /*
             * 3 days - success
             * 6 daus - warning
             * 10 days - danger
             */
            var days = self.getDays(item.repeatDate);

            if (days < 3) {
                return "success";
            } else if (days >= 3 && days <= 6) {
                return "warning";
            } else {
                return "danger";
            }

            return "default";
        }
    }]
});
