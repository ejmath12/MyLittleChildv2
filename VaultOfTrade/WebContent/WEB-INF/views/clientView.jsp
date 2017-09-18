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
<a href ="" onclick="logout();return false;"><h2>Logout</h2></a>
</div>
</div>
 <div class = "full" id ="full">
<div>
${model.popUp}
</div>
<div class = "showName">
<h2>Welcome, </h2><h1>${model.generated}</h1>
</div>
<div class = "productTabs" id="productTabs"> 
<a href ="" onclick="viewAllProducts();return false;"><h2>View Products</h2></a>
<a href ="" onclick="order_show();return false;"><h2>View Orders</h2></a>
</div>
<div class= "alertText" id ="alertText">
<h2>You have<br>${model.alert}<br>new order updates</h2>
</div>
</div>
</div>
</body>
</html>

