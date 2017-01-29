// ==UserScript==
// @name         HiveMC LEDHub Hook
// @namespace    stuntguy3000.me
// @version      1.1
// @description  Hook HiveMC BlockParty game events into LEDHub
// @author       stuntguy3000
// @match        https://hivemc.com/blockparty/jukebox
// @grant        GM_xmlhttpRequest
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
	
	BlockParty.leave_server = function(sid) {
       if (sid != "NONE") {
            BlockParty.socket.emit("leaveserver", {
                server: sid
            });
            this.log('Leaving room: ' + sid);
        }

        GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/leaveserver" } );
    };
	
	BlockParty.event_loadsong = function(data) {
       SC.stream('/tracks/' + data.soundcloud).then(function(player) {
            if (player.options.protocols[0] === 'rtmp') {
                player.options.protocols.splice(0, 1);
            }
            BlockParty.musicTrack = player;
            BlockParty.musicTrack.setVolume(BlockParty.currentVolume / 2);
            if (BlockParty.hasStarted) {
                BlockParty.musicTrack.play();
            }
        });
		
        if (data.name != undefined) {
            $('#wing_song').html(data.name);
        }
		GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/choosesong" } );
    };
	
	BlockParty.event_narration = function(data) {
       var folder = null;
        switch (data.type) {
            case "gamestart":
                folder = "GameStartEvents";
                break;
            case "random":
                folder = "RandomEvents";
                break;
            case "win":
                folder = "WinEvents";
                break;
            case "death":
                folder = "DeathEvents";
                break;
        }
        BlockParty.narrationTrack.setAttribute("src", BlockParty.mediaBase + "narration/" + folder + "/" + data.file + ".ogg");
        BlockParty.narrationTrack.play();
		
		GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/narration/" + data } );
    };

    BlockParty.event_color = function(data) {
        $("#juke_color").css("background", BlockParty.colorMap["byte" + data.colorid]);
        var url = "http://localhost:4567/hivemc/colour/" + data.colorid;
		
		if (BlockParty.hasStarted) {
			GM_xmlhttpRequest ( { method: 'GET', url: url } );
        }
    };
	
	BlockParty.event_endgame = function(data) {
        this.log('Game over. Resetting Jukebox');
        BlockParty.reset_jukebox();
		
        GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/gameover" } );
    };

    $(document).ready(function() {
        GM_xmlhttpRequest ( { method: 'GET', url: "http://localhost:4567/hivemc/load" } );
        $('#wing_song').html("LEDHub v1.1 Loaded.");
    });
})();