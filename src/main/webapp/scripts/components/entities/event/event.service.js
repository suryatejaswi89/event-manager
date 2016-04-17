'use strict';

angular.module('eventmanagerApp')
    .factory('Event', function ($resource, DateUtils) {
        return $resource('api/events/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                    data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    return data;
                }
            },
            'update': {method:'PUT'}
        });
    });
