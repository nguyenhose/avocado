<%-- 
    Document   : index
    Created on : Aug 2, 2015, 12:20:41 PM
    Author     : nguyen
--%>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Avocado</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" href="common/css/custom.css" rel="stylesheet"/>

    </head>
    <body onload="init()">
        <div class="avc-title">Avocado</div>
        <div class="avc-header">
            <div class="avc-span" id="new-album-id">
                <a href="#newword">New word</a>
            </div>
            <div class="avc-span" id="my-album-id">
                <a href="#album" >My Albums</a>
            </div>
            <div class="avc-span" id="library-id">
                <a href="#library" >Library</a>
            </div>
            <div class="avc-span">
                <a href="#login-from">

                </a>
            </div>
        </div>
        <div class="avc-content">
            <div class="avc-panel" id="new-album">
                <jsp:include page="new_album.jsp"/>
            </div>
            <div class="avc-panel" id="my-album">
                <jsp:include page="albums.jsp"/>
            </div>
            <div class="avc-panel" id="library">
                <jsp:include page="library.jsp"/>
            </div>
        </div>
        <div id="user-id" data-id="${sessionScope._id}" />
        <div class="avc-footer"></div>
        <script type="text/javascript" src="common/js/avc.js"></script>
    </body>
</html>

