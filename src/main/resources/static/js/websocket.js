var ws;

function connect() {
	ws = new WebSocket('ws://localhost:8080/socket');
	ws.onmessage = function(data){
		showGreeting(data.data);
	}
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
	setTimeout(function(){
	    ws.send(JSON.stringify({
	    	name: "Fred",
	    	date: "December"
	    }));
	}, 1000);
}

function showGreeting(message) {
    console.log(message);
}

connect();
sendName();