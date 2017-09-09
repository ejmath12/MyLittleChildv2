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
			<h2>Orders In Your Boat</h2>
			<br> <br>         
			<form action="editOrder" method="post" name="tradeVault" id="editOrder">

				<table>
					<tr>
						<td>ProductName</td>
						<td>Manufacturer</td>
						<td>Client</td>
						<td>Logistics</td>
						<td>Progress State</td>
						<td>Change Status</td>
					</tr>
					<c:forEach items="${model.orders}" var="order">
						<tr>
							<td><h3>
									<c:out value="${order.productName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.manName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.clientName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.logistics}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.progressState}" />
								</h3></td>

							<td><input type="checkbox"
								name="chosen_order"
								value="${order.orderId}#${order.progressState}" /></td>
						</tr>
					</c:forEach>
				</table>
				<br>
			</form>
			
			<br> <br> ${model.edit}
				<br><br><br>
					<h2>New Updates In Active Orders</h2>
			<br> <br>         

				<table>
					<tr>
						<td>ProductName</td>
						<td>Manufacturer</td>
						<td>Client</td>
						<td>Logistics</td>
						<td>Progress State</td>
					</tr>
					<c:forEach items="${model.upOrders}" var="order">
						<tr>
							<td><h3>
									<c:out value="${order.productName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.manName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.clientName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.logistics}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.progressState}" />
								</h3></td>
						</tr>
					</c:forEach>
				</table>
	<br><br>
					<h2>Orders With No Updates</h2>
			<br> <br>         

				<table>
					<tr>
						<td>ProductName</td>
						<td>Manufacturer</td>
						<td>Client</td>
						<td>Logistics</td>
						<td>Progress State</td>
					</tr>
					<c:forEach items="${model.norOrders}" var="order">
						<tr>
							<td><h3>
									<c:out value="${order.productName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.manName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.clientName}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.logistics}" />
								</h3></td>
							<td><h3>
									<c:out value="${order.progressState}" />
								</h3></td>
						</tr>
					</c:forEach>
				</table>

		</div>
	</div>
</body>
</html>

