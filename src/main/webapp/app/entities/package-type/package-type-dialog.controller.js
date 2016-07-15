(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('PackageTypeDialogController', PackageTypeDialogController);

    PackageTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PackageType'];

    function PackageTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PackageType) {
        var vm = this;

        vm.packageType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.packageType.id !== null) {
                PackageType.update(vm.packageType, onSaveSuccess, onSaveError);
            } else {
                PackageType.save(vm.packageType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:packageTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
