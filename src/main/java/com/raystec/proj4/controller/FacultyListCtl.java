package com.raystec.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.BaseBean;
import com.raystec.proj4.bean.FacultyBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.model.FacultyModel;
import com.raystec.proj4.util.DataUtility;
import com.raystec.proj4.util.PropertyReader;
import com.raystec.proj4.util.ServletUtility;
/**
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl.do" })
public class FacultyListCtl extends BaseCtl 
{
	private static Logger log = Logger.getLogger(FacultyListCtl.class);

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        FacultyBean bean = new FacultyBean();

        bean.setFacultyName(DataUtility.getString(request
                .getParameter("facultyName")));
        
        //bean.setSubject(DataUtility.getString(request.getParameter("subject")));
        //bean.setSubjectCode(DataUtility.getString(request.getParameter("subjectcode")));

        return bean;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("FacultyListCtl doGet Start");
        List list = null;

        int pageNo = 1;

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        FacultyBean bean = (FacultyBean) populateBean(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        FacultyModel model = new FacultyModel();
        try {
            list = model.search(bean, pageNo, pageSize);
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
        log.debug("FacultyListCtl doGet End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("FacultyListCtl doPost Start");
        List list = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;

        FacultyBean bean = (FacultyBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        
        
     // get the selected checkbox ids array for delete list
     		String[] ids = request.getParameterValues("ids");
     		
        FacultyModel model = new FacultyModel();

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            }
            
            else if (OP_NEW.equalsIgnoreCase(op)) {

    			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
    			return;
    		}
            else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					FacultyBean deletebean = new FacultyBean();
					for (String id : ids) {
				        System.out.println("*******************"+id);

						deletebean.setId(DataUtility.getInt(id));
                         model.delete(deletebean);
                         ServletUtility.setSuccessMessage("Record Deleted Successfully", request);}
				} else {
					ServletUtility.setErrorMessage(
							"Select at least one record", request);
				}
			}
            list = model.search(bean, pageNo, pageSize);
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
        log.debug("FacultyListCtl doGet End");
    }

    @Override
    protected String getView() {
        return ORSView.FACULTY_LIST_VIEW;
    }


}
