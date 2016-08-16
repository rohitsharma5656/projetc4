package com.raystec.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.raystec.proj4.bean.MarksheetBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.MarksheetModel;
/**
 * Marksheets Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class MarksheetTest 
{
	/**
	 * model object to test
	 */
	public static MarksheetModel model=new MarksheetModel();
	
    /**
     * Main method to call test method0
     * @throws DatabaseException 
     * @throws DuplicateRecordException 
     * @throws ApplicationException 
     */
	public static void main(String args[]) throws DatabaseException, DuplicateRecordException, ApplicationException
	{
		//testList();
		//testNextPK();
		//testAdd();
	//	testDelete();
		//testFindByRollNo();
	//	findByPK();
		//update();
   testMeritList();
		//serchTest();
	}
	
	public static void testNextPK() throws DatabaseException
	{
		System.out.println(model.nextPK());
	}
	public static void testAdd() throws DuplicateRecordException
	{
		try
		{
			MarksheetBean bean=new MarksheetBean();
			bean.setRollNo("45");
			bean.setPhysics(55);
			bean.setChemistry(95);
			bean.setMaths(56);
			bean.setStudentId(1L);
		
			long pk=model.add(bean);
			MarksheetBean addedBean=model.findByPK(pk);
			if(addedBean==null)
			{
				System.out.println("Test add fail");
			}
			else
			{
				System.out.println("Test add success");
			}
		}
		catch(ApplicationException e)
		{
			e.printStackTrace();
		}
	  }
	/**
	 * update marksheet model
	 * 
	 */
	  public static void update()
	  {
		  try
		  {
			  MarksheetBean bean=model.findByPK(2L);
			 bean.setRollNo("56");
			 // bean.setName("");
			  bean.setPhysics(56);
			  bean.setChemistry(56);
			  bean.setMaths(76);
			  //bean.setStudentId(2L);
			   model.update(bean);
			   MarksheetBean updatedbean = model.findByPK(2L);
	           
	            if (!"sss".equals(updatedbean.getName())) 
	            {
	            	 System.out.println("Test Update succ");
	            }else{
	            	  System.out.println("Test Update fail");
	            }
	            
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  
	  }
	
	public static void testDelete()
	{
		try
		{
			MarksheetBean bean=new MarksheetBean();
			long pk=1L;
		    bean.setId(pk);
		    model.delete(bean);
		    MarksheetBean deletedBean=model.findByPK(pk);
		    if(deletedBean!=null)
		    {
		    	System.out.println("Delete opration is fail");
		    }
		}  
		    catch (Exception e)
		    {
		    	e.printStackTrace();
				// TODO: handle exception
			}
		    	
		}
	  public static void testFindByRollNo()
	  {
		  try
		  {
			  MarksheetBean bean=model.findByRollNo("46");
					if(bean==null)
					{	
						System.out.println("Test Find by fail");
					}
			  System.out.println(bean.getId());
			  System.out.println(bean.getRollNo());
			  System.out.println(bean.getName());
			  System.out.println(bean.getPhysics());
			  System.out.println(bean.getChemistry());
			  System.out.println(bean.getMaths());
		  }
		  catch(ApplicationException e)
		  {
			  e.printStackTrace();
		  }
		  
		}
	  public static void findByPK() throws ApplicationException
	  {
		  try
		  {
		     //  MarksheetBean bean=model.findByPK(2L);
			  
			  MarksheetBean bean=new MarksheetBean();
			  long pk=2L;
			  bean=model.findByPK(pk);
			  
		       if(bean==null)
		      {	  
			      System.out.println("Test find by pk fail");
		       }
		        System.out.println(bean.getId());
		        System.out.println(bean.getRollNo());
		        System.out.println(bean.getName());
		        System.out.println(bean.getPhysics());
		        System .out.println(bean.getChemistry());
		       System.out.println(bean.getMaths());
		  }
		  catch(ApplicationException e)
		  {
			  e.printStackTrace();
		  }
	}
	  /**
	     * Tests get the meritlist of Marksheets
	     */
	    public static void testMeritList() {
	        try {
	            MarksheetBean bean = new MarksheetBean();
	            List list = new ArrayList();
	            list = model.getMeritList(1, 3);
	            if (list.size() < 0) {
	                System.out.println("Test List fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (MarksheetBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getRollNo());
	                System.out.println(bean.getName());
	                System.out.println(bean.getPhysics());
	                System.out.println(bean.getChemistry());
	                System.out.println(bean.getMaths());
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	    }
	  public static void serchTest()
	  {
		  try {
	            MarksheetBean bean = new MarksheetBean();
	            List list = new ArrayList();
	            bean.setName("AshishNehra");
	            list = model.search( bean,1,3);
	            if (list.size() < 0) {
	                System.out.println("Test Search fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (MarksheetBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getRollNo());
	                System.out.println(bean.getName());
	                System.out.println(bean.getPhysics());
	                System.out.println(bean.getChemistry());
	                System.out.println(bean.getMaths());
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	  }
	  public static void testList() throws ApplicationException
	  {
		  try
		  {
		     MarksheetBean bean=new MarksheetBean();
		     List list=new ArrayList();
		     list=model.list(0,0);
		    if(list.size()<0)
		    {
			    System.out.println("Test list fail");
		    }
		     Iterator it=list.iterator();
		    while (it.hasNext()) 
		    {
			    bean=(MarksheetBean) it.next();
			    System.out.println(bean.getId());
			    System.out.println(bean.getRollNo());
			    System.out.println(bean.getName());
		     	System.out.println(bean.getPhysics());
			    System.out.println(bean.getChemistry());
			    System.out.println(bean.getMaths());
			    System.out.println(bean.getCreatedBy());
			    System.out.println(bean.getModifiedBy());
			    System.out.println(bean.getCreatedDatetime());
			    System.out.println(bean.getModifiedDatetime());
		}
	  }
	 catch(ApplicationException e)
	 {
		 e.printStackTrace();
	 }
	  }
	  }
	

