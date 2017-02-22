(function (angular) {
  'use strict';

  angular
    .module('gameGrid')
    .component('gameGrid', {
      templateUrl: 'components/game-grid/game-grid.template.html',
      bindings: {
        matrix: '<'
      },
      controller: ['$scope', function ($scope) {
        var self = this;
        if (self.matrix === undefined)
          self.matrix = [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]];

        self.internalMatrix = convertToInternal(self.matrix);
        self.getCellStyle = getCellStyle;

        $scope.$watch(function () {
          return self.matrix;
        }, function (value) {
          if (isCompatible(value))
            self.internalMatrix = convertToInternal(value);
        });
      }]
    });

  var colorMapping = setupColorMappings();

  function convertToInternal(matrix) {
    var transposed = transposeMatrix(matrix);

    var internalMatrix = [];
    for (var i = 0; i < transposed.length; i++)
      for (var j = 0; j < transposed[i].length; j++)
        internalMatrix.push(createCell(transposed[i][j]));

    return internalMatrix;
  }

  function getCellStyle(cell) {
    return {
      'background-color': getBackgroundColorFor(cell),
      'color': getTextColorFor(cell)
    };
  }

  function isCompatible(value) {
    if (!isArrayOfLengthFour(value))
      return false;

    for (var i = 0; i < 4; ++i)
      if (!isArrayOfLengthFour(value[i]))
        return false;

    return true;
  }

  function isArrayOfLengthFour(value) {
    return Array.isArray(value) && value.length == 4;
  }

  function transposeMatrix(matrix) {
    return matrix[0].map(function (col, i) {
      return matrix.map(function (row) {
        return row[i];
      })
    });
  }

  function getBackgroundColorFor(value) {
    var colors = colorMapping.background;
    for (var i = 0; i < colors.length; i++)
      if (colors[i].value == value)
        return colors[i].color;

    return getBackgroundColorFor(2048);
  }

  function getTextColorFor(value) {
    return value == '' || value < 8 ? colorMapping.text.dark : colorMapping.text.light;
  }

  function createCell(el) {
    return el == 0 ? '' : el.toString();
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
