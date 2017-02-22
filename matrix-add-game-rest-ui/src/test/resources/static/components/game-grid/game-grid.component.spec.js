(function () {
  'use strict';

  describe("gameGrid", function () {
    beforeEach(module('gameGrid'));

    var $ctrl;
    beforeEach(inject(function ($componentController) {
      $ctrl = $componentController('gameGrid');
    }));

    describe("initialization", function () {
      it("should have setup background colors", function () {
        expect($ctrl.getCellStyle('')).toEqual({'background-color': '#CCC0B3', 'color': '#776e65'});
        expect($ctrl.getCellStyle('2')).toEqual({'background-color': '#EEE4DA', 'color': '#776e65'});
        expect($ctrl.getCellStyle('4')).toEqual({'background-color': '#EDE0C8', 'color': '#776e65'});
        expect($ctrl.getCellStyle('8')).toEqual({'background-color': '#F2B179', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('16')).toEqual({'background-color': '#f59563', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('32')).toEqual({'background-color': '#f67c5f', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('64')).toEqual({'background-color': '#f65e3b', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('128')).toEqual({'background-color': '#edcf72', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('256')).toEqual({'background-color': '#edcc61', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('512')).toEqual({'background-color': '#edc850', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('1024')).toEqual({'background-color': '#edc53f', 'color': '#f9f6f2'});
        expect($ctrl.getCellStyle('2048')).toEqual({'background-color': '#edc22e', 'color': '#f9f6f2'});
      });

      it('should initialize empty matrix', function () {
        expect($ctrl.internalMatrix.length).toBe(16);
        $ctrl.internalMatrix.forEach(function (e) {
          expect(e).toEqual('')
        });
      });
    });

    describe("api", function () {
      var scope,
        element;

      beforeEach(inject(function ($rootScope, $compile, $templateCache) {
        $templateCache.put('components/game-grid/game-grid.template.html', 'empty');
        scope = $rootScope.$new();
        element = angular.element('<game-grid matrix="matrix"></game-grid>');
        element = $compile(element)(scope);
        scope.$digest();
      }));

      it("should have empty matrix", function () {
        element.isolateScope().$ctrl.internalMatrix.forEach(function (e) {
          expect(e).toEqual('');
        });
      });

      it("should setup correctly matrix on input data", function () {
        scope.matrix = [[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]];
        scope.$apply();
        element.isolateScope().$ctrl.internalMatrix.forEach(function (e) {
          expect(e).toEqual('2');
        });
      });
    });
  });

})();
