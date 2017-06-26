package com.stuffstoreE.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import com.stuffstoreE.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stuffstoreE.domain.MemShipping;
import com.stuffstoreE.domain.security.PasswordResetToken;
import com.stuffstoreE.domain.security.Role;
import com.stuffstoreE.domain.security.UserRole;
import com.stuffstoreE.service.StuffService;
import com.stuffstoreE.service.CartItemService;
import com.stuffstoreE.service.OrderService;
import com.stuffstoreE.service.MemPaymentService;
import com.stuffstoreE.service.MemService;
import com.stuffstoreE.service.MemShippingService;
import com.stuffstoreE.service.impl.UserSecurityService;
import com.stuffstoreE.utility.MailConstructor;
import com.stuffstoreE.utility.SecurityUtility;
import com.stuffstoreE.utility.USConstants;

@Controller
public class HomeController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private MemService memService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private StuffService stuffService;
	
	@Autowired
	private MemPaymentService memPaymentService;
	
	@Autowired
	private MemShippingService memShippingService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/hours")
	public String hours() {
		return "hours";
	}
	
	@RequestMapping("/auction")
	public String auction() {
		return "auction";
	}

	@RequestMapping("/faq")
	public String faq() {
		return "faq";
	}
	
	@RequestMapping("/bookshelf")
	public String bookshelf(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			User user = memService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		List<Book> bookList = stuffService.findAll();
		model.addAttribute("bookList", bookList);
		model.addAttribute("activeAll",true);
		
		return "bookshelf";
	}

	@RequestMapping("/bookInfo")
	public String bookInfo(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			User user = memService.findByUsername(username);
			model.addAttribute("user", user);
		}
		return "bookInfo";
	}
	
	@RequestMapping("/bookDetail")
	public String bookDetail(
			@PathParam("id") Long id, Model model, Principal principal
			) {
		if(principal != null) {
			String username = principal.getName();
			User user = memService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		Book book = stuffService.findOne(id);
		
		model.addAttribute("book", book);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		
		return "bookDetail";
	}

	@RequestMapping("/forgetPassword")
	public String forgetPassword(
			HttpServletRequest request,
			@ModelAttribute("email") String email,
			Model model
			) {

		model.addAttribute("classActiveForgetPassword", true);
		
		User user = memService.findByEmail(email);
		
		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		memService.save(user);
		
		String token = UUID.randomUUID().toString();
		memService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(newEmail);
		
		model.addAttribute("forgetPasswordEmailSent", "true");
		
		
		return "myAccount";
	}
	
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = memService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		MemShipping memShipping = new MemShipping();
		model.addAttribute("memShipping", memShipping);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(
			Model model, Principal principal, HttpServletRequest request
			) {
		User user = memService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(
			Model model, Principal principal, HttpServletRequest request
			) {
		User user = memService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(
			Model model, Principal principal
			){
		User user = memService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		MemBilling memBilling = new MemBilling();
		UserPayment userPayment = new UserPayment();
		
		
		model.addAttribute("memBilling", memBilling);
		model.addAttribute("userPayment", userPayment);
		
		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(
			Model model, Principal principal
			){
		User user = memService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		
		MemShipping memShipping = new MemShipping();
		
		model.addAttribute("memShipping", memShipping);
		
		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping(value="/addNewCreditCard", method=RequestMethod.POST)
	public String addNewCreditCard(
			@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") MemBilling memBilling,
			Principal principal, Model model
			){
		User user = memService.findByUsername(principal.getName());
		memService.updateUserBilling(memBilling, userPayment, user);
		
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping(value="/addNewShippingAddress", method=RequestMethod.POST)
	public String addNewShippingAddressPost(
			@ModelAttribute("memShipping") MemShipping memShipping,
			Principal principal, Model model
			){
		User user = memService.findByUsername(principal.getName());
		memService.updateUserShipping(memShipping, user);
		
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	
	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(
			@ModelAttribute("id") Long creditCardId, Principal principal, Model model
			) {
		User user = memService.findByUsername(principal.getName());
		UserPayment userPayment = memPaymentService.findById(creditCardId);
		
		if(user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			MemBilling memBilling = userPayment.getMemBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("memBilling", memBilling);
			
			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("memShippingList", user.getMemShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping("/updateUserShipping")
	public String updateUserShipping(
			@ModelAttribute("id") Long shippingAddressId, Principal principal, Model model
			) {
		User user = memService.findByUsername(principal.getName());
		MemShipping memShipping = memShippingService.findById(shippingAddressId);
		
		if(user.getId() != memShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			
			model.addAttribute("memShipping", memShipping);
			
			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfCreditCards", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("memShippingList", user.getMemShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping(value="/setDefaultPayment", method=RequestMethod.POST)
	public String setDefaultPayment(
			@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId, Principal principal, Model model
			) {
		User user = memService.findByUsername(principal.getName());
		memService.setUserDefaultPayment(defaultPaymentId, user);
		
		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping(value="/setDefaultShippingAddress", method=RequestMethod.POST)
	public String setDefaultShippingAddress(
			@ModelAttribute("defaultShippingAddressId") Long defaultShippingId, Principal principal, Model model
			) {
		User user = memService.findByUsername(principal.getName());
		memService.setUserDefaultShipping(defaultShippingId, user);
		
		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("memShippingList", user.getMemShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping("/removeCreditCard")
	public String removeCreditCard(
			@ModelAttribute("id") Long creditCardId, Principal principal, Model model
			){
		User user = memService.findByUsername(principal.getName());
		UserPayment userPayment = memPaymentService.findById(creditCardId);
		
		if(user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			memPaymentService.removeById(creditCardId);
			
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("memShippingList", user.getMemShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping("/removeUserShipping")
	public String removeUserShipping(
			@ModelAttribute("id") Long userShippingId, Principal principal, Model model
			){
		User user = memService.findByUsername(principal.getName());
		MemShipping memShipping = memShippingService.findById(userShippingId);
		
		if(user.getId() != memShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			
			memShippingService.removeById(userShippingId);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveShipping", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("memShippingList", user.getMemShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping(value="/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		if (memService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			
			return "myAccount";
		}
		
		if (memService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			
			return "myAccount";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		memService.createUser(user, userRoles);
		
		String token = UUID.randomUUID().toString();
		memService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
		model.addAttribute("orderList", user.getOrderList());
		
		return "myAccount";
	}
	

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = memService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		
		return "myProfile";
	}
	
	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(
			@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model
			) throws Exception {
		User currentUser = memService.findById(user.getId());
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		/*check email already exists*/
		if (memService.findByEmail(user.getEmail())!=null) {
			if(memService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		
		/*check username already exists*/
		if (memService.findByUsername(user.getUsername())!=null) {
			if(memService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}
		
//		update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				
				return "myProfile";
			}
		}
		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		
		memService.save(currentUser);
		
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);
		
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("listOfCreditCards", true);
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping("/orderDetail")
	public String orderDetail(
			@RequestParam("id") Long orderId,
			Principal principal, Model model
			){
		User user = memService.findByUsername(principal.getName());
		Order order = orderService.findOne(orderId);
		
		if(order.getUser().getId()!=user.getId()) {
			return "badRequestPage";
		} else {
			List<CartItem> cartItemList = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("memShippingList", user.getMemShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			MemShipping memShipping = new MemShipping();
			model.addAttribute("memShipping", memShipping);
			
			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);
			
			return "myProfile";
		}
	}
	
	
	
	
}
