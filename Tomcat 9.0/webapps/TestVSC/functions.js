function wawa() {
	var num = document.getElementById("number").value;

	if (isNaN(num)) {
		window.alert("Value inserted not a number!");
	} else {
		var result = document.getElementById("test").innerHTML;
		for (let i = 0; i < num; i++) {
			result += "wa";
		}
		document.getElementById("test").innerHTML = result;
	}
}
function big() {
	document.getElementById("test").style.fontSize = "35px";
}
function small() {
	document.getElementById("test").style.fontSize = "16px";
}
function reset() {
	document.getElementById("test").innerHTML = "";
}
