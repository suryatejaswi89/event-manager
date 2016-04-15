'use strict';

angular.module('eventmanagerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hall', {
                parent: 'entity',
                url: '/halls',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'eventmanagerApp.hall.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hall/halls.html',
                        controller: 'HallController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hall');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hall.detail', {
                parent: 'entity',
                url: '/hall/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'eventmanagerApp.hall.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hall/hall-detail.html',
                        controller: 'HallDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hall');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Hall', function($stateParams, Hall) {
                        return Hall.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hall.new', {
                parent: 'hall',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hall/hall-dialog.html',
                        controller: 'HallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    address: null,
                                    capacity: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hall', null, { reload: true });
                    }, function() {
                        $state.go('hall');
                    })
                }]
            })
            .state('hall.edit', {
                parent: 'hall',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hall/hall-dialog.html',
                        controller: 'HallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Hall', function(Hall) {
                                return Hall.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hall', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hall.delete', {
                parent: 'hall',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hall/hall-delete-dialog.html',
                        controller: 'HallDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Hall', function(Hall) {
                                return Hall.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hall', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
