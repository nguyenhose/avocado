/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global listObject */

index = 0;
testIndex = 0;
_id = '';
listObject = []
score = 0;
document.onkeydown = checkKey;

function checkKey(e) {
    e = e || window.event;
    if (e.keyCode == '38') {
        // up arrow
        upSideDown();
    }
    else if (e.keyCode == '40') {
        // down arrow
        upSideDown();
    }
    else if (e.keyCode == '37') {
        // left arrow
        previous();
    }
    else if (e.keyCode == '39') {
        // right arrow
        next();
    }
}
function init() {
    toggleContent();
    document.getElementById('search-library').addEventListener('keypress', searchLibraryInput);
    document.getElementById('key_word').addEventListener('keypress', searchOnlineInput);
    document.getElementById('test-input').addEventListener('keypress', testInput);
    document.getElementById('key_word').focus();
}

//LIBRARY TAB
function searchLibraryInput(evt) {
    if (evt.keyCode === 13)
        searchLibrary();
}
function searchLibrary() {
    var keyword = document.getElementById("search-library").value;
    if (keyword.trim() !== "") {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
            {
                document.getElementById("public-album").innerHTML = xmlhttp.responseText;
            }
        };
        xmlhttp.open("GET", "/avocado/ViewLibrary?keyword=" + keyword, true);
        xmlhttp.send();
    } else {
        alert('Empty text!')
    }

}

//NEWWORD TAB
function searchOnlineInput(evt) {
    if (evt.keyCode === 13)
        searchOnline();
}
function searchOnline() {
    var xmlhttp;
    var name = document.getElementById("key_word").value;
    if (name.trim() !== "") {
        document.getElementById("result-content").style.display = 'inline-block';
        document.getElementById("result").style.display = 'block';
        document.getElementById("result").innerHTML = 'Loading...';
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
            {


                document.getElementById("message").style.display = 'none';
                if ("Not Found." !== xmlhttp.responseText) {
                    document.getElementById("button-save").style.display = 'block';
                    document.getElementById("result").innerHTML = xmlhttp.responseText;
                    viewDropdown();
                } else {
                    document.getElementById("search-des").style.display = 'Not Found.';
                }
            }
        };
        xmlhttp.open("GET", "/avocado/CreateWord?word=" + name, true);
        xmlhttp.send();
    }
    else {
        alert('Keyword is empty');
    }
}
//TEST
function testInput(evt) {
    if (evt.keyCode === 13)
        checkAndNext();
}
function checkAndNext() {
    var inputVal = document.getElementById('test-input').value.trim();
    if (inputVal === listObject[testIndex].key)
    {
        score = score + 1;
    }
    else {
        listObject[testIndex].input = inputVal;
    }
    testIndex = testIndex + 1;
    if (listObject.length > testIndex) {
        startQuiz();
    }
    else {
        showResult();
    }
    inputVal = document.getElementById('test-input').value = "";
}
function showTest() {
    tm = document.getElementById("test-modal");
    tm.style.visibility = (tm.style.visibility == "visible") ? "hidden" : "visible";
    startTestMode();
}

//HELPER FUNCTION
function viewDropdown() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("avc-add-button").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", "/avocado/ViewAlbums?mode=dropdown", true);
    xmlhttp.send();
}
function showDropdown() {
    var event;
    var dropdown = document.getElementById('avc-dropdown');
    event = document.createEvent('MouseEvents');
    event.initEvent('mousedown', true, true, window);
    dropdown.dispatchEvent(event);
}
//ALBUMS
function showListWord() {
    var listCard = document.getElementsByClassName('card-front');
    var wordZone = document.getElementById('list-horizone-words');
    var shareStatus = document.getElementsByClassName('avc-album-item album-active')[0].id;
    if (shareStatus.split('.')[1] === 'true') {
        document.getElementById('album-status').innerText = 'un-publish'
    } else {
        document.getElementById('album-status').innerText = 'Publish'

    }
    var listWord = "";
    if (listCard.length > 0) {
        for (var i = 0; i < listCard.length; i++) {
            listWord = "<div class='item-word'>" + listCard[i].innerText +
                    "</div>" + listWord;
        }
    }
    wordZone.innerHTML = listWord;
}
function addToAlbum() {
    var myselect = document.getElementById("avc-dropdown");
    var selectValue = myselect.options[myselect.selectedIndex].value;
    if (selectValue !== 'new-album') {
        saveToAlbum(selectValue);
    }
    else {
        showPopUp();
    }
}
function showPopUp() {
    var newAlbum = prompt("Please enter your new album name", "my-new-album");
    if (newAlbum !== null) {
        saveToAlbum(newAlbum, 'newAlbum');
    }
}
function startLearnMode() {
    var listWord = document.getElementsByClassName('effect-click');
    var ctrl = document.getElementsByClassName('controller');
    ctrl[0].style.display = 'block';
    index = 0;
    listWord[0].style.display = 'block';
    registerClickEvent();
}
function startTestMode() {
    listObject = [];
    testIndex = 0;
    score = 0;
    var listWord = document.getElementsByClassName('card-text');
    var listMeaning = document.getElementsByClassName('my-definition');
    for (var i = 0; i < listWord.length; i++) {
        listObject.push({key: listWord[i].innerText,
            meaning: listMeaning[i].innerText,
            right: "true",
            input: ""})
    }
    startQuiz();
}
function startQuiz() {
    document.getElementById('test-input').focus();
    var key = document.getElementById("test-card");
    key.innerHTML = listObject[testIndex].meaning;
}
function showResult() {
    var process = document.getElementById("test-process");
    var result = document.getElementById("test-result");
    var des = document.getElementById("test-des");

    var result_list = document.getElementById("test-result-list");
    process.style.display = 'none';
    result.style.display = 'block';
    var data = ""
    for (var i = 0; i < listObject.length; i++) {
        if (listObject[i].input != "") {
            data = "<tr><td>" + listObject[i].meaning + "</td><td>" + listObject[i].input + "</td><td>" + listObject[i].key + "</td></tr>"
                    + data;
        }
    }
    if (data === "") {
        data = "Well done!"
    }
    else {
        data = "<table><tr><th>Meaning</th><th>Wrong</th><th>Correct</th></tr>" + data + "</table>"
    }
    result_list.innerHTML = data + "<br/> You got: " + score + "/" + listObject.length;
    des.innerHTML = "Press x button to exit the quiz"
}
function closeModal() {
    document.getElementById("test-modal").style.visibility = 'hidden';
}
function viewAlbums() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("list-album").innerHTML = xmlhttp.responseText;
            getListWord(document.getElementsByClassName('avc-word-key')[0].innerText)
        }
    };
    xmlhttp.open("GET", "/avocado/ViewAlbums", true);
    xmlhttp.send();
}
function saveToAlbum(albumName, status) {
    var xmlhttp;
    var name = document.getElementById("my-name").innerHTML;
    var type = document.getElementById("my-type").innerHTML;
    var definition = document.getElementById("my-definition").innerHTML;
    var pronun = document.getElementById("my-pronun").innerHTML;
    var example = document.getElementById("my-example").innerHTML;
    var origin = document.getElementById("my-origin").innerHTML;
    if (definition !== "") {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
            {
                document.getElementById("button-save").style.display = 'none';
                document.getElementById("message").innerHTML = xmlhttp.responseText;
                document.getElementById("message").style.display = 'block';
            }
        };
        xmlhttp.open("POST", "/avocado/SaveToAlbum", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        if (status === undefined) {
            xmlhttp.send("type=" + type
                    + "&definition=" + definition
                    + "&name=" + name
                    + "&album=" + albumName
                    + "&pronun=" + pronun
                    + "&origin=" + origin
                    + "&exam=" + example
                    );
        } else {
            xmlhttp.send("type=" + type
                    + "&definition=" + definition
                    + "&name=" + name
                    + "&album=" + albumName
                    + "&status=" + status
                    + "&origin=" + origin
                    + "&pronun=" + pronun
                    + "&exam=" + example
                    );
        }
    }
    else {
        alert('Please input your word!');
    }
}
function getListWord(id) {
    var activeAlbum = document.getElementById(id).parentElement;
    var listAlbums = document.getElementsByClassName("avc-album-item");
    for (var i = 0; i < listAlbums.length; i++) {
        listAlbums[i].setAttribute("class", "avc-album-item");
    }
    activeAlbum.setAttribute("class", "avc-album-item album-active");
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("list-words").innerHTML = xmlhttp.responseText;
            document.getElementById("list-words").style.display = 'block';
            startLearnMode();
            showListWord();
        }
    };
    xmlhttp.open("GET", "/avocado/ViewAlbum?album=" + id, true);
    xmlhttp.send();
}
function publishAlbum() {
    var albumName = document.getElementsByClassName('album-active')[0].innerText.trim()
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/avocado/PublishAlbum", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("album=" + albumName);
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            viewAlbums();
        }
    }
}
//PDF
function exportPDF() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            alert("success");
            document.getElementById("list-album").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", "/avocado/PDFExport", true);
    xmlhttp.send();
}



function viewLibrary() {
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("public-album").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", "/avocado/ViewLibrary", true);
    xmlhttp.send();
}

//HOME
function toggleContent() {
    var sideBar = document.getElementsByClassName('avc-span');
    var myAlbum = document.getElementById('my-album');
    var newAlbum = document.getElementById('new-album');
    var library = document.getElementById('library');
    //first init
    newAlbum.style.display = 'block';
    sideBar[0].setAttribute("class", "avc-span active");
    myAlbum.style.display = 'none';
    library.style.display = 'none';

    for (var i = 0; i < sideBar.length; i++) {
        sideBar[i].onclick = function () {
            if (this.className.indexOf("active") !== 1) {
                for (var j = 0; j < sideBar.length; j++) {
                    sideBar[j].classList.remove("active");
                }
            }
            this.setAttribute("class", "avc-span active");
            if (this.id === "new-album-id") {
                unfade(newAlbum);
                fade(myAlbum);
                fade(library);
                viewDropdown();
                document.getElementById('key_word').focus();

            }
            if (this.id === "library-id") {
                viewLibrary();
                unfade(library);
                fade(myAlbum);
                fade(newAlbum);
                document.getElementById('search-library').focus();
            }
            if (this.id === "my-album-id") {
                unfade(myAlbum);
                fade(newAlbum);
                fade(library);
                viewAlbums();

            }
        };
    }
}

//ANIMATION

function fade(element) {
    var op = 1;  // initial opacity
    var timer = setInterval(function () {
        if (op <= 0.1) {
            clearInterval(timer);
            element.style.display = 'none';
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op -= op * 0.1;
    }, 10);
}
function unfade(element) {
    var op = 0.1;  // initial opacity
    element.style.display = 'block';
    var timer = setInterval(function () {
        if (op >= 1) {
            clearInterval(timer);
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.1;
    }, 10);
}

function next() {
    var listWord = document.getElementsByClassName('effect-click');
    if (index < listWord.length - 1) {
        listWord[index].style.display = 'none'
        index = index + 1;
        listWord[index].classList.remove("flipped");
        listWord[index].style.display = 'block'
    } else {
        //index = 0;
    }

}
function previous() {
    var listWord = document.getElementsByClassName('effect-click');
    if (index > 0) {
        listWord[index].style.display = 'none'
        index = index - 1;
        listWord[index].classList.remove("flipped");
        listWord[index].style.display = 'block';

    }
}
function upSideDown() {
    var listWord = document.getElementsByClassName('effect-click');
    var c = listWord[index].classList;
    c.contains("flipped") === true ? c.remove("flipped") : c.add("flipped");
}

function registerClickEvent() {
    var cards = document.getElementsByClassName("effect-click");
    for (var i = 0, len = cards.length; i < len; i++) {
        var card = cards[i];
        clickListener(card);
    }
}
function clickListener(card) {
    card.addEventListener("keydown", function () {
        var c = this.classList;
        c.contains("flipped") === true ? c.remove("flipped") : c.add("flipped");
    });
}
//
function switchMode(mode) {
    var custom = document.getElementById('custom-content');
    var auto = document.getElementById('auto-content');
    document.getElementById('manual').setAttribute('class', '');
    document.getElementById('auto').setAttribute('class', '');
    if (mode === 'manual') {
        custom.style.display = 'block';
        auto.style.display = 'none';
        document.getElementById('manual').setAttribute('class', 'active');
    } else {
        custom.style.display = 'none';
        auto.style.display = 'block';
        document.getElementById('auto').setAttribute('class', 'active');
    }
}


    