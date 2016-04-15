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

    });
