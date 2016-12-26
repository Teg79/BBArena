//set width and height variables for game
var width = 780;
var height = 450;
//create game object and initialize the canvas
var game = new Phaser.Game(width, height, Phaser.AUTO, null, {preload: preload, create: create, update: update});

//initialize some variables
var cursors;
var speed = 175;
var home;
var away;

// WS
var wsUri = "ws://localhost:8080/bbarena-server/match/m/c";
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
        writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
        var json = JSON.parse(evt.data);
        var envelope = json["bbarena.server.json.Envelope"];
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
	game.load.image('chaos-warrior1', 'asset/icons/chaos/cwarrior1.png');
	game.load.image('chaos-warrior2', 'asset/icons/chaos/cwarrior2.png');
	game.load.image('chaos-warrior3', 'asset/icons/chaos/cwarrior3.png');
	game.load.image('chaos-warrior4', 'asset/icons/chaos/cwarrior4.png');
	game.load.image('chaos-beast1', 'asset/icons/chaos/cbeastman1.png');
	game.load.image('chaos-beast2', 'asset/icons/chaos/cbeastman2.png');
	game.load.image('chaos-beast3', 'asset/icons/chaos/cbeastman3.png');
	game.load.image('chaos-beast4', 'asset/icons/chaos/cbeastman4.png');
	game.load.image('orc-bob1', 'asset/icons/orc/oblackorc1b.png');
	game.load.image('orc-bob2', 'asset/icons/orc/oblackorc2b.png');
	game.load.image('orc-blitzer1', 'asset/icons/orc/oblitzer1b.png');
	game.load.image('orc-blitzer2', 'asset/icons/orc/oblitzer2b.png');
	game.load.image('orc-blitzer3', 'asset/icons/orc/oblitzer3b.png');
	game.load.image('orc-line1', 'asset/icons/orc/olineman1b.png');
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

	player = game.add.sprite(width*0.5, height*0.5, 'player');
	player.anchor.set(0.5);
	game.physics.enable(player, Phaser.Physics.ARCADE);

    // crete teams
    home = game.add.group();
    away = game.add.group();

	//create a group called food and add 4 food pieces to the game
	food = game.add.group();
	food.create(width*0.1, height*0.1, 'food');
	food.create(width*0.9, height*0.1, 'food');
	food.create(width*0.1, height*0.9, 'food');
	food.create(width*0.9, height*0.9, 'food');
	//set the anchors of their sprites to the center
	for (var i in food.children) {
		food.children[i].anchor.set(0.5);
	}
	//enable physics for the food
	game.physics.enable(home, Phaser.Physics.ARCADE);
	game.physics.enable(away, Phaser.Physics.ARCADE);

}
function update() {

}

function firePutPlayerInPitchEvent(msg) {
	home.create(width*0.1, height*0.1, 'food');
    console.info(msg);
}
