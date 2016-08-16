package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.Request;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.CourseBean;
import com.raystec.proj4.bean.MarksheetBean;
import com.raystec.proj4.bean.RoleBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CourseModel;
import com.raystec.proj4.model.MarksheetModel;
import com.raystec.proj4.model.RoleModel;
import com.raystec.proj4.model.StudentModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * Course functionality Controller. Performs operation for list, search and
 * delete operations of Course
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl.do" })
public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CourseCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug(" Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			System.out.println("name" + request.getParameter("name"));
			request.setAttribute("error.require",
					PropertyReader.getValue("Name"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("name"))) {
			if ("Select".equals(request.getParameter("name"))) {
				request.setAttribute("name",
						PropertyReader.getValue("error.require", "Name"));
				pass = false;
			}
		}

		if (DataValidator.isNull(request.getParameter("subject"))) {
			request.setAttribute("subject",
					PropertyReader.getValue("error.null", "Subject"));
			pass = false;
		} else {
			if (DataValidator.isSpecial(request.getParameter("subject"))) {
				request.setAttribute("subject", PropertyReader.getValue(
						"error.specialsymbol", "Subject"));
				pass = false;
			} else if (DataValidator.isNumber(request.getParameter("subject"))) {
				request.setAttribute("subject",
						PropertyReader.getValue("error.number", "Subject"));
				pass = false;
			}
		}
//
//		if (DataValidator.isNull(request.getParameter("duration"))) {
//			request.setAttribute("duration",
//					PropertyReader.getValue("error.null", "Subject Code"));
//			pass = false;
//
//		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description",
					PropertyReader.getValue("error.null", "Description"));
			pass = false;
		} else {
			if (DataValidator.isSpecial(request.getParameter("description"))) {
				request.setAttribute("description", PropertyReader.getValue(
						"error.specialsymbol", "Description"));
				pass = false;
			} else if (DataValidator.isNumber(request
					.getParameter("description"))) {
				request.setAttribute("description",
						PropertyReader.getValue("error.number", "Description"));
				pass = false;
			}

		}

		log.debug("CourseCtl Method validate Ended");

		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CourseCtl Method populatebean Started");

		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setSubject(DataUtility.getString(request.getParameter("subject")));
		/*
		 * bean.setSubjectCode(DataUtility.getString(request
		 * .getParameter("duration")));
		 */
		bean.setDescription(DataUtility.getString(request
				.getParameter("description")));
		populateDTO(bean, request);

		log.debug("Coursectl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("CourseCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CourseModel model = new CourseModel();
		System.out.println("india");
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			CourseBean bean;
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
		log.debug("CourseCtl Method doGetEnded");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("CourseCtl Method doGet Started");

		System.out.println(" do post started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		// ServletUtility.setOpration("Add", request);

		System.out.println("india");
		if (OP_SAVE.equalsIgnoreCase(op)) {

			System.out.println("CTL Save");
			CourseBean bean = (CourseBean) populateBean(request);

			try {
				if (id > 0) {
					System.out.println("kk");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(
							"Data Updated Successfully", request);
				}

				else {
					System.out.println("mm");
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setSuccessMessage(
							"Data is successfully saved", request);

				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject  Already Exists",
						request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CourseBean bean = (CourseBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request,
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

				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request,
						response);
				return;
			} else {
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			}

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("CourseCtl Method doPOst Ended");
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}
