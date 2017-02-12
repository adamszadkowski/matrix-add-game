(function (angular) {
  'use strict';

  angular
    .module('gameGrid')
    .component('gameGrid', {
      templateUrl: 'components/game-grid/game-grid.template.html',
      controller: function () {
        var self = this;

        self.colorMapping = setupColorMappings();

        self.getBackgroundColorFor = function (value) {
          var colors = self.colorMapping.background;
          for (var i = 0; i < colors.length; i++)
            if (colors[i].value == value)
              return colors[i].color;

          return self.getBackgroundColorFor(2048);
        };

        self.getTextColorFor = function (value) {
          if (value == '' || value < 8)
            return self.colorMapping.text.dark;
          else
            return self.colorMapping.text.light;
        };

        self.matrix = initializeMatrix(self);

        self.update = function (matrix) {
          var newArray = matrix[0].map(function (col, i) {
            return matrix.map(function (row) {
              return row[i]
            })
          });
          self.matrix = [];
          for (var i = 0; i < newArray.length; i++) {
            for (var j = 0; j < newArray[i].length; j++) {
              var el = newArray[i][j];
              self.matrix.push(createCell(self, el == 0 ? '' : el.toString()));
            }
          }
        };
      }
    });

  function initializeMatrix(self) {
    var matrix = [];
    for (var i = 0; i < 16; i++)
      matrix.push(createCell(self, ''));
    return matrix;
  }

  function createCell(self, value) {
    return {
      value: value,
      color: {
        background: self.getBackgroundColorFor(value),
        text: self.getTextColorFor(value)
      }
    };
  }

  function setupColorMappings() {
    return {
      background: [
        {value: '', color: '#CCC0B3'},
        {value: '2', color: '#EEE4DA'},
        {value: '4', color: '#EDE0C8'},
        {value: '8', color: '#F2B179'},
        {value: '16', color: '#f59563'},
        {value: '32', color: '#f67c5f'},
        {value: '64', color: '#f65e3b'},
        {value: '128', color: '#edcf72'},
        {value: '256', color: '#edcc61'},
        {value: '512', color: '#edc850'},
        {value: '1024', color: '#edc53f'},
        {value: '2048', color: '#edc22e'}
      ],
      text: {
        dark: '#776e65',
        light: '#f9f6f2'
      }
    };
  }
})(angular);
