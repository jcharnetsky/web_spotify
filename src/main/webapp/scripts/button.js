function toggleDropdown(num) {
  document.getElementById("other_dropdown_" + num).classList.toggle("show");
}

window.onclick = function(event) {
  if (!event.target.matches('.options_button')) {
    var dropdowns = document.getElementsByClassName("dropdown_content");
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}