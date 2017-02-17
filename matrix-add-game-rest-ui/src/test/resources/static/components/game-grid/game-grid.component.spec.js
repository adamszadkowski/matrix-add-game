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
        expect($ctrl.colorMapping.background).toContain({value: '', color: '#CCC0B3'});
        expect($ctrl.colorMapping.background).toContain({value: '2', color: '#EEE4DA'});
        expect($ctrl.colorMapping.background).toContain({value: '4', color: '#EDE0C8'});
        expect($ctrl.colorMapping.background).toContain({value: '8', color: '#F2B179'});
        expect($ctrl.colorMapping.background).toContain({value: '16', color: '#f59563'});
        expect($ctrl.colorMapping.background).toContain({value: '32', color: '#f67c5f'});
        expect($ctrl.colorMapping.background).toContain({value: '64', color: '#f65e3b'});
        expect($ctrl.colorMapping.background).toContain({value: '128', color: '#edcf72'});
        expect($ctrl.colorMapping.background).toContain({value: '256', color: '#edcc61'});
        expect($ctrl.colorMapping.background).toContain({value: '512', color: '#edc850'});
        expect($ctrl.colorMapping.background).toContain({value: '1024', color: '#edc53f'});
        expect($ctrl.colorMapping.background).toContain({value: '2048', color: '#edc22e'});
      });

      it("should have setup text colors", function () {
        expect($ctrl.colorMapping.text.dark).toBe('#776e65');
        expect($ctrl.colorMapping.text.light).toBe('#f9f6f2');
      });

      it('should initialize empty matrix', function () {
        expect($ctrl.internalMatrix.length).toBe(16);
        $ctrl.internalMatrix.forEach(function (e) {
          expect(e).toEqual({
            value: '',
            color: {
              background: '#CCC0B3',
              text: '#776e65'
            }
          })
        });
      });
    });

    describe("conversions", function () {
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
    });
  });

})();
