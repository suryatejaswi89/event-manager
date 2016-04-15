'use strict';

angular.module('eventmanagerApp')
	.controller('HallDeleteController', function($scope, $uibModalInstance, entity, Hall) {

        $scope.hall = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Hall.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
