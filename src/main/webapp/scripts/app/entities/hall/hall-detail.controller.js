'use strict';

angular.module('eventmanagerApp')
    .controller('HallDetailController', function ($scope, $rootScope, $stateParams, entity, Hall) {
        $scope.hall = entity;
        $scope.load = function (id) {
            Hall.get({id: id}, function(result) {
                $scope.hall = result;
            });
        };
        var unsubscribe = $rootScope.$on('eventmanagerApp:hallUpdate', function(event, result) {
            $scope.hall = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.eventSources = {
            events: [
                {
                    title: 'Event1',
                    start: 'Apr 12, 2016 12:12:00 PM'
                },
                {
                    title: 'Event2',
                    start: '2016-04-21'
                }
                // etc...
            ]
        };

    });
