(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('PackageType', PackageType);

    PackageType.$inject = ['$resource'];

    function PackageType ($resource) {
        var resourceUrl =  'api/package-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
