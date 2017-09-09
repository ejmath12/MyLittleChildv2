function submitSignIn() {
	if (document.getElementById('name').value == "" || document.getElementById('password').value =="") {
		alert("Fields cannot be empty");
		return false;
	}
	return true;
}

function submitNewProduct() {
	if (document.getElementById('prodName').value == "" || document.getElementById('Features').value =="" || document.getElementById('Price').value =="" || document.getElementById('Availability').value =="") {
		alert("Fields cannot be empty");
		return false;
	}
	var price = document.getElementById('Price').value;
	 if(isNaN(price)){
			alert("Price has to be number");
			return false;
	 }
	var avail = document.getElementById('Availability').value;
	if(isNan(avail) || Number(avail)==0) {
		alert("Stock cannot be empty");
		return false;
	}
	return true;
}

function viewAllProducts() {
	var url ='viewProduct';
	window.location.href = url;
}
function logout() {
	var url ='logOut';
	window.location.href = url;
}

function submitSignUp(){

	var cName = document.getElementById('companyName').value;
	var pass = document.getElementById('pass').value;
	var conPass = document.getElementById('passcon').value;
	var x = document.getElementById('email').value;
	var atpos = x.indexOf("@");
	var dotpos = x.lastIndexOf(".");
	if(cName == "" || pass == "" || conPass == "" ) {
		alert("Fields cannot be empty");
		return false;
	}
	if(pass != conPass) {
		alert("Passwords dont match");
		return false;
	} 
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
		alert("Please enter a valid email id");
		return false;
	}
	var cType = document.getElementById('companyType');
	var val = cType.options[cType.selectedIndex].value;
	if(val == "") {
		alert("Company type is mandatory");
		return false;
	}
	return true;
}

function div_show() {
	document.getElementById('signin').style.display = "block";
	document.getElementById('full').style.opacity = "0.25";

}

function order_show() {
	var url ='orderView';
	window.location.href = url;
}

function buyStuff() {
    var checkBox = document.getElementsByName("chosen_product");
    var flag =false;
    for (var i = 0; i < checkBox.length; i++) {
        if(checkBox[i].checked) {
        	flag=true;
        	break;
        }
    }
    if(!flag) {
    	alert("Your cart is empty");
    	return false;
    }
    var radioB = document.getElementsByName("chosen_logistics");
    flag =false;
    for (var i = 0; i < radioB.length; i++) {
        if(radioB[i].checked) {
        	flag=true;
        	break;
        }
    }
    if(!flag) {
    	alert("Please Select Logistics Provider");
    	return false;
    }
		 document.getElementById('buynow').submit();
}

function editStuff() {
	var checkBox = document.getElementsByName("chosen_order");
    var flag =false;
    for (var i = 0; i < checkBox.length; i++) {
        if(checkBox[i].checked) {
        	flag=true;
        	break;
        }
    }
    if(!flag) {
    	alert("Please chose an order first");
    	return false;
    }
	 document.getElementById('editOrder').submit();
}

function newProductFormShow() {
	document.getElementById('newProductDiv').style.display = "block";
	document.getElementById('productTabs').style.opacity = "0.25";
	document.getElementById('alertText').style.opacity = "0.25";

}

function div_hide() {
	document.getElementById('signin').style.display = "none";
	document.getElementById('full').style.opacity = "1";

}
function newProductFormHide() {
	document.getElementById('newProductDiv').style.display = "none";
	document.getElementById('productTabs').style.opacity = "1";
	document.getElementById('alertText').style.opacity = "1";



}