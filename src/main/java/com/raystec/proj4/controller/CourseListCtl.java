package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.CourseBean;
import com.raystec.proj4.bean.TimeTableBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.model.CourseModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * Course List functionality Controller. Performs operation for list, search and
 * delete operations of Course
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = " CourseListCtl", urlPatterns = { "/ctl/ CourseListCtl.do" })
public class CourseListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CourseListCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean = new CourseBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setSubject(DataUtility.getString(request.getParameter("subject")));
		/*
		 * bean.setSubjectCode(DataUtility.getString(request.getParameter(
		 * "subjectcode")));
		 */

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("CourseListCtl doGet Start");
		System.out.println("doget started");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseBean bean = (CourseBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		CourseModel model = new CourseModel();
		try {
			System.out.println("in try block");
			list = model.search(bean, pageNo, pageSize);
			System.out.println("after list");
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("courseListCtl doGet End");
		System.out.println("method do get ended");
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("CourseListCtl doPost Start");
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;
		CourseBean bean = (CourseBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		CourseModel model = new CourseModel();
		String[] ids = request.getParameterValues("ids");
		System.out.println(ids);
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
					|| "Previous".equalsIgnoreCase(op)
					|| OP_NEW.equalsIgnoreCase(op)
					|| OP_DELETE.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.COURSE_CTL, request,
							response);
					return;
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					System.out.println("CourseListCTL DELETE ");
					pageNo = 1;
					int i = 0;
					if (ids != null && ids.length > 0) {
						CourseBean deletebean = new CourseBean();
						for (String id : ids) {
							System.out.println(id);
							deletebean.setId(DataUtility.getInt(id));
							model.delete(deletebean);
							i++;
							ServletUtility.setSuccessMessage(i
									+ " Record Deleted Successfully", request);
						}
					} else {
						ServletUtility.setErrorMessage(
								"Select at least one record", request);
					}
				}

			}
			list = model.search(bean, pageNo, pageSize);
			System.out.println(list);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("CoourseListCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
