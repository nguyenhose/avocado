/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    toggleContent();
    viewAlbums();

}
function showDropdown() {
    var event;
    var dropdown = document.getElementById('avc-dropdown');
    event = document.createEvent('MouseEvents');
    event.initEvent('mousedown', true, true, window);
    dropdown.dispatchEvent(event);
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
//new Album
function ajaxRequest() {
    var xmlhttp;
    var name = document.getElementById("key_word").value;
    if (name !== "") {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
            {
                document.getElementById("result").innerHTML = xmlhttp.responseText;
                document.getElementById("result").style.display = 'block';
                document.getElementById("button-save").style.display = 'block';
                document.getElementById("message").style.display = 'none';
            }
        };
        xmlhttp.open("GET", "/avocado/CreateWord?word=" + name, true);
        xmlhttp.send();
    }
    else {
        alert('dont troll me!');
    }
}

function saveToAlbum(albumName, status) {
    var xmlhttp;
    var name = document.getElementById("my-name").innerHTML;
    var type = document.getElementById("my-type").innerHTML;
    var definition = document.getElementById("my-definition").innerHTML;
    if (definition !== "") {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
            {
//                alert("success");
//                viewWordsPerAblbum();
                document.getElementById("button-save").style.display = 'none';
                document.getElementById("message").innerHTML = xmlhttp.responseText;
                document.getElementById("message").style.display = 'block';


            }
        };
        xmlhttp.open("POST", "/avocado/SaveToAlbum", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        if (status == undefined) {
            xmlhttp.send("type=" + type
                    + "&definition=" + definition
                    + "&name=" + name
                    + "&album=" + albumName
                    );
        } else {
            xmlhttp.send(
                    "type=" + type
                    + "&definition=" + definition
                    + "&name=" + name
                    + "&album=" + albumName
                    + "&status=" + status
                    );
        }
    }
    else {
        alert('dont troll me!');
    }
}

function getListWord(id) {
    var activeAlbum = document.getElementById(id).parentElement;
    var albumItems = document.getElementsByClassName("avc-album-item");
    for (var i = 0; i < albumItems.length; i++) {
        albumItems[i].setAttribute("class", "avc-album-item");
    }
    activeAlbum.setAttribute("class", "avc-album-item active");
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("list-words").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", "/avocado/ViewAlbum?album=" + id, true);
    xmlhttp.send();
}
function viewAlbums() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("list-album").innerHTML = xmlhttp.responseText;
            viewDropdown();
        }
    };
    xmlhttp.open("GET", "/avocado/ViewAlbums?userId=2", true);
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
    xmlhttp.open("GET", "/avocado/ViewLibrary?userId=2", true);
    xmlhttp.send();
}
function viewDropdown() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            document.getElementById("avc-add-button").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", "/avocado/ViewAlbums?userId=2&mode=dropdown", true);
    xmlhttp.send();
}
function toggleImage(itemId) {

    var keys = document.getElementsByClassName('avc-word-key');
    for (i = 0; i < keys.length; i++) {
        keys[i].setAttribute("class", "avc-word-key");
    }
    this.event.target.setAttribute("class", "avc-word-key word-active");

    var items = document.getElementsByClassName('item');

    for (i = 0; i < items.length; i++) {
        if (items[i].id === itemId) {
            items[i].style.display = 'block';
        }
        else {
            items[i].style.display = 'none';
        }
    }
}

//home
function toggleContent() {
    var sideBar = document.getElementsByClassName('avc-span');
    var myAlbum = document.getElementById('my-album');
    var newAlbum = document.getElementById('new-album');
    var library = document.getElementById('library');
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
            }
            if (this.id === "library-id") {
                viewLibrary();
                unfade(library);
                fade(myAlbum);
                fade(newAlbum);
            }
            if (this.id === "my-album-id") {
                unfade(myAlbum);
                fade(newAlbum);
                fade(library);

            }
        };
    }
}

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

