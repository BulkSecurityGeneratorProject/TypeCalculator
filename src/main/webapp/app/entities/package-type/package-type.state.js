(function() {
    'use strict';

    angular
        .module('myappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('package-type', {
            parent: 'entity',
            url: '/package-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.packageType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/package-type/package-types.html',
                    controller: 'PackageTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('packageType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('package-type-detail', {
            parent: 'entity',
            url: '/package-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.packageType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/package-type/package-type-detail.html',
                    controller: 'PackageTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('packageType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PackageType', function($stateParams, PackageType) {
                    return PackageType.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('package-type.new', {
            parent: 'package-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-type/package-type-dialog.html',
                    controller: 'PackageTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                name: null,
                                description: null,
                                rule: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('package-type', null, { reload: true });
                }, function() {
                    $state.go('package-type');
                });
            }]
        })
        .state('package-type.edit', {
            parent: 'package-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-type/package-type-dialog.html',
                    controller: 'PackageTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PackageType', function(PackageType) {
                            return PackageType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('package-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('package-type.delete', {
            parent: 'package-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-type/package-type-delete-dialog.html',
                    controller: 'PackageTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PackageType', function(PackageType) {
                            return PackageType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('package-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
