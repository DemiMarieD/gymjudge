//todo can not access from html?!
function displayGymnasts() {
    if (document.getElementById("GymnastBody").style.display === 'none') {
        document.getElementById("GymnastBody").style.display = "block";
        document.getElementById("dontShow").style.display = "none";
        document.getElementById("show").style.display = "block";
    } else {
        document.getElementById("GymnastBody").style.display = "none";
        document.getElementById("dontShow").style.display = "block";
        document.getElementById("show").style.display = "none";
    }
}