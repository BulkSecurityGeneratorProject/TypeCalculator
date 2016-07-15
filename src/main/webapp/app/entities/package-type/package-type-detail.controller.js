(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('PackageTypeDetailController', PackageTypeDetailController);

    PackageTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PackageType'];

    function PackageTypeDetailController($scope, $rootScope, $stateParams, entity, PackageType) {
        var vm = this;

        vm.packageType = entity;

        var unsubscribe = $rootScope.$on('myappApp:packageTypeUpdate', function(event, result) {
            vm.packageType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
