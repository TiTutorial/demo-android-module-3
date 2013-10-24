var win = Ti.UI.createWindow({
	backgroundColor : 'white'
});

win.open();

var demo3 = require('titutorial.demo3');
Ti.API.info("module is => " + demo3);

var proxy = demo3.createExample({
	imageUrl : "icon.png"
});
win.add(proxy);

