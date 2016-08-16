package com.raystec.proj4.test;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.bean.CourseBean;
import com.raystec.proj4.bean.MarksheetBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CourseModel;
/**
 * Course Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class CourseTest {
	public static CourseModel cModel = new CourseModel();

	public static void main(String[] args) throws DuplicateRecordException, ApplicationException {
	//	findfByPk();
		//findfByName();
		//testDelete();
		//testUpdate();
	UserAdd();
//		findfBySubjectCode();
	}

	public static void findfByPk() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 2L;
			bean = cModel.findByPK(pk);
			if (bean == null) {
				System.out.println("Find by pk is fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
	//		System.out.println(bean.getSubjectCode());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());

		} catch (ApplicationException e) {
			e.printStackTrace();

		}
	}
	public static void findfByName() {
		try {
			CourseBean bean = new CourseBean();
			String pk ="as";
			bean = cModel.findBySubject(pk);
			if (bean == null) {
				System.out.println("Find by pk is fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			//System.out.println(bean.getSubjectCode());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());

		} catch (ApplicationException e) {
			e.printStackTrace();

		}
	}
	public static void testDelete()
	{
		try
		{
			CourseBean bean=new CourseBean();
			long pk=3L;
		    bean.setId(pk);
		    cModel.delete(bean);
		    CourseBean deletedBean=cModel.findByPK(pk);
		    if(deletedBean!=null)
		    {
		    	System.out.println("Delete opration is fail");
		    }
		    else
		    {
		    	System.out.println("Record dellete");
		    }
		}  
		    catch (Exception e)
		    {
		    	e.printStackTrace();
				// TODO: handle exception
			}
		    	
		}
	public static void testUpdate() throws DuplicateRecordException {
		try {
			CourseBean bean = cModel.findByPK(1L);
			bean.setName("hadoop");
			bean.setSubject("COMPUTER APP");
			//bean.setSubjectCode("E-103");
			bean.setDescription("Kamlesh");
			cModel.update(bean);
			System.out.println("Test update success");
			CourseBean updateBean = cModel.findByPK(1L);
			if (!"hadoop".equals(updateBean.getName())) {
				System.out.println("Test update is fail");

			}
			else
			{
				System.out.println("Test update Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void UserAdd() throws ApplicationException, DuplicateRecordException {
		CourseBean bean = new CourseBean();
		bean.setName("BE");
		bean.setSubject("manc");
		//bean.setSubjectCode("102");
		bean.setDescription("Course assign by user");
		cModel.add(bean);
      
    
	}
	public static void findfBySubjectCode() {
		try {
			CourseBean bean = new CourseBean();
			String pk = "101";
			bean = cModel.findBySubjectCode(pk);
			if (bean == null) {
				System.out.println("Find by pk is fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			//System.out.println(bean.getSubjectCode());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());

		} catch (ApplicationException e) {
			e.printStackTrace();

		}
	}
	
}
