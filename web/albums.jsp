<%-- 
    Document   : learn_word
    Created on : Jul 31, 2015, 12:32:26 AM
    Author     : nguyen
--%>
<div class="navigator">
    <h4>List albums</h4>
    <div id="list-album">
    </div>
</div>
<div class="main-panel">
    <div class="avc-list-word" id="list-words" style="display:none"></div>
    <div class='controller' style="display:none">
        <div onclick="previous()" id='previous'>Previous</div>
        <div onclick="next()" id='next'>Next</div>
    </div>
</div>
<div class="navigator-right" id="list-horizone-words">
</div>
<div class="public-album" onclick="publishAlbum()">
    <div class="test-icon">
        P
    </div>  
    <div class="test-des" id='album-status' >Publish</div>    
</div>
<!--<div class="make-card" onclick="exportPDF()">
    <div class="test-icon">
        ?
    </div>  
    <div class="test-des" >Make Cards</div>    
</div>-->
<div class="test-area" onclick='showTest()'>
    <div class="test-icon">?</div>    
    <div class="test-des">Do quiz</div>
</div>




