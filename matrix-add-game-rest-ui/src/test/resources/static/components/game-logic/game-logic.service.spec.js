(function () {
  'use strict';

  describe('game logic', function () {
    beforeEach(module('gameLogic'));

    var $httpBackend,
      GameLogic;

    beforeEach(inject(function (_$httpBackend_, _GameLogic_) {
      $httpBackend = _$httpBackend_;
      GameLogic = _GameLogic_;
    }));

    var initialMatrix = [[0, 0, 2, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 2, 0]];
    var movedRightMatrix = [[0, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 2]];
    var movedLeftMatrix = [[2, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [2, 0, 0, 0]];
    var movedUpMatrix = [[0, 0, 0, 0], [2, 0, 0, 0], [2, 0, 0, 0], [0, 0, 0, 0]];
    var movedDownMatrix = [[0, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 2], [0, 0, 0, 0]];

    describe('pending game creation', function () {
      beforeEach(function() {
        $httpBackend
          .when('GET', '/v1/game')
          .respond(200, {"id": "c2f97ac8-8483-4677-b416-4a8438d3b692"});

        $httpBackend
          .when('GET', '/v1/game/c2f97ac8-8483-4677-b416-4a8438d3b692')
          .respond(200, {"gameMatrix": initialMatrix});

        $httpBackend
          .when('GET', '/v1/game/c2f97ac8-8483-4677-b416-4a8438d3b692?move=RIGHT')
          .respond(200, {"gameMatrix": movedRightMatrix});

        $httpBackend
          .when('GET', '/v1/game/c2f97ac8-8483-4677-b416-4a8438d3b692?move=LEFT')
          .respond(200, {"gameMatrix": movedLeftMatrix});

        $httpBackend
          .when('GET', '/v1/game/c2f97ac8-8483-4677-b416-4a8438d3b692?move=UP')
          .respond(200, {"gameMatrix": movedUpMatrix});

        $httpBackend
          .when('GET', '/v1/game/c2f97ac8-8483-4677-b416-4a8438d3b692?move=DOWN')
          .respond(200, {"gameMatrix": movedDownMatrix});
      });

      beforeEach(function () {
        GameLogic.startGame();
      });

      it('should be able to get matrix', function () {
        var executed = false;
        GameLogic.getMatrix(function (matrix) {
          executed = true;
          expect(matrix).toEqual(initialMatrix);
        });

        expect(executed).toBe(false);
        $httpBackend.flush();
        expect(executed).toBe(true);
      });

      it('should be able to move right', function () {
        var executed = false;
        GameLogic.moveRight(function (matrix) {
          executed = true;
          expect(matrix).toEqual(movedRightMatrix);
        });

        expect(executed).toBe(false);
        $httpBackend.flush();
        expect(executed).toBe(true);
      });

      it('should be able to move left', function () {
        var executed = false;
        GameLogic.moveLeft(function (matrix) {
          executed = true;
          expect(matrix).toEqual(movedLeftMatrix);
        });

        expect(executed).toBe(false);
        $httpBackend.flush();
        expect(executed).toBe(true);
      });

      it('should be able to move up', function () {
        var executed = false;
        GameLogic.moveUp(function (matrix) {
          executed = true;
          expect(matrix).toEqual(movedUpMatrix);
        });

        expect(executed).toBe(false);
        $httpBackend.flush();
        expect(executed).toBe(true);
      });

      it('should be able to move down', function () {
        var executed = false;
        GameLogic.moveDown(function (matrix) {
          executed = true;
          expect(matrix).toEqual(movedDownMatrix);
        });

        expect(executed).toBe(false);
        $httpBackend.flush();
        expect(executed).toBe(true);
      });
    });
  });
})();
