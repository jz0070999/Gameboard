// create the 3 buttons in here 
// create a back button in here too 

// For sprint 1 make sure it is passing the correct game id to the backend service. 
let currentGameId = null;
let currentGameStatus = null;

function showMessage(message) {
    let banner = document.querySelector("#messageBanner");
    if (banner == null) {
        return;
    }

    banner.innerText = message;
    banner.style.display = "block";
}

function clearMessage() {
    let banner = document.querySelector("#messageBanner");
    if (banner == null) {
        return;
    }

    banner.innerText = "";
    banner.style.display = "none";
}

function makemove(col, row) {
    if (currentGameStatus != "Active") {
        if (currentGameStatus != null) {
            showMessage("The game is not active");
        }
        return;
    }

    clearMessage();

    let options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ column: col, row: row })
    }

    fetch(`http://localhost:8080/api/game/${currentGameId}/move`, options)
        .then(response => { return response.json() })
        .then(data => {
            if (data.isSuccess != true) {
                showMessage(data.message.join(" "));
                console.log(data);
                return;
            }

            updateBoard(data);
            currentGameStatus = data.data.status;
            if (currentGameStatus == "Complete") {
                showMessage("You won the game!");
            }
            console.log(data);
        });
}


// click handler 
function handleClick(col, row) {
    makemove(col, row);
}

// update board UI
function updateBoard(data) {
    if (data == null || data.data == null) {
        return;
    }

    for (let x = 0; x <= 3; x++) {
        for (let y = 0; y <= 3; y++) {

            let elementid = `#cell_${x}_${y}`;
            let elementtext = `.`;

            let element = document.querySelector(elementid);
            element.classList.remove("selected");
        }
    }

    // highlight the selected cell
    let column = data.data.column;
    let row = data.data.row;

    let elementid = `#cell_${column}_${row}`;

    let element = document.querySelector(elementid);
    element.classList.add("selected");
}



function ShowGame(gameid) {

    // make sure "Create New Game" and "Show Active Games" always use the correct game id 
    currentGameId = gameid;
    currentGameStatus = null;

    CreateBoard();

    let url = `http://localhost:8080/api/game/${gameid}`;

    fetch(url)
        .then(response => { return response.json() })
        .then(game => {
            if (game.isSuccess == true) {
                console.log(game.data.id);
                currentGameStatus = game.data.status;
                updateBoard(game);
                if (currentGameStatus != "Active") {
                    showMessage("The game is not active");
                }
            } else {
                showMessage(game.message.join(" "));
            }
        });
}


function CreateBoard() {

    let cellUI = '';
    for (let x = 0; x <= 3; x++) {
        for (let y = 0; y <= 3; y++) {
            cellUI += `<div id="cell_${x}_${y}" class="cellgrid" onclick="handleClick(${x},${y})"></div>`;
        }
    }

    let backButton = `<div class="backbutton" onclick="ShowMenu()">\u2190 Back </div>`;
    let messageBanner = `<div id="messageBanner" class="messagebanner"></div>`;

    let boardUI = `<div class="gamecontainer">${backButton}${messageBanner}<div class="boardgrid">${cellUI}</div></div>`;
    let body = document.querySelector(".body");
    body.innerHTML = boardUI;

}

function ShowMenu() {
    currentGameStatus = null;

    let body = document.querySelector(".body");
    // use back ticks like Florin talked about 
    // make button clickable 
    body.innerHTML = `
         <div class ="menu">
             <div class="menubutton" onclick="CreateNewGame()">Create New Game</div>
             <div class="menubutton" onclick="ShowActiveGames()">Show Active Games</div>
             <div class="menubutton">Generic Option 3</div>
         </div>
    `; // use back ticks in JavaScript liked Florin talked about, it allows you to write multi-line strings and embed expressions easily.
}

function CreateNewGame() {
    console.log("clicked");

    let options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ playerid: 1 })
    };
    fetch('http://localhost:8080/api/game', options)
        .then(response => { return response.json() })
        .then(data => {
            console.log(data);
            currentGameId = data.data.id; // save id here
            // go to the new game 
            ShowGame(currentGameId);
        });
}

function ShowActiveGames() {
    console.log("show active games clicked");
    let playerId = 1; // hardcoded for now, will need to be dynamic later
    let url = `http://localhost:8080/api/player/${playerId}/games/active`;
    let body = document.querySelector(".body");

    fetch(url)
        .then(response => { return response.json() })
        .then(games => {
            console.log(games);
            // display active games
            body.innerHTML = ""; // clear the body content
            let backButton = `<div class="backbutton" onclick="ShowMenu()">\u2190 Back </div>`;
            body.innerHTML = backButton; // add back button
            body.appendChild(document.createElement("h2")).innerText = "Active Games";
            if (games.isSuccess != true) {
                let message = document.createElement("div");
                message.classList.add("messagebanner");
                message.style.display = "block";
                message.innerText = games.message.join(" ");
                body.appendChild(message);
                return;
            }

            for (let i = 0; i < games.data.length; i++) {
                let game = games.data[i];

                let gameButton = document.createElement("div");
                gameButton.classList.add("gamebutton");
                gameButton.innerText = `Load Game ${game.id}`;

                gameButton.onclick = function () {
                    currentGameId = game.id;
                    ShowGame(currentGameId);
                };

                body.appendChild(gameButton);
            }
        });
}


fetch("http://localhost:8080/api/login/player/1", {
    method: "POST"
}); 

// then show the menu ShowGame(1);
ShowMenu(); 
