package com.raystec.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CollegeModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.DataValidator;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl.do" })
public class CollegeCtl extends BaseCtl 
{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) 
	{

		log.debug("CollegeCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) 
		{
			request.setAttribute("name",PropertyReader.getValue("error.null", "Name"));
			pass = false;
		}
		else 
		{
			if (DataValidator.isSpecial(request.getParameter("name"))) 
			{
				request.setAttribute("name",PropertyReader.getValue("error.specialsymbol", "Name"));
				pass = false;
			}
			else if (DataValidator.isNumber(request.getParameter("name")))
			{
				request.setAttribute("name",PropertyReader.getValue("error.number", "Name"));
				pass = false;
			}
		}

		if (DataValidator.isNull(request.getParameter("address")))
		{
			request.setAttribute("address",PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) 
		{
			request.setAttribute("state",
					PropertyReader.getValue("error.null", "State name"));
			pass = false;
		} 
		else 
		{
			if (DataValidator.isSpecial(request.getParameter("state"))) 
			{
				request.setAttribute("state", PropertyReader.getValue("error.specialsymbol", "State name"));
				pass = false;
			}
			else if (DataValidator.isNumber(request.getParameter("state"))) 
			{
				request.setAttribute("state",PropertyReader.getValue("error.number", "State name"));
				pass = false;
			}
		}

		if (DataValidator.isNull(request.getParameter("city"))) 
		{
			request.setAttribute("city",PropertyReader.getValue("error.null", "City name"));
			pass = false;
		}
		else if (DataValidator.isSpecial(request.getParameter("city")))
		{
			request.setAttribute("city",PropertyReader.getValue("error.specialsymbol", "City name"));
			pass = false;
		}
		else if (DataValidator.isNumber(request.getParameter("city"))) 
		{
			request.setAttribute("city",PropertyReader.getValue("error.number", "City name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo",PropertyReader.getValue("error.null", "Phone Number"));
			pass = false;
		} 
		else if (!DataValidator.isLong(request.getParameter("phoneNo"))|| !DataValidator.isLengthTen(request.getParameter("phoneNo"))) 
		{
			request.setAttribute("phoneNo",PropertyReader.getValue("error.phoneNo", "Phone Number"));
			pass = false;

		} 
		else if (!DataValidator.isMobileNo(request.getParameter("phoneNo"))) 
		{
			request.setAttribute("phoneNo",PropertyReader.getValue("error.ismobile", "Phone Number"));
			pass = false;

		}

		log.debug("CollegeCtl Method validate Ended");

		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request)
	{
		log.debug("Collegectl method papulateBean started");
		CollegeBean bean = new CollegeBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		//System.out.println("%%%%%%%%%%%%%%");
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		//System.out.println("##########");
		//System.out.println(request.getParameter("phoneNo "));
		populateDTO(bean, request);
		log.debug("Collegectl method paulatebean end");
		return bean;
	}

	/**
	 * Contain display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModel model = new CollegeModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) 
		{
			CollegeBean bean;
			try 
			{
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			}
			catch (ApplicationException e)
			{
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	  protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        log.debug("CollegeCtl Method doPost Started");

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        CollegeModel model = new CollegeModel();

	        long id = DataUtility.getLong(request.getParameter("id"));

	        if (OP_SAVE.equalsIgnoreCase(op)) 
	        {

	            CollegeBean bean = (CollegeBean) populateBean(request);

	            try {
	                if (id > 0)
	                {
	                    model.update(bean);
	                    ServletUtility.setBean(bean, request);
	                    ServletUtility.setSuccessMessage("Data Updated Successfully",
	                            request);

	                } 
	                else 
	                {
	                    long pk = model.add(bean);
	                    bean.setId(pk);
	                    ServletUtility.setSuccessMessage("Data Saved Successfully",
	                            request);

	                }
	                
	            } 
	            catch (ApplicationException e) 
	            {
	                e.printStackTrace();
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } 
	            catch (DuplicateRecordException e) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("College Name already exists",
	                        request);
	            }

	        } else if (OP_DELETE.equalsIgnoreCase(op)) {

	            CollegeBean bean = (CollegeBean) populateBean(request);
	            try {
	                model.delete(bean);
	                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request,
	                        response);
	                return;

	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } 
	        else if (OP_CANCEL.equalsIgnoreCase(op)||OP_RESET.equalsIgnoreCase(op)) {

	        	if (id > 0) 
	        	{
	                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request,
	                        response);
	                return;
	             }
	            else
	            {
	            		ServletUtility.redirect(ORSView.COLLEGE_CTL, request,
	                            response);
	                    return;
	            			}
	        }
	        ServletUtility.forward(getView(), request, response);

	        log.debug("CollegeCtl Method doGet Ended");
	    }


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_VIEW;
	}

}
