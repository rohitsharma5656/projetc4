package com.raystec.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



import com.raystec.proj4.bean.MarksheetBean;
import com.raystec.proj4.bean.StudentBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.util.JDBCDataSource;
/**
 * JDBC Implementation of MarksheetModel
 * 
 @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 */
public class MarksheetModel 
{
	Logger log=Logger.getLogger(MarksheetModel.class);
	
	
	public Integer nextPK() throws DatabaseException
	{
		log.debug("Model next pk started");
		Connection conn=null;
		int pk=0;
		try
		{
			conn=JDBCDataSource.getConnection();
			PreparedStatement psmt=conn.prepareStatement("SELECT MAX(ID) FROM ST_MARKSHEET");
			ResultSet rs=psmt.executeQuery();
			while(rs.next())
			{
				pk=rs.getInt(1);
			}
			rs.close();
		}
		catch(Exception e)
		{
			log.error(e);
			throw new DatabaseException("Exception in marksheets getting pk");
			
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
  /**
 * @throws DuplicateRecordException 
 * @throws ApplicationException 
   * Adds a marksheets
   * @param bean
   * @throws 
   */
	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException
	{
	     	log.debug("moodel add started");
	    	Connection conn=null;
		
		//get Student name
		StudentModel sModel=new StudentModel();
		StudentBean studentbean=sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName()+""+studentbean.getLastName());
		
		MarksheetBean duplicateBean=findByRollNo(bean.getRollNo());
		int pk=0;
		if(duplicateBean!=null)
		{
			throw new DuplicateRecordException("Roll number already exits");
		}
		try
		{
			conn=JDBCDataSource.getConnection();
			//getAuto genrated primary ket
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement psmt=conn.prepareStatement("INSERT ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?) ");
		    psmt.setInt(1, pk);
		    psmt.setString(2,bean.getRollNo());
		    psmt.setLong(3,bean.getStudentId());
		  psmt.setString(4, bean.getName());
		    psmt.setInt(5,bean.getPhysics());
		    psmt.setInt(6,bean.getChemistry());
		    psmt.setInt(7,bean.getMaths());
		    psmt.setString(8,bean.getCreatedBy());
		    psmt.setString(9,bean.getModifiedBy());
            psmt.setTimestamp(10,bean.getCreatedDatetime());
            psmt.setTimestamp(11,bean.getModifiedDatetime());
            psmt.executeUpdate();
            conn.commit();
            psmt.close();
		}
		catch(Exception e)
		{
			log.error(e);
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				throw new ApplicationException("add rollback exception"+ex.getMessage());
			}
			throw new ApplicationException("Exception in add  marksheets");
		}
		log.debug("model add end");
      return pk;		
    }
	  public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException
	  {
		  log.debug("Marksheet update stated");
		  Connection conn=null;
		  MarksheetBean beanExixt=findByRollNo(bean.getRollNo());
		  
		  if(beanExixt!=null&&beanExixt.getId()!=bean.getId())
		  {
			  throw new DuplicateRecordException("record already exits");
		  }
		  //get Name
		  StudentModel smodel=new StudentModel();
		  StudentBean studentBean=smodel.findByPK(bean.getStudentId());
		  bean.setName(studentBean.getFirstName()+" "+studentBean.getLastName());
		  try
		  {
			  conn=JDBCDataSource.getConnection();
			  conn.setAutoCommit(false);
			  PreparedStatement psmt=conn.prepareStatement("UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
             psmt.setString(1,bean.getRollNo());
             psmt.setLong(2, bean.getStudentId());
             psmt.setString(3,bean.getName());
             psmt.setInt(4,bean.getPhysics());
             psmt.setInt(5,bean.getChemistry());
             psmt.setInt(6,bean.getMaths());
             psmt.setString(7,bean.getCreatedBy());
             psmt.setString(8,bean.getModifiedBy());
             psmt.setTimestamp(9,bean.getCreatedDatetime());
             psmt.setTimestamp(10,bean.getModifiedDatetime());
             psmt.setLong(11,bean.getId());
             psmt.executeUpdate();
             conn.commit();
             psmt.close();
           }
		  catch(Exception e)
		  {
			  log.error(e);
			  try
			  {
				  conn.rollback();
			  }
			  catch(Exception ex)
			  {
				  throw new ApplicationException("update rollback exception"+ex.getMessage());
			}
			  throw new ApplicationException("Exception in updating marksheets");
			  
		  }
		  finally
		  {
			  JDBCDataSource.closeConnection(conn);
		  }
			log.debug("model update end");  
	  }
	
	
	
	public void delete(MarksheetBean bean) throws ApplicationException
	{
		log.debug("marksheets add started");
		Connection conn=null;
		try
		{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt=conn.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID=?");
			psmt.setLong(1, bean.getId());
			
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		}
		catch(Exception e)
		{
			log.error(e);
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				log.error("Delete Rollback exception"+ex.getMessage());
			}
			throw new ApplicationException("Exception in dlete Markshetets");
			
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public  MarksheetBean findByRollNo(String rollNo) throws ApplicationException
	{
		log.debug("model find by roll no started");
		StringBuffer sql=new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE  ROLL_NO=?");
		MarksheetBean bean=null;
		Connection conn=null;
		try
		{
			conn=JDBCDataSource.getConnection();
			PreparedStatement psmt=conn.prepareStatement(sql.toString());
			psmt.setString(1,rollNo);
			ResultSet rs=psmt.executeQuery();
			while(rs.next())
			{
				bean=new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		}
		catch(Exception e)
		{
			log.error(e);
			throw new ApplicationException("Exceptio in getting marksheets by roll no");
			
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model find by rollNo end");
		return bean;
	}
	/**
	 * finds marksheet by pk
	 * @param pk=get parameter
	 * @return bean
	 * @throws ApplicationException 
	 * @throws DatabaseException
	 * 
	 * 
	 */
	 public MarksheetBean findByPK(long pk) throws ApplicationException
	 {
		 log.debug("Model find by pk started");
		 
		 StringBuffer sql=new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ID=?");
		 MarksheetBean bean=null;
		 Connection conn=null;
		 
		 try
		 {
			 conn=JDBCDataSource.getConnection();
			 PreparedStatement psmt=conn.prepareStatement(sql.toString());
			 psmt.setLong(1, pk);
			 ResultSet rs=psmt.executeQuery();
			 while(rs.next())
			 {
				 bean=new MarksheetBean();
				 bean.setId(rs.getLong(1));
				 bean.setRollNo(rs.getString(2));
				 bean.setStudentId(rs.getLong(3));
				 bean.setName(rs.getString(4));
				 bean.setPhysics(rs.getInt(5));
				 bean.setChemistry(rs.getInt(6));
				 bean.setMaths(rs.getInt(7));
				 bean.setCreatedBy(rs.getString(8)) ;
				 bean.setModifiedBy(rs.getString(9));
				 bean.setCreatedDatetime(rs.getTimestamp(10));
				 bean.setModifiedDatetime(rs.getTimestamp(11));
			 }
			 rs.close();
		 }
		 catch(Exception e)
		 {
			 log.error(e);
			 throw new ApplicationException("Exception in getting marksheets  by pk");
		 }
		 finally
		 {
			 JDBCDataSource.closeConnection(conn);
		 }
		 log.debug("model find by pk end");
		 return bean;
		
	 }

	 public List search(MarksheetBean bean,int pageNo, int pageSize) throws ApplicationException
	 {
         log.debug("model search started");
         StringBuffer sql=new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE TRUE");
          if(bean!=null)
          {
        	  System.out.println(" service "+bean.getName());
        	  if(bean.getId()>0)
        	  {
        		  sql.append("  AND Id= "+bean.getId());
        	  }
        	  if(bean.getRollNo()!=null&&bean.getRollNo().length()>0)
        	  {
        		  sql.append(" AND roll_no like  '"+bean.getRollNo()+"%'");
        	  }
        	  if(bean.getName()!=null&&bean.getName().length()>0)
        	  {
        		  sql.append(" AND name like'"+bean.getName()+"%'");
        	  }
        	  if(bean.getPhysics()!=null&&bean.getPhysics()>0)
        	  {
        		  sql.append(" AND physics= "+bean.getPhysics());
        	  }
        	  if(bean.getChemistry()!=null&&bean.getChemistry()>0)
        	  {
        		  sql.append(" AND chemistry= "+bean.getChemistry());
        	  }
        	  if(bean.getMaths()!=null&&bean.getMaths()>0)
        	  {
        		  sql.append(" AND maths= "+bean.getMaths());
        	  }
         }
          //if pagesize is greater then zero then apply pagination
          if(pageSize>0)
          {
        	  //calculate start index
        	  pageNo=(pageNo-1)*pageSize;
        	  sql.append(" Limit "+pageNo+","+pageSize);
         }
          ArrayList list=new ArrayList();
          Connection conn=null;
          try
          {
        	  System.out.println(sql);
        	conn=JDBCDataSource.getConnection();        	 
        	  PreparedStatement psmt=conn.prepareStatement(sql.toString());
        	  
        	  ResultSet rs=psmt.executeQuery();
        	  while(rs.next())
        	  {
        		  bean=new MarksheetBean();
        		  bean.setId(rs.getLong(1));
        		  bean.setRollNo(rs.getString(2));
        		  bean.setStudentId(rs.getLong(3));
        		  bean.setName(rs.getString(4));
        		  bean.setPhysics(rs.getInt(5));
        		  bean.setChemistry(rs.getInt(6));
        		  bean.setMaths(rs.getInt(7));
        		  bean.setCreatedBy(rs.getString(8));
        		  bean.setModifiedBy(rs.getString(9));
        		  bean.setCreatedDatetime(rs.getTimestamp(10));
        		  bean.setModifiedDatetime(rs.getTimestamp(11));
        		  list.add(bean);
               }
        	  rs.close();
          }
          catch(Exception ex)
          {
        	  log.error(ex);
        	  throw new ApplicationException("update rollback exceotion"+ex.getMessage());
         }
          finally
          {
        	  JDBCDataSource.closeConnection(conn);
          }
        log.debug("model search end");
        return list;
	 }
	  
	 public List list(int pageNo,int pageSize) throws ApplicationException
	 {
		 log.debug("model add started");
		 ArrayList list=new ArrayList();
		 StringBuffer sql=new StringBuffer("SELECT * FROM ST_MARKSHEET ");
		 System.out.println(sql);
		 //IF PAGE SIZE IS GREATER THEN ZERO THEN APPLY PAGINATION
		 if(pageSize>0)
		 {
			 //calculate start record index
			 pageNo=(pageNo-1)*pageSize;
			 sql.append(" limit " + pageNo + ","+pageSize);
		 }
		 Connection conn=null;
		 try
		 {
			 conn=JDBCDataSource.getConnection();
			 PreparedStatement psmt=conn.prepareStatement(sql.toString());
			 ResultSet rs=psmt.executeQuery();
			 while(rs.next())
			 {
				 MarksheetBean bean=new MarksheetBean();
				
				bean.setId(rs.getLong(1));
				 bean.setRollNo(rs.getString(2));
				 bean.setStudentId(rs.getLong(3));
				 bean.setName(rs.getString(4));
			
				 bean.setPhysics(rs.getInt(5));
				 bean.setChemistry(rs.getInt(6));
				 bean.setMaths(rs.getInt(7));
		         bean.setCreatedBy(rs.getString(8));
				 bean.setModifiedBy(rs.getString(9));
				 bean.setCreatedDatetime(rs.getTimestamp(10));
				 bean.setModifiedDatetime(rs.getTimestamp(11));
				
				 list.add(bean);
				}
			 rs.close();
		 }
		 catch(Exception e)
		 {
			 log.equals(e);
			 e.printStackTrace();
		
			 throw new ApplicationException("Exception in getting list of marksheets");
			 
		 }
		 finally
		 {
			 JDBCDataSource.closeConnection(conn);
		 }
		return list;
	 }
	 
	  /**
	     * get Merit List of Marksheet with pagination
	     * 
	     * @return list : List of Marksheets
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     * @throws DatabaseException
	     */

	    public List getMeritList(int pageNo, int pageSize)
	            throws ApplicationException {
	        log.debug("Model  MeritList Started");
	        ArrayList list = new ArrayList();
	        StringBuffer sql = new StringBuffer(
	                "SELECT `ID`,`ROLL_NO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS` , (PHYSICS + CHEMISTRY + MATHS) as total from `ST_MARKSHEET` order by total desc");
	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                MarksheetBean bean = new MarksheetBean();
	                bean.setId(rs.getLong(1));
	                bean.setRollNo(rs.getString(2));
	                bean.setName(rs.getString(3));
	                bean.setPhysics(rs.getInt(4));
	                bean.setChemistry(rs.getInt(5));
	                bean.setMaths(rs.getInt(6));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error(e);
	            throw new ApplicationException(
	                    "Exception in getting merit list of Marksheet");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model  MeritList End");
	        return list;
	    }

	}

	 

