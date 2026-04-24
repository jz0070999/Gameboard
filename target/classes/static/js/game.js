// create the 3 buttons in here 
// create a back button in here too 

// For sprint 1 make sure it is passing the correct game id to the backend service. 
let currentGameId = null;


function makemove(col, row) {

    let options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ column: col, row: row })
    }

    fetch(`http://localhost:8080/api/game/${currentGameId}/move`, options)
        .then(response => { return response.json() })
        .then(data => {
            updateBoard(data);
            console.log(data);
        });
}


// click handler 
function handleClick(col, row) {
    makemove(col, row);
}

// update board UI
function updateBoard(data) {

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

    CreateBoard();

    let url = `http://localhost:8080/api/game/${gameid}`;

    fetch(url)
        .then(response => { return response.json() })
        .then(game => {
            if (game.isSuccess == true) {
                console.log(game.data.id);
                updateBoard(game);
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

    let boardUI = `<div class="gamecontainer">${backButton}<div class="boardgrid">${cellUI}</div></div>`;
    let body = document.querySelector(".body");
    body.innerHTML = boardUI;

}

function ShowMenu() {

    let body = document.querySelector(".body");
    // use back ticks like Florin talked about 
    // make button clickable 
    body.innerHTML = `
         <div class ="menu">
             <div class="menubutton" onclick="CreateNewGame()">Create New Game</div>
             <div class="menubutton">Generic Option 2</div>
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

// ShowGame(1);
ShowMenu(); 
