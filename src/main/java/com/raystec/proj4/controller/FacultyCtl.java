package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.CourseBean;
import com.raystec.proj4.bean.FacultyBean;
import com.raystec.proj4.bean.TimeTableBean;
import com.raystec.proj4.bean.UserBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CollegeModel;
import com.raystec.proj4.model.FacultyModel;
import com.raystec.proj4.model.RoleModel;
import com.raystec.proj4.model.TimeTableModel;
import com.raystec.proj4.model.UserModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * Faculty functionality Controller. Performs operation for list, search and
 * delete operations of Faculty
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl.do" })
public class FacultyCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(FacultyCtl.class);

	/*@Override
	protected void preload(HttpServletRequest request) {
		UserModel model = new UserModel();
		CollegeModel model1 = new CollegeModel();
		try {
			List l = model.facultylist();
			request.setAttribute("userList", l);
			List l1 = model1.list();
			request.setAttribute("collegeList", l1);

		} catch (ApplicationException e) {
			log.error(e);
		}

	}
*/
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		UserModel umodel=new UserModel();
		CollegeModel cmodel=new CollegeModel();
		// TODO Auto-generated method stub
		
		try
		{
			List ul=umodel.list();
			request.setAttribute("userList", ul);
			
			List cl=cmodel.list();
			request.setAttribute("collegeList",cl);
		}
		catch(ApplicationException e)
		{
			log.error(e);
		}
		
	}
	
	
	
	
	
	
	protected boolean validate(HttpServletRequest request) {
		log.debug("FacultyCtl Method validate Started");
		boolean pass = true;
		// System.out.println("In Validations");

		if (DataValidator.isNotNull(request.getParameter("collegeId"))) {
			String s1 = request.getParameter("collegeId");
			System.out.println(s1);
			if ("Select".equals(s1)) {
				request.setAttribute("collegeId", PropertyReader.getValue(
						"error.require", "College Name"));
				pass = false;
			}
		}
		if (DataValidator.isNotNull(request.getParameter("userId"))) {
			String userId = request.getParameter("userId");
			if ("Select".equals(userId)) {
				request.setAttribute("userId", PropertyReader.getValue(
						"error.require", "Faculty Name"));
				pass = false;
			}
		}

		log.debug("FacultyCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("FacultyCtl Method populatebean Started");

		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setUserId(DataUtility.getLong(request.getParameter("userId")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		bean.setFacultyName(DataUtility.getString(request
				.getParameter("firstName")));

		populateDTO(bean, request);

		log.debug("FacultyCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains DIsplay logics
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("FacultyCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		FacultyModel model = new FacultyModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			//System.out.println("in id > 0  condition");
			FacultyBean bean;
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("FacultyCtl Method doGet Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("FacultyCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		FacultyModel model = new FacultyModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(
							"Data Updated Successfully", request);

				} else {
					long pk = model.add(bean);
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
				ServletUtility.setErrorMessage("Faculty Name Already Exists",
						request);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			FacultyBean bean = (FacultyBean) populateBean(request);

			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request,
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

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request,
						response);
				return;
			} else {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;

			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("FacultyCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}
