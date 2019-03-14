package bbarena.model.event.game

/**
 * Created by Teg on 02/11/16.
 */
class ThrowInBallEvent : ScatterBallEvent {

    constructor(ballId: Int) : super(ballId) {}

    constructor(ballId: Int, direction: Int) : super(ballId, direction) {}

    constructor(ballId: Int, direction: Int, distance: Int) : super(ballId, direction, distance) {}
}
