package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.RoleBean;
import com.raystec.proj4.bean.UserBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.UserModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/ctl/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_SIGN_UP = "SignUp";

	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName",
					PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else {
			if (DataValidator.isSpecial(request.getParameter("firstName"))) {
				request.setAttribute("firstName", PropertyReader.getValue(
						"error.specialsymbol", "First Name"));
				pass = false;
			} else if (DataValidator
					.isNumber(request.getParameter("firstName"))) {
				request.setAttribute("firstName",
						PropertyReader.getValue("error.number", "First Name"));
				pass = false;
			} else if (DataValidator.isSpace(request.getParameter("firstName"))) {
				request.setAttribute("firstName",
						PropertyReader.getValue("error.space", "First Name"));
				pass = false;

			}

		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName",
					PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else {
			if (DataValidator.isSpecial(request.getParameter("lastName"))) {
				request.setAttribute("lastName", PropertyReader.getValue(
						"error.specialsymbol", "Last Name"));
				pass = false;
			} else if (DataValidator.isNumber(request.getParameter("lastName"))) {
				request.setAttribute("lastName",
						PropertyReader.getValue("error.number", "Last Name"));
				pass = false;
			}
			/**
			 * else if (DataValidator.isSpace(request.getParameter("lastName")))
			 * { request.setAttribute("lastName",
			 * PropertyReader.getValue("error.space", "Last Name")); pass =
			 * false;
			 * 
			 * }
			 **/
		}
		if (DataValidator.isNull(login)) {
			request.setAttribute("login",
					PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login",
					PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password",
					PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else {
			if (!DataValidator.isLengthPass(request.getParameter("password"))) {
				request.setAttribute("password",
						PropertyReader.getValue("error.password", "Password"));
				pass = false;
			}
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue(
					"error.require", "Confirm Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			// System.out.println("gender" + request.getParameter("gender"));
			request.setAttribute("error.require",
					PropertyReader.getValue("Gender"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("gender"))) {
			if ("Select".equals(request.getParameter("gender"))) {
				request.setAttribute("gender",
						PropertyReader.getValue("error.require", "Gender"));
				pass = false;
			}
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.dateValidate(new Date(dob))) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date1", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(new Date(dob))) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date3", "Minimum Age"));
			pass = false;
		}

		if (!request.getParameter("password").equals(
				request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmpass", PropertyReader.getValue(
					"error.confirmpassword", "Confirm  Password "));
			pass = false;
		}
		log.debug("UserRegistrationCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRoleId(RoleBean.STUDENT);

		bean.setFirstName(DataUtility.getString(request
				.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request
				.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);

		log.debug("UserRegistrationCtl Method populatebean Ended");

		return bean;
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegistrationCtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Display concept of user registration
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("UserRegistrationCtl Method doGet Started");

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit concept of user registration
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		log.debug("UserRegistrationCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				
				long pk = model.registerUser(bean);
				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);
				ServletUtility.setSuccessMessage(
						"User Successfully Registered", request);
				ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				log.error(e);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists",
						request);
				ServletUtility.forward(getView(), request, response);
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)
				|| OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request,
					response);

		}
		log.debug("UserRegistrationCtl Method doPost Ended");

	}

}
