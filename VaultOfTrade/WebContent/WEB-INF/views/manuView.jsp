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
<div class = "showName">
<h2>Welcome, </h2><h1>${model.generated}</h1>
</div>
<div class ="newProductDiv" id="newProductDiv" style = "display: none;">
<form action="newProduct" id="newProductForm" method="post" name="newProductForm" onSubmit = "return submitNewProduct();"><br>
<input id="prodName" name="prodName" placeholder="Product Name" type="text"><br>
<input id="Features" name="Features" placeholder="Description" type="text"><br>
<input id="Price" name="Price" placeholder="Price" type="text"><br>
<input id="Availability" name="Availability" placeholder="Stock" type="text"><br>
<input id="discount" name="discount" placeholder="Discount" type="text"><br>
<input type = "submit" id="createProduct" class ="button" value="Create Product"><br>
<input type = "button" id="cancel" class ="button" value="Cancel"  onclick = "newProductFormHide();return false;"></form>
</div>
<div>
${model.popUp}
</div>
<div class = "productTabs" id="productTabs"> 
<a href ="" onclick="newProductFormShow();return false;"><h2>Add Product</h2></a>
<a href ="" onclick="order_show();return false;"><h2>View Orders</h2></a>
</div>
<div class= "alertText" id ="alertText">
<h2>You have<br>${model.alert}<br>new order updates</h2>
</div>
</div>
</div>
</body>
</html>

