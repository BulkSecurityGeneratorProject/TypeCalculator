(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('PackageTypeController', PackageTypeController);

    PackageTypeController.$inject = ['$scope', '$state', 'PackageType'];

    function PackageTypeController ($scope, $state, PackageType) {
        var vm = this;
        
        vm.packageTypes = [];

        loadAll();

        function loadAll() {
            PackageType.query(function(result) {
                vm.packageTypes = result;
            });
        }
    }
})();
