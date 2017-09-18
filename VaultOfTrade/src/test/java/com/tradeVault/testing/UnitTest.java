package test.java.com.tradeVault.testing;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.java.com.tradeVault.Utils.TradeVaultUtil;
import main.java.com.tradeVault.models.Order;

import static org.mockito.Mockito.when;

import java.util.List;



public class UnitTest {

	@Mock
	HttpServletRequest request;

	@Mock
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testReqToOrd() {
		String s[] = {"rapidor#Acelr","planX#Acelr"};
		when(request.getParameterValues("chosen_product")).thenReturn(s);
		when(session.getAttribute("user")).thenReturn("NewUser");
		List<Order> orders = TradeVaultUtil.convertReqToOrder(request, session);
		for(Order order : orders) {
			assertEquals(order.getManName(),"Acelr" );
		}
	}

}
