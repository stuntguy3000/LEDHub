// ==UserScript==
// @name         HiveMC LEDHub Hook
// @namespace    stuntguy3000.me
// @version      1.0
// @description  Hook HiveMC BlockParty game events into LEDHub
// @author       stuntguy3000
// @match        https://hivemc.com/blockparty/jukebox
// @grant        GM_xmlhttpRequest
// @grant        GM_*
// @require https://code.jquery.com/jquery-2.1.4.min.js
// @require https://cdn.socket.io/socket.io-1.4.5.js
// @connect https://api.hivemc.com
// @connect https://hivemc.com
// @connect localhost
// ==/UserScript==

(function() {
    'use strict';

    BlockParty.join_server = function(sid) {
        BlockParty.socket.emit("joinserver", {
            server: sid
        });
        $('#wing_server').html("Server: " + sid);
        $('#wing_song').html("Waiting for the game to start... Prepare your feet!");
        this.log('Joining room: ' + sid);

        GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/joinserver" } );
    };

    BlockParty.event_color = function(data) {
        $("#juke_color").css("background", BlockParty.colorMap["byte" + data.colorid]);
        var url = "http://localhost:4567/hivemc/colour/" + data.colorid;
        GM_xmlhttpRequest ( { method: 'GET', url: url } );

        this.log('Colour: ' + url);
    };

    $(document).ready(function() {
        GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/load" } );
        $('#wing_song').html("LEDHub v1.0 Loaded.");
    });
})();