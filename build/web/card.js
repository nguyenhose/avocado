/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function registerCard() {
    var cards = document.querySelectorAll(".card.effect__click");
    for (var i = 0, len = cards.length; i < len; i++) {
        var card = cards[i];
        clickListener(card);
    }
}
function clickListener(card) {
    card.addEventListener("click", function () {
        var c = this.classList;
        c.contains("flipped") === true ? c.remove("flipped") : c.add("flipped");
    });
}