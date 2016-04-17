'use strict';

angular.module('eventmanagerApp')
    .controller('HallDetailController', function ($scope, $rootScope, $stateParams, entity, Hall,hallEvents) {
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

        function dbEventsToUEvents(events){
            var uEvents=[];
            for(var i=0;i<events.length;i++){
                var temp = {};
                temp.title= events[i].name;
                temp.start=events[i].startDate;
                temp.end=events[i].endDate;
                uEvents.push(temp);
            }
            return uEvents;
        }

        $scope.eventSources = {
            events: dbEventsToUEvents(hallEvents.data)
        };

    });
