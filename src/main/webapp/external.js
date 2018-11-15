/**
 * External JS file to index.html login page.
 */

function validatePassword() {
	var x = document.getElementById("pwd").value;
	var y = document.getElementById("matchingpwd").value;
	if (x != y) {
		alert("Passwords do not match. Try again.");
		return false;
	}
	return true;
}