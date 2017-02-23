(function (angular) {
  'use strict';

  angular
    .module('gameLogic')
    .factory('GameLogic', ['$resource', function ($resource) {
      var self = this;
      self.game = {};

      var gameLogicResource = $resource('/v1/game/:gameId');

      self.execute = function (callback, request) {
        self.game.$promise.then(function () {
          if (request === undefined)
            request = {};
          request.gameId = self.game.id;
          var game = gameLogicResource.get(request, function () {
            callback(game.gameMatrix);
          });
        });
      };

      return {
        startGame: function () {
          self.game = gameLogicResource.get();
        },
        getMatrix: function (callback) {
          self.execute(callback);
        },
        moveRight: function (callback) {
          self.execute(callback, {move: 'RIGHT'});
        },
        moveLeft: function (callback) {
          self.execute(callback, {move: 'LEFT'});
        },
        moveUp: function (callback) {
          self.execute(callback, {move: 'UP'});
        },
        moveDown: function (callback) {
          self.execute(callback, {move: 'DOWN'});
        }
      };
    }]);
})(angular);
