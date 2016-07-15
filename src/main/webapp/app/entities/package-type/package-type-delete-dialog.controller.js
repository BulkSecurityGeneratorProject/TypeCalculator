(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('PackageTypeDeleteController',PackageTypeDeleteController);

    PackageTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PackageType'];

    function PackageTypeDeleteController($uibModalInstance, entity, PackageType) {
        var vm = this;

        vm.packageType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PackageType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
