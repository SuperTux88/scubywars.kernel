# Scubywars

## Links

[Website](http://scubywars.de/)  
[Jenkins. You can download all the stuff you need there](http://jenkins.scubywars.de/)

**Testserver:** test.scubywars.de:1337

## Protocol

###MessageLayout

<table border="1">
	<tr>
		<th colspan="3">Header</th>
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
		<th colspan="3">Body</th>
	</tr>
	<tr>
		<td colspan="3">messageLength bytes</td>
	</tr>
</table>

<table border="1">
	<tr>
		<th colspan="3">MessageTypeIds:</th>
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
		<th colspan="3">Player (0)</th>
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
		<th colspan="3">Shot (1)</th>
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
		<td>Speed</td>
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
		<th colspan="3">PowerUp (2)</th>
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
		<th colspan="3">World (3)</th>
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
		<th colspan="3">Handshake Client -&gt; Server (4)</th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>PlayerVisualizerFlag</td>
		<td>2</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>playerName</td>
		<td>40</td>
		<td>char[20]</td>
	</tr>
</table>

<!-- ........................... -->
<!-- Handshake server to client  -->
<!-- ........................... -->

<table border="1">
	<tr>
		<th colspan="3">Handshake Server -&gt; Client (4)</th>
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
		<th colspan="3">PlayerActionMessage Client -&gt; Server (5)</th>
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
		<th colspan="3">Scoreboard (6)</th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td colspan="3">Todo: documentation here</td>
	</tr>
</table>

<!-- ............ -->
<!-- PlayerJoined -->
<!-- ............ -->

<table border="1">
	<tr>
		<th colspan="3">PlayerJoinedMessage (7)</th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td colspan="3">Todo: documentation here</td>
	</tr>
</table>

<!-- .......... -->
<!-- PlayerLeft -->
<!-- .......... -->

<table border="1">
	<tr>
		<th colspan="3">PlayerLeftMessage (8)</th>
	</tr>
	<tr>
		<th>Attribute</th>
		<th>Bytes</th>
		<th>Type</th>
	</tr>
	<tr>
		<td colspan="3">Todo: documentation here</td>
	</tr>
</table>
