'use strict';

angular.module('eventmanagerApp').controller('HallDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Hall',
        function($scope, $stateParams, $uibModalInstance, entity, Hall) {

        $scope.hall = entity;
        $scope.load = function(id) {
            Hall.get({id : id}, function(result) {
                $scope.hall = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('eventmanagerApp:hallUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hall.id != null) {
                Hall.update($scope.hall, onSaveSuccess, onSaveError);
            } else {
                Hall.save($scope.hall, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
