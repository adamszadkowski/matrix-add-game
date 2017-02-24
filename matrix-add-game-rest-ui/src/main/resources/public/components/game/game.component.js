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

        self.callback = function (matrix) {
          self.model = matrix;
        };

        angular.element(document).bind('keyup', function (e) {
          switch (e.keyCode) {
            case 38:
              GameLogic.moveUp(self.callback);
              break;
            case 40:
              GameLogic.moveDown(self.callback);
              break;
            case 37:
              GameLogic.moveLeft(self.callback);
              break;
            case 39:
              GameLogic.moveRight(self.callback);
              break;
          }
        });
      }]
    });

})(angular);
