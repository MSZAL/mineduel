var currentSquareX = -1;
var currentSquareY = -1;
var new_player = "y";
var currentGameId;
var board = document.getElementById("board");
var ws;

/*
 * Generates div's that represent the squares
 */
function generateBoard() {
    var size = 16;

    /*
     * click, mouseout, mouseover -> track mouse
     * contextmenu -> need to return false to disable default right click pop up
     */
    board.addEventListener("click", leftClickSquare); 
    board.addEventListener("mouseout", mouseOut); 
    board.addEventListener("mouseover", mouseOver);
    board.addEventListener("contextmenu", rightClickSquare);   
    
    var i;
    var j;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            board.innerHTML = board.innerHTML + 
                "<div id=\"" + i + "-" + j + "\" class=\"squareblank\"></div>";
        }
    }
}

/*
 *  Send request to check with server what value the square clicked should get
 */
function leftClickSquare(event) {
    const url='http://localhost:8080/move';
    
//    console.log(document.getElementById(currentSquareX + "-" + currentSquareY).className + ' ')
//    				.indexOf(' ' + "bombflag");
    if (currentSquareX == -1 || currentSquareY == -1) {
    	return;
    }
    
    var square = document.getElementById(currentSquareX + "-" + currentSquareY);
    
    if (square.className != "squareblank") {
    	return;
    }
    
    sendMoveData();
    
//    (async () => {    	
//        const rawResponse = await fetch(url, {
//            method: 'POST',
//            headers: {
//                        'Accept': 'application/json',
//                        'Content-Type': 'application/json'
//                      },
//            body: JSON.stringify({newPlayer: new_player, gameId: currentGameId, squareX: currentSquareX, squareY: currentSquareY})
//            });
//        const content = await rawResponse.json();
//    	
//       
//    })();
}

// Add/Remove flag
function rightClickSquare(event) {
    event.preventDefault();
    var square = document.getElementById(currentSquareX + "-" + currentSquareY);

    if (square.className == "bombflag") {
    	square.className = "squareblank";
    }
    else if (square.className == "squareblank") {
    	square.className = "bombflag";
    }
    else {
    	
    }
}

function mouseOver(event) {
    // Translate x & y pos to correct square
    
    var x = event.clientX;
	var y = event.clientY;

    elemRect = board.getBoundingClientRect();
    console.log(elemRect);
	var squareY = (x - Math.floor(elemRect.left))/16;
	var squareX = (y - Math.floor(elemRect.top))/16;
	
    currentSquareX = Math.floor(squareX);
	currentSquareY = Math.floor(squareY);
	
//	console.log("CURRENT SQUARE: " + currentSquareX + " " + currentSquareY);
}

/*
 * Current square becomes a default of -1
 * meaning it is not hovering out anything
 */
function mouseOut(event) {
	currentSquareX = -1;
	currentSquareY = -1;
	console.log(currentSquareX + " " + currentSquareY);
}

function getGameId() {
	var full = window.location.pathname;
	var res = full.split("/");
	return res[2];
}

function connect() {
	ws = new WebSocket('ws://localhost:8080/socket');
	ws.onmessage = data => {
		handleBoardData(data.data);
	}
	ws.onopen = () => {
		sendNewConnectionData();
	}
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    console.log("Disconnected");
}

function sendNewConnectionData() {
	var data = JSON.stringify({newPlayer: new_player, gameId: currentGameId, squareX: currentSquareX, squareY: currentSquareY})
	ws.send(data);
	new_player = "n";
}

function sendMoveData() {
	var data = JSON.stringify({newPlayer: new_player, gameId: currentGameId, squareX: currentSquareX, squareY: currentSquareY})
	ws.send(data);
}

function handleBoardData(data) {
	var content = JSON.parse(data);
	
	if (content == "{}" || content == "")
		return;
	
    for (var key in content) {
        if (content.hasOwnProperty(key)) {

            if (content[key] == -1) {
                document.getElementById(key)
                    .className = "bombdeath";
            }
            else if (content[key] == 0) {
                document.getElementById(key)
                    .className = "squareopen";
            }
            else {
                document.getElementById(key)
                    .className = "square" + content[key];
            }
        }
    }
}

currentGameId = getGameId();
generateBoard();
connect();
