var http = require('http');
var fs = require('fs')

function serve(req, res){
	res.setHeader("Content-Type", "application/json");
	data = fs.readFileSync('data.json');
	res.end(data);
}

var server = http.createServer(serve);
server.listen(8000);