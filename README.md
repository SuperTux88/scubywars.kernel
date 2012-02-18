# Scubywars

## Links

[Website](http://scubywars.de/)  
[Jenkins. You can download all the stuff you need there](http://jenkins.scubywars.de/)

**Testserver:** test.scubywars.de:1337

## Other Projects

[scubywars.parent](https://github.com/SuperTux88/scubywars.parent)  
[scubywars.library](https://github.com/SuperTux88/scubywars.library)  
[scubywars.visualizer](https://github.com/SuperTux88/scubywars.visualizer)  
[scubywars.sampleclient](https://github.com/SuperTux88/scubywars.sampleclient)  
[scubywars.sampleclient.java](https://github.com/SuperTux88/scubywars.sampleclient.java)  

## Protocol

###MessageLayout

<table border="1">
	<tr>
		<th colspan="3"><strong>Header</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>messageTypeId</td>
		<td>2</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>messageLength</td>
		<td>4</td>
		<td>Int</td>
	</tr>

	<tr>
		<th colspan="3"><strong>Body</strong></th>
	</tr>
	<tr>
		<td colspan="3">messageLength bytes</td>
	</tr>
</table>

<table border="1">
	<tr>
		<th colspan="3"><strong>MessageTypeIds:</strong></th>
	</tr>
	<tr>
		<td>Type</td>
		<td>Wert</td>
		<td>Direction</td>	
	</tr>
	<tr>
		<td>Player</td>
		<td>0</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>Shot</td>
		<td>1</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>PowerUp</td>
		<td>2</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>World</td>
		<td>3</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>Handshake</td>
		<td>4</td>
		<td>Server&lt;-&gt;Client</td> 		
	</tr>
	<tr>
		<td>Action</td>
		<td>5</td>
		<td>Client-&gt;Server</td> 		
	</tr>
	<tr>
		<td>Scoreboard</td>
		<td>6</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>PlayerJoined</td>
		<td>7</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>PlayerLeft</td>
		<td>8</td>
		<td>Server-&gt;Client</td> 		
	</tr>
	<tr>
		<td>PlayerName</td>
		<td>9</td>
		<td>Server-&gt;Client</td> 		
	</tr>

</table>

### Messagedetails

<!-- ...... -->
<!-- Player -->
<!-- ...... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>Player (0)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>pos.x</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>pos.y</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>direction</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>radius</td>
		<td>2</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>Rotation Speed</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>turn left</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>turn right</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>thrust</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>fire</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
</table>

<!-- .... -->
<!-- Shot -->
<!-- .... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>Shot (1)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>pos.x</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>pos.y</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>direction</td>
		<td>4</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>radius</td>
		<td>2</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>speed</td>
		<td>2</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>parentId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>lifeTime</td>
		<td>4</td>
		<td>Float</td>
	</tr>
</table>

<!-- ....... -->
<!-- PowerUp -->
<!-- ....... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>PowerUp (2)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td colspan="3">Not implemented</td>
	</tr>
</table>


<!-- ..... -->
<!-- World -->
<!-- ..... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>World (3)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>entityCount</td>
		<td>4</td>
		<td>Int</td>
	</tr>
</table>

<!-- ........................... -->
<!-- Handshake client to server  -->
<!-- ........................... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>Handshake Client -&gt; Server (4)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>PlayerVisualizerFlag (0: player / 1: listener)</td>
		<td>2</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>playerName</td>
		<td>40</td>
		<td>char[20] (Unicode (UTF-16) Big Endian)</td>
	</tr>
</table>

<!-- ........................... -->
<!-- Handshake server to client  -->
<!-- ........................... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>Handshake Server -&gt; Client (4)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>Ack/Nack</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
</table>

<!-- ................... -->
<!-- PlayerActionmessage -->
<!-- ................... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>PlayerActionMessage Client -&gt; Server (5)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>turnLeft</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>turnRight</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>thrust</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>fire</td>
		<td>1</td>
		<td>Byte</td>
	</tr>
</table>

<!-- ........... -->
<!-- Scoreboard  -->
<!-- ........... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>Scoreboard (6)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>score</td>
		<td>4</td>
		<td>Int</td>
	</tr>
	<tr>
		<td>(publicId)</td>
		<td>(8)</td>
		<td>(Long)</td>
	</tr>
	<tr>
		<td>(score)</td>
		<td>(4)</td>
		<td>(Int)</td>
	</tr>
	<tr>
		<td colspan="3">... (Map)</td>
	</tr>
</table>

<!-- ............ -->
<!-- PlayerJoined -->
<!-- ............ -->

<table border="1">
	<tr>
		<th colspan="3"><strong>PlayerJoinedMessage (7)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>playerName</td>
		<td>40</td>
		<td>char[20] (Unicode (UTF-16) Big Endian)</td>
	</tr>
</table>

<!-- .......... -->
<!-- PlayerLeft -->
<!-- .......... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>PlayerLeftMessage (8)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
</table>

<!-- .......... -->
<!-- PlayerName -->
<!-- .......... -->

<table border="1">
	<tr>
		<th colspan="3"><strong>PlayerName (9)</strong></th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>publicId</td>
		<td>8</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>playerName</td>
		<td>40</td>
		<td>char[20] (Unicode (UTF-16) Big Endian)</td>
	</tr>
</table>
