package com.raystec.proj4.test;

import com.raystec.proj4.bean.FacultyBean;
import com.raystec.proj4.bean.TimeTableBean;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.FacultyModel;
/**
 * Faculty Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class FacultyTest 
{
	public static FacultyModel fModel=new FacultyModel();
	
	
	public static void main(String args[])
	{
		//testUpdate();
	}

		 /**public static void testUpdate() 
		 {
				try 
				{
			         FacultyBean bean =fModel.findByPK(3L); 
			        		bean.setUserId(109);
					bean.setCollegeId(13);
					bean.setCollegeName("A.i.T");
					bean.setFacultyName("Kamlesh sir");
					fModel.update(bean);
					System.out.println("Test update success");
					FacultyBean updateBean = fModel.findByPK(3l);
					if (!"N.I.T".equals(updateBean.getCollegeId())) {
						System.out.println("Test update is fail");

					}
					else
					{
						System.out.println("Test update Success");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}**/

	}


