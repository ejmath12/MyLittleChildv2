<!DOCTYPE html>
<html>
<head>
<title>tradeVault - One Stop to Track Trade Information</title>
<meta name = "author" content = "Eldo"/>
<meta name = "description" content = "For Acelr"/>
<link rel ="stylesheet" href ="resources/tradeVault.css" type ="text/css"/>
<link href="https://fonts.googleapis.com/css?family=Roboto+Slab" rel="stylesheet">
<script src="resources/tradeVault.js"></script>
</head>
<body style="overflow:scroll">
<div class ="container">
<div class = "main">
<div class ="logo">
<a href ="" > <img src ="resources/logo.jpg"><h2>&nbsp;&nbsp;tradeVault</h2></a></div>
<div class = "nav_tabs">
<a href ="" onclick="div_show();return false;"><h2>Sign In</h2></a>
</div>
<div class ="signInForm" id="signin" style = "display: none;">
<form action="signIn" id="form" method="post" name="form" onSubmit= "return submitSignIn()"><br>
<input id="name" name="name" placeholder="Company Name" type="text">
<input id="password" name="signinpass" placeholder="Password" type="password">
<input type = "submit" id="signIn" class ="button" value="Sign In"">
<input type = "button" id="cancel" class ="button" value="Cancel"  onclick = "div_hide();return false;"></form>
</div>
${generated}
</div>
 <div class = "full" id ="full">
<div class = "signupform">
<form action="signUp" id="form1" method ="post" name = "tradeVault"  onSubmit= "return submitSignUp()"> 
<input type ="text" name = "companyName" class = "companyname" id ="companyName" placeholder ="Company Name"><br>
<input type = "text" name = "companyEmail" class = "companyemail" id="email" placeholder = "Company Email ID"><br>
<input type = "password" name = "passwordNew" class = "password" id="pass" placeholder = "Password"><br>
<input type = "password" class = "password" id="passcon" placeholder = "Confirm Password"><br>
  <select name="companyType" id ="companyType">
    <option value="" disabled selected>Select Company Type</option>
    <option value="Manufacturer">Manufacturer</option>
    <option value="Client">Client</option>
    <option value="Logistics">Logistics</option>
  </select><br>
<input type = "submit" id="Signup" class ="button" value="Sign Up"></input><br>
</form>
</div>
</div>
</div>
</body>
</html>

