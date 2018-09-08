  <script>
         $('.pop').popover().click(function () {
             setTimeout(function () {
                 $('.pop').popover('hide');
             }, 4000);
         });
         
        function openNav() {
             document.getElementById("mySidenav").style.width = "200px";
             document.getElementById("mySidenav").style.height = "220px";
             document.getElementById("mySidenav").style.align = "left";
         
         }
         
         function closeNav() {
             document.getElementById("mySidenav").style.width = "0";
         }
         

function exefunction() {
   var a = document.getElementById("lifecheck").checked

    if(a) {

 var address = document.getElementById("perAddressLine1").value;
    var city = document.getElementById("perCity").value;
            var state = document.getElementById("perState").value;       
            var pin = document.getElementById("perPincode").value;    



         document.getElementById("tempAddressLine1").value = address;
            document.getElementById("tempCity").value =city;
            document.getElementById("tempState").value = state;
            document.getElementById("tempPincode").value = pin;   
    } else {

     document.getElementById("tempAddressLine1").value = "";
            document.getElementById("tempCity").value = "";
            document.getElementById("tempState").value = "";
            document.getElementById("tempPincode").value = "";
    }
}

      </script>
