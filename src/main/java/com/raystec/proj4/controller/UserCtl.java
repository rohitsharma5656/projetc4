package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.UserBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.RoleModel;
import com.raystec.proj4.model.UserModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * * User functionality Controller. Performs operation for add, update and get
 * User
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl.do" })
public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName",
					PropertyReader.getValue("error.null", "First Name"));
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
			} else if (!DataValidator.isWhiteSpace(request
					.getParameter("firstName"))) {
				// System.out.println("Anitejadxjksbdxksdkxk");
				request.setAttribute("firstName",
						PropertyReader.getValue("error.space", "First Name"));
				pass = false;
			}
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName",
					PropertyReader.getValue("error.null", "Last Name"));
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

		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("login",
					PropertyReader.getValue("error.null", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login",
					PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password",
					PropertyReader.getValue("error.null", "Password"));
			pass = false;
		} else {
			if (!DataValidator.isLengthPass(request.getParameter("password"))) {
				request.setAttribute("password",
						PropertyReader.getValue("error.password", "Password"));
				pass = false;
			}
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword",
					PropertyReader.getValue("error.null", "Confirm Password"));
			pass = false;
		} else if (!request.getParameter("password").equals(
				request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue(
					"error.confirmpassword", "Confirm Password"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			System.out.println("gender" + request.getParameter("gender"));
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
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId",
					PropertyReader.getValue("error.require", "Role"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("roleId"))) {
			if ("Select".equals(request.getParameter("roleId"))) {
				request.setAttribute("roleId",
						PropertyReader.getValue("error.require", "Role"));
				pass = false;
			}
		}

		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.null", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		else if (!DataValidator.dateValidate(new Date(dob))) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date1", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(new Date(dob))) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date3", "Minimum Age"));
			pass = false;
		}

		log.debug("UserCtl Method validate Ended");

		return pass;
	}

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel model = new RoleModel();
		try {
			List l = model.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

		super.preload(request);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

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

		log.debug("UserCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains DIsplay logics
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		ServletUtility.setOpration("Add", request);
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			UserBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setOpration("Edit", request);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doGet Ended");
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("UserCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(
							"Data Updated Successfully", request);

				} else {
					long pk = model.registerUser(bean);
					bean.setId(pk);
					// ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data Saved Successfully",
							request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id Already Exists",
						request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request,
						response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)
				|| OP_RESET.equalsIgnoreCase(op)) {
			if (id > 0) {

				ServletUtility.redirect(ORSView.USER_LIST_CTL, request,
						response);
				return;
			} else {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;

			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}
