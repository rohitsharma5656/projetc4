package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.MarksheetBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.MarksheetModel;
import com.raystec.proj4.model.StudentModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl.do" })
public class MarksheetCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(MarksheetCtl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel model = new StudentModel();
		try {
			List l = model.list();
			request.setAttribute("studentList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		// TODO Auto-generated method stub
		// super.preload(request);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MarksheetCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo",
					PropertyReader.getValue("error.null", "Roll Number"));
			pass = false;
		} else {
			if (DataValidator.isSpecial(request.getParameter("rollNo"))) {
				request.setAttribute("rollNo", PropertyReader.getValue(
						"error.specialsymbol", "Roll Number"));
				pass = false;
			} else if (DataValidator.isSpace(request.getParameter("rollNo"))) {
				request.setAttribute("rollNo",
						PropertyReader.getValue("error.space", "Roll Number"));
				pass = false;

			}
		}
		if (DataValidator.isNotNull(request.getParameter("studentId"))) {
			if (request.getParameter("studentId").equalsIgnoreCase("Select")) {
				request.setAttribute("studentId", PropertyReader.getValue(
						"error.require", "Student Name "));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("studentId"))) {
				request.setAttribute("studentId", PropertyReader.getValue(
						"error.require", "Student Name"));
				pass = false;
			}

			if (DataValidator.isNotNull(request.getParameter("physics"))
					&& !DataValidator
							.isInteger(request.getParameter("physics"))) {
				request.setAttribute("physics",
						PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			}

			if (DataUtility.getInt(request.getParameter("physics")) > 100
					|| DataUtility.getInt(request.getParameter("physics")) < 0) {
				request.setAttribute("physics",
						"Marks can not be less than 0 and greater than 100");
				pass = false;
			}

			if (DataValidator.isNotNull(request.getParameter("chemistry"))
					&& !DataValidator.isInteger(request
							.getParameter("chemistry"))) {
				request.setAttribute("chemistry",
						PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			}

			if (DataUtility.getInt(request.getParameter("chemistry")) > 100
					|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {
				request.setAttribute("chemistry",
						"Marks can not be less than 0 and greater than 100 ");
				pass = false;
			}

			if (DataValidator.isNotNull(request.getParameter("maths"))
					&& !DataValidator.isInteger(request.getParameter("maths"))) {
				request.setAttribute("maths",
						PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			}

			if (DataUtility.getInt(request.getParameter("maths")) > 100
					|| DataUtility.getInt(request.getParameter("maths")) < 0) {
				request.setAttribute("maths",
						"Marks can not be less than 0 and greater than 100");
				pass = false;
			}

			log.debug("MarksheetCtl Method validate Ended");

		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("Marksheets ctl paulate bean started ");
		MarksheetBean bean = new MarksheetBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
		populateDTO(bean, request);
		System.out.println("Papulate bean done");
		log.debug("Marksheet ctl method populatebean ended");

		// TODO Auto-generated method stub
		return bean;
	}

	/**
	 * Contain display logic
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("Marksheet ctl method do get started");
		String op = DataUtility.getString(request.getParameter("operation"));
		MarksheetModel model = new MarksheetModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			MarksheetBean bean;
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
		log.debug("MarksheetCtl Do get Method eend");
	}

	/**
	 * Contain submit logic
	 */
	/**
	 * @Override protected void doPost(HttpServletRequest request,
	 *           HttpServletResponse response) throws ServletException,
	 *           IOException { log.debug("Marksheetctl method dopost started");
	 *           String op =
	 *           DataUtility.getString(request.getParameter("operation")); //
	 *           get model MarksheetModel model = new MarksheetModel(); long id
	 *           = DataUtility.getLong(request.getParameter("id"));
	 * 
	 *           if (OP_SAVE.equalsIgnoreCase(op)) { MarksheetBean bean =
	 *           (MarksheetBean) populateBean(request); try { if (id > 0) {
	 *           model.update(bean); } else { long pk = model.add(bean);
	 *           bean.setId(pk); } ServletUtility.setBean(bean, request);
	 *           ServletUtility.setSuccessMessage("Data is Succesfully Added ",
	 *           request); } catch (ApplicationException e) { log.error(e);
	 *           ServletUtility.handleException(e, request, response); return; }
	 *           catch (DuplicateRecordException e) {
	 *           ServletUtility.setBean(bean, request); ServletUtility
	 *           .setErrorMessage("roll no alrady exists", request); // TODO:
	 *           handle exception } } else if (OP_DELETE.equalsIgnoreCase(op)) {
	 *           MarksheetBean bean = (MarksheetBean) populateBean(request);
	 *           System.out.println("in try"); try { model.delete(bean);
	 *           ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
	 *           response); System.out.println("in try"); return; } catch
	 *           (ApplicationException e) { System.out.println("in catch");
	 *           log.error(e); ServletUtility.handleException(e, request,
	 *           response); return; }
	 * 
	 *           } else if
	 *           (OP_CANCEL.equalsIgnoreCase(op)||OP_RESET.equalsIgnoreCase(op))
	 *           { if (id > 0) {
	 *           ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
	 *           response); return; } else{
	 *           ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
	 *           response); return;
	 * 
	 *           }
	 * 
	 * 
	 * 
	 * 
	 *           } ServletUtility.forward(getView(), request, response);
	 * 
	 *           log.debug("MarksheetCtl Method doPost Ended"); }
	 **/
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("MarksheetCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);
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
				ServletUtility.setErrorMessage("Roll no already exists",
						request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);
			System.out.println("in try");
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
						response);
				System.out.println("in try");
				return;
			} catch (ApplicationException e) {
				System.out.println("in catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)
				|| OP_RESET.equalsIgnoreCase(op)) {
			if (id > 0) {
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
						response);
				return;
			} else {
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
						response);
				return;

			}

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW;
	}

}
