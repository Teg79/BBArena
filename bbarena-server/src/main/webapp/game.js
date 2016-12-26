//set width and height variables for game
var width = 780;
var height = 450;
//create game object and initialize the canvas
var game = new Phaser.Game(width, height, Phaser.AUTO, null, {preload: preload, create: create, update: update});

//initialize some variables
var player;
var food;
var cursors;
var speed = 175;
var score = 0;
var scoreText;

// WS
var wsUri = "ws://localhost:8080/bbarena-server/match/m/c";
websocket = new WebSocket(wsUri);
websocket.onopen = function(evt) { onOpen(evt) };
websocket.onclose = function(evt) { onClose(evt) };
websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror = function(evt) { onError(evt) };

function onOpen(evt)
{
}

function onClose(evt)
{
}

function onMessage(evt)
{
}

function onError(evt)
{
}

function doSend(message)
{
    websocket.send(message);
}

function preload() {
	//set background color of canvas
	game.stage.backgroundColor = '#093';

	//load assets
	game.load.image('player', 'asset/icons/chaos/cwarrior1.png');
	game.load.image('food', 'asset/icons/chaos/cbeastman1.png');

	game.load.image('field-nice', 'asset/field/nice.jpg');
	game.load.image('chaos-warrior1', 'asset/icons/chaos/cwarrior1.png');
	game.load.image('chaos-warrior2', 'asset/icons/chaos/cwarrior2.png');
	game.load.image('chaos-warrior3', 'asset/icons/chaos/cwarrior3.png');
	game.load.image('chaos-warrior4', 'asset/icons/chaos/cwarrior4.png');
	game.load.image('chaos-beast1', 'asset/icons/chaos/cbeastman1.png');
	game.load.image('chaos-beast2', 'asset/icons/chaos/cbeastman2.png');
	game.load.image('chaos-beast3', 'asset/icons/chaos/cbeastman3.png');
	game.load.image('chaos-beast4', 'asset/icons/chaos/cbeastman4.png');

}
function create() {
	//start arcade physics engine
	game.physics.startSystem(Phaser.Physics.ARCADE);
	game.add.image(game.world.centerX, game.world.centerY, 'field-nice').anchor.set(0.5);

	//initialize keyboard arrows for the game controls
	cursors = game.input.keyboard.createCursorKeys();

	//add player sprite
	player = game.add.sprite(width*0.5, height*0.5, 'player');
	//set anchor point to center of the sprite
	player.anchor.set(0.5);
	//enable physics for the player body
	game.physics.enable(player, Phaser.Physics.ARCADE);
	//make the player collide with the bounds of the world
	player.body.collideWorldBounds = true;

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
	game.physics.enable(food, Phaser.Physics.ARCADE);

}
function update() {

	//move the player up and down based on keyboard arrows
	if (cursors.up.isDown) {
		player.body.velocity.y = -speed;
	}
	else if (cursors.down.isDown) {
		player.body.velocity.y = speed;
	}
	else {
		player.body.velocity.y = 0;
	}

	//move the player right and left based on keyboard arrows
	if (cursors.left.isDown) {
		player.body.velocity.x = -speed;
	}
	else if (cursors.right.isDown) {
		player.body.velocity.x = speed;
	}
	else {
		player.body.velocity.x = 0;
	}

	//call eatFood function when the player and a piece of food overlap
	game.physics.arcade.overlap(player, food, eatFood);
}

//eatFood function
function eatFood(player, food) {
	//remove the piece of food
	food.kill();
	//update the score
	score++;
	scoreText.text = score;
}