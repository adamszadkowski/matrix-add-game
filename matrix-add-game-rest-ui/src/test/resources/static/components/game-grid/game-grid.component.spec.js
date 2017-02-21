(function () {
  'use strict';

  describe("gameGrid", function () {
    beforeEach(module('gameGrid'));
    var element;

    describe("initialization", function () {
      it('should initialize empty matrix', inject(function ($rootScope, $compile) {
        var scope = $rootScope.$new();
        element = $compile(angular.element('<game-grid matrix="matrix"></game-grid>'))(scope);

        var internalScope = element.isolateScope();
        expect(internalScope.internalMatrix.length).toBe(16);
        internalScope.internalMatrix.forEach(function (e) {
          expect(e).toEqual('')
        });
      }));
    });

   /* describe("conversions", function () {
      it("should get correct background color for number", function () {
        expect($ctrl.getBackgroundColorFor('')).toBe('#CCC0B3');
        expect($ctrl.getBackgroundColorFor('2')).toBe('#EEE4DA');
        expect($ctrl.getBackgroundColorFor('2048')).toBe('#edc22e');
        expect($ctrl.getBackgroundColorFor('4096')).toBe('#edc22e');
      });

      it("should get correct text color for number", function () {
        expect($ctrl.getTextColorFor('')).toBe('#776e65');
        expect($ctrl.getTextColorFor('2')).toBe('#776e65');
        expect($ctrl.getTextColorFor('4')).toBe('#776e65');
        expect($ctrl.getTextColorFor('8')).toBe('#f9f6f2');
        expect($ctrl.getTextColorFor('4096')).toBe('#f9f6f2');
      });
    });

    describe("api", function () {
      it("should setup correctly matrix on input data", function () {
        var background = $ctrl.getBackgroundColorFor('');
        var background2 = $ctrl.getBackgroundColorFor('2');
        var background4096 = $ctrl.getBackgroundColorFor('4096');
        var text = $ctrl.getTextColorFor('');
        var text2 = $ctrl.getTextColorFor('2');
        var text4096 = $ctrl.getTextColorFor('4096');
        $ctrl.update([[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 4096]]);
        expect($ctrl.internalMatrix).toEqual([
          {value: '2', color: {background: background2, text: text2}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '', color: {background: background, text: text}},
          {value: '2', color: {background: background2, text: text2}},
          {value: '', color: {background: background, text: text}},
          {value: '2', color: {background: background2, text: text2}},
          {value: '4096', color: {background: background4096, text: text4096}}
        ]);
      });
    });*/
  });

})();
