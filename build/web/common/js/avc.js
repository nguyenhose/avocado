/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//new Album
function ajaxRequest() {
    var xmlhttp;
    var name = document.getElementById("key_word").value;
    if (name != "") {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
            {
                document.getElementById("result").innerHTML = xmlhttp.responseText;
                document.getElementById("result").style.display = 'block';
                document.getElementById("button-save").style.display = 'block';
            }
        }
        xmlhttp.open("GET", "/avocado/CreateWord?word=" + name, true);
        xmlhttp.send();
    }
    else {
        alert('dont troll me!');
    }
}

function saveToAlbum() {
    var xmlhttp;
    var name = document.getElementById("my-name").innerHTML;
    var type = document.getElementById("my-type").innerHTML;
    var definition = document.getElementById("my-definition").innerHTML;
    if (definition != "") {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
            {
//                alert("success");
//                viewWordsPerAblbum();
                document.getElementById("button-save").style.display = 'none';
            }
        }
        xmlhttp.open("POST", "/avocado/SaveToAlbum", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("type=" + type + "&definition=" + definition + "&name=" + name);
    }
    else {
        alert('dont troll me!');
    }
}

function viewWordsPerAblbum() {
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("list-word").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET", "/avocado/ViewAlbum?album=1", true);
    xmlhttp.send();
}
function viewAlbums() {
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("list-album").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET", "/avocado/ViewAlbums?userId=2", true);
    xmlhttp.send();
}

function toggleImage(itemId) {
    var items = document.getElementsByClassName('item');
    for (i = 0; i < items.length; i++) {
        if (items[i].id == itemId) {
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
    for (var i = 0; i < sideBar.length; i++) {
        sideBar[i].onclick = function () {
            if (this.className.indexOf("active") != 1) {
                for (var j = 0; j < sideBar.length; j++) {
                    sideBar[j].classList.remove("active");
                }
            }
            this.setAttribute("class", "avc-span active");
            if (this.id == "new-album-id") {
                fade(myAlbum);
                unfade(newAlbum);
            }
            else {
                fade(newAlbum);
                unfade(myAlbum);
            }
        }
    }
}
function init(){
    toggleContent();
    viewAlbums();
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
//                    alert("here");
    }, 10);
}