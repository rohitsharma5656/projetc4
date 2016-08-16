package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.TimeTableBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CollegeModel;
import com.raystec.proj4.model.CourseModel;
import com.raystec.proj4.model.TimeTableModel;
import com.raystec.proj4.model.UserModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * Timetable functionality Controller. Performs operation for add, update and
 * get Timetable
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "TimetableCtl", urlPatterns = { "/ctl/TimetableCtl.do" })
public class TimetableCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(TimetableCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		UserModel umodel = new UserModel();
		CourseModel cmodel = new CourseModel();
		// CollegeModel model = new CollegeModel();
		try {
			List ul = umodel.list();
			request.setAttribute("facultyList", ul);

			List cl = cmodel.list();
			request.setAttribute("courseList", cl);

			List sl = cmodel.list();
			request.setAttribute("subjectList", sl);
			/*
			 * List l=model.list(); request.setAttribute("collegeList", l);
			 */

		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("TimetableCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNotNull(request.getParameter("userId"))) {
			String s1 = request.getParameter("userId");
			// System.out.println(s1);
			if ("Select".equals(s1)) {
				request.setAttribute("userId", PropertyReader.getValue(
						"error.require", "Faculty Name"));
				pass = false;
			}
		}
		if (DataValidator.isNotNull(request.getParameter("courseId"))) 
		{
			String s1 = request.getParameter("courseId");
			// System.out.println(s1);
			if ("Select".equals(s1)) {
				request.setAttribute("courseId",
						PropertyReader.getValue("error.require", "Course Name"));
				pass = false;
			}
		}
		if (DataValidator.isNull(request.getParameter("day"))) {
			// System.out.println("day" + request.getParameter("day"));
			request.setAttribute("error.require",
					PropertyReader.getValue("Day"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("day"))) {
			if ("Select".equals(request.getParameter("day"))) {
				request.setAttribute("day",
						PropertyReader.getValue("error.require", "Day"));
				pass = false;
			}
		}
		if (DataValidator.isNull(request.getParameter("time"))) {
			// System.out.println("time"+ request.getParameter("time"));
			request.setAttribute("error.require",
					PropertyReader.getValue("Time"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("time"))) {
			if ("Select".equals(request.getParameter("time"))) {
				request.setAttribute("time",
						PropertyReader.getValue("error.require", "Time"));
				pass = false;
			}
		}
		log.debug("TimetableCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("TimetableCtl Method populatebean Started");

		TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setUserId(DataUtility.getLong(request.getParameter("userId")));
		bean.setFirstName(DataUtility.getString(request
				.getParameter("firstName")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setCourseName(DataUtility.getString(request
				.getParameter("courseName")));

		bean.setSubject(DataUtility.getString(request.getParameter("subject")));
		bean.setDay(DataUtility.getString(request.getParameter("day")));
		bean.setTime(DataUtility.getString(request.getParameter("time")));

		populateDTO(bean, request);

		log.debug("TimetableCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("TimetableCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		// get model

		TimeTableModel model = new TimeTableModel();
		if (id > 0 || op != null) {
			TimeTableBean bean;
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
		log.debug("TimetableCtl Method doGett Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("TimetableCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		TimeTableModel model = new TimeTableModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(
							"Data Updated Successfully ", request);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setSuccessMessage(
							"Data Saved Successfully ", request);

				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility
						.setErrorMessage(
								" Faculty is already scheduled for a lecture ",
								request);
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request,
						response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			if (id > 0) {
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request,
						response);
				return;

			} else {
				ServletUtility.redirect(ORSView.TIMETABLE_CTL, request,
						response);
				return;

			}

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("TimetableCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
