(function (angular) {
  'use strict';

  angular
    .module('game')
    .component('game', {
      templateUrl: 'components/game/game.template.html',
      controller: ['GameLogic', function (GameLogic) {
        var self = this;
        GameLogic.startGame();
        GameLogic.getMatrix(function (matrix) {
          self.model = matrix;
        });
        this.update = function () {
          GameLogic.moveRight(function (matrix) {
            self.model = matrix;
          });
        };
      }]
    });

})(angular);
