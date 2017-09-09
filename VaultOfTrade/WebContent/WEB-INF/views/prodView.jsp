<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>tradeVault - One Stop to Track Trade Information</title>
<meta name="author" content="Eldo" />
<meta name="description" content="For Acelr" />
<link rel="stylesheet" href="resources/tradeVault.css" type="text/css" />
<link href="https://fonts.googleapis.com/css?family=Roboto+Slab"
	rel="stylesheet">
<script src="resources/tradeVault.js"></script>
</head>
<body style="overflow: scroll">
	<div class="container">
		<div class="main">
			<div class="logo">
				<a href=""> <img src="resources/logo.jpg">
					<h2>&nbsp;&nbsp;tradeVault</h2></a>
			</div>
			<div class="nav_tabs">
				<a href="" onclick="logout();return false;"><h2>Logout</h2></a>
			</div>
		</div>
		<div class="products">
			<h2>${model.generated}</h2>
			<br> <br>         
			<form action="buyNow"  method ="post" name = "tradeVault" id ="buynow"> 
			<table>
				<tr>
					<td>Product Name</td>
					<td>Manufacturer</td>
					<td>Description</td>
					<td>Price</td>
					<td>Stock Left</td>
					<td>Discount</td>
					<td>Add to Cart</td>
				</tr>
				<c:forEach items="${model.products}" var="product">
					<tr>
						<td><h3>
								<c:out value="${product.productName}" />
							</h3></td>
						<td><h3>
								<c:out value="${product.companyName}" />
								</h3></td>
						<td><h3>
								<c:out value="${product.features}" />
								</h3></td>
						<td><h3>
								<c:out value="${product.price}" />
								</h3></td>
						<td><h3>
								<c:out value="${product.availability}" />
								</h3></td>
						<td><h3>
								<c:out value="${product.discount}" />
								</h3></td>
						<td><input type="checkbox" name="chosen_product"
							value="${product.productName}#${product.companyName}" /></td>

					</tr>
				</c:forEach>	        
			</table><br>
			<p>${model.logAlert}</p>
			<c:forEach items="${model.logistics}" var="logistic">
			<input type="radio"  name="chosen_logistics" value="${logistic}"/><h3>${logistic}</h3> 
			</c:forEach>	        
			</form>
			<br><br><br><a href="" onclick="buyStuff();return false;"><h4>${model.buy}</h4></a>
		</div>
	</div>
</body>
</html>

