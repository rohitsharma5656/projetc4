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
import com.raystec.proj4.bean.StudentBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CollegeModel;
import com.raystec.proj4.model.StudentModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * Student functionality Controller. Performs operation for add, update and get
 * Student
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "StudentCt", urlPatterns = { "/ctl/StudentCtl.do" })
public class StudentCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StudentCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel model = new CollegeModel();
		try {
			List l = model.list();
			request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("StudentCtl Method validate Started");
		System.out.println("validate");
		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");

		if (DataValidator.isNotNull(request.getParameter("collegeId"))) {
			String s1 = request.getParameter("collegeId");
			System.out.println(s1);
			if ("Select".equals(s1)) {
				request.setAttribute("collegeId", PropertyReader.getValue(
						"error.require", "College Name"));
				pass = false;
			}
		}
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
			/**
			 * else if(DataValidator.isSpace(request.getParameter("lastName"))){
			 * request
			 * .setAttribute("lastName",PropertyReader.getValue("error.space",
			 * "Last Name")); pass = false;
			 * 
			 * }
			 **/
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",
					PropertyReader.getValue("error.null", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobileNo"))
				|| !DataValidator.isLengthTen(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",
					PropertyReader.getValue("error.phoneNo", "Mobile No"));
			pass = false;

		}

		else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",
					PropertyReader.getValue("error.ismobile", "Mobile No"));
			pass = false;
		}
		if (DataValidator.isNull(email)) {
			request.setAttribute("email",
					PropertyReader.getValue("error.require", "Email "));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("email",
					PropertyReader.getValue("error.email", "Email "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId",
					PropertyReader.getValue("error.require", "College Name"));
			pass = false;
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
		}

		else if (!DataValidator.ageLimit(new Date(dob))) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date3", "Minimum Age"));
			pass = false;
		}

		log.debug("StudentCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("StudentCtl Method populatebean Started");

		StudentBean bean = new StudentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setFirstName(DataUtility.getString(request
				.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		populateDTO(bean, request);

		log.debug("StudentCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get model

		StudentModel model = new StudentModel();
		if (id > 0 || op != null) {
			StudentBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGett Ended");
	}

	/**
	 * Contains Submit logics
	 */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_VIEW;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("StudentCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			StudentBean bean = (StudentBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(
							"Data Updated Successfully", request);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setSuccessMessage("Data Saved Successfully",
							request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(
						"Student Email Id already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			StudentBean bean = (StudentBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request,
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

				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request,
						response);
				return;
			} else {
				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
				return;

			}
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("StudentCtl Method doPost Ended");
	}

}
