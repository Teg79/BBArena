//set width and height variables for game
/*eslint-env browser*/
/*globals Phaser */
var width = 780;
var height = 450;
//create game object and initialize the canvas
var game = new Phaser.Game(width, height, Phaser.AUTO, null, {preload: preload, create: create, update: update});

//initialize some variables
var cursors;
var speed = 175;
var square = 30;
var home;
var away;
var playersMap = new Map();
var ball;
var output;

// WS
// var wsUri = "ws://mafateg.no-ip.biz:8888/bbarena/match/m/c";
var wsUri = "ws://" + location.host + "/bbarena-server/match/m/c";
var websocket;

function onOpen(evt)
{
    doSend('Start');
        writeToScreen("CONNECTED");
}

function onClose(evt)
{
        writeToScreen("DISCONNECTED");
}

function onMessage(evt)
{
        writeToScreen('RESPONSE: ' + evt.data);
        var json = JSON.parse(evt.data);
        var envelope = json['bbarena.server.json.Envelope'];
        var type = envelope._type;
        window["fire" + type](envelope._event);
}

function onError(evt)
{
        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend(message)
{
    websocket.send(message);
    writeToScreen("SENT: " + message);
}

function writeToScreen(message)
{
    console.debug(message);
}

function preload() {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpen(evt) };
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };

	//set background color of canvas
	game.stage.backgroundColor = '#093';

	//load assets
	game.load.image('field-nice', 'asset/field/nice.jpg');
	game.load.image('ball-onfield', 'asset/field/icons/ball_onfield.png');
	game.load.image('Chaos-Chaos Warrior', 'asset/icons/chaos/cwarrior1.png');
	game.load.image('chaos-warrior2', 'asset/icons/chaos/cwarrior2.png');
	game.load.image('chaos-warrior3', 'asset/icons/chaos/cwarrior3.png');
	game.load.image('chaos-warrior4', 'asset/icons/chaos/cwarrior4.png');
	game.load.image('Chaos-Beastman', 'asset/icons/chaos/cbeastman1.png');
	game.load.image('chaos-beast2', 'asset/icons/chaos/cbeastman2.png');
	game.load.image('chaos-beast3', 'asset/icons/chaos/cbeastman3.png');
	game.load.image('chaos-beast4', 'asset/icons/chaos/cbeastman4.png');
	game.load.image('Orc-Black Orc Blocker', 'asset/icons/orc/oblackorc1b.png');
	game.load.image('orc-bob2', 'asset/icons/orc/oblackorc2b.png');
	game.load.image('Orc-Blitzer', 'asset/icons/orc/oblitzer1b.png');
	game.load.image('orc-blitzer2', 'asset/icons/orc/oblitzer2b.png');
	game.load.image('orc-blitzer3', 'asset/icons/orc/oblitzer3b.png');
	game.load.image('Orc-Lineorc', 'asset/icons/orc/olineman1b.png');
	game.load.image('orc-line2', 'asset/icons/orc/olineman2b.png');
	game.load.image('orc-line3', 'asset/icons/orc/olineman3b.png');
	game.load.image('orc-thrower1', 'asset/icons/orc/othrower1b.png');
	game.load.image('orc-thrower2', 'asset/icons/orc/othrower2b.png');

    output = document.getElementById("output");
}
function create() {
	//start arcade physics engine
	game.physics.startSystem(Phaser.Physics.ARCADE);
	game.add.image(game.world.centerX, game.world.centerY, 'field-nice').anchor.set(0.5);

	//initialize keyboard arrows for the game controls
	cursors = game.input.keyboard.createCursorKeys();

	ball = game.add.sprite(-square, -square, 'ball-onfield');
	ball.anchor.set(0.5);
	game.physics.enable(ball, Phaser.Physics.ARCADE);

    // crete teams
    home = game.add.group();
    away = game.add.group();

	//enable physics
	game.physics.enable(home, Phaser.Physics.ARCADE);
	game.physics.enable(away, Phaser.Physics.ARCADE);

}
function update() {

}

function fireKickOffBallEvent(msg) {
    ball.x = msg._to.x * square;
    ball.y = msg._to.y * square;
}

function fireScatterBallEvent(msg) {
    ball.x = msg._destination._lastValidSquare.x * square;
    ball.y = msg._destination._lastValidSquare.y * square;
}

function fireTouchBackEvent(msg) {

}

function fireCatchBallEvent(msg) {
}

function firePutPlayerInPitchEvent(msg) {
    playersMap.get(msg.playerId).x = msg._to.x * square;
    playersMap.get(msg.playerId).y = msg._to.y * square;
}

function firePutPlayerInDugoutEvent(msg) {
    var team = msg._coach == 0 ? home : away;
	var player = team.create(-square, square, msg._player.team.roster.race + '-' + msg._player.template.position);
	playersMap.set(msg._player.id, player);
}

function fireMovePlayerEvent(msg) {
    var deltaX = msg._range * square;
    var deltaY = msg._range * square;

    if (msg._direction == 'N') {
        deltaX = -deltaX;
        deltaY = 0;
    } else if (msg._direction == 'NE') {
        deltaY = -deltaY;
    } else if (msg._direction == 'E') {
        deltaX = 0;
    } else if (msg._direction == 'SE') {

    } else if (msg._direction == 'S') {
        deltaX = 0;
    } else if (msg._direction == 'SW') {
        deltaX = -deltaX;
    } else if (msg._direction == 'W') {
        deltaX = -deltaX;
        deltaY = 0;
    } else if (msg._direction == 'NW') {
        deltaX = -deltaX;
        deltaY = -deltaY;
    }

    playersMap.get(msg.playerId).x += deltaX;
    playersMap.get(msg.playerId).y += deltaY;
}
