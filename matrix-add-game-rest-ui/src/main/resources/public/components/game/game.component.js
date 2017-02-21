(function (angular) {
  'use strict';

  angular
    .module('game')
    .component('game', {
      templateUrl: 'components/game/game.template.html',
      controller: function () {
        var self = this;
        this.model = [[0, 0, 0, 0], [0, 2, 4, 8], [0, 0, 2048, 0], [128, 64, 0, 0]];
        this.update = function () {
          self.model = [[0, 4, 0, 2], [0, 2, 4, 8], [0, 0, 2048, 0], [128, 64, 0, 0]];
        };
      }
    });

})(angular);
