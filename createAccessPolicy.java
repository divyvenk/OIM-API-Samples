import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.thortech.xl.vo.AccessPolicyResourceData;
import com.thortech.xl.vo.PolicyChildTableRecord;

import oracle.iam.platform.OIMClient;
import Thor.API.Operations.tcAccessPolicyOperationsIntf;
import Thor.API.Operations.tcObjectOperationsIntf;
import Thor.API.Operations.tcUserOperationsIntf;
import Thor.API.tcResultSet;

public class createAccessPolicy{
public static OIMClient oimClient = null;
	
	public static void main(String[] args) throws Exception {
		
		
		String hostname = "";
		String port = "";
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, "weblogic.jndi.WLInitialContextFactory");
		env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, "t3://" + hostname + ":" + port);
		oimClient = new OIMClient(env);
		System.out.println("Logging in"); 
		oimClient.login("xelsysadm", "Welcome1".toCharArray());
		System.out.println("Logged in as Xelsysadm");
		tcAccessPolicyOperationsIntf apIntf = oimClient.getService(tcAccessPolicyOperationsIntf.class);
		
		String prefix = "PDIT Server User";
		
			AccessPolicyResourceData data = new AccessPolicyResourceData(9, prefix ,18 ,"UD_PDIS_USR","P");
			HashMap map = new HashMap();
			long itResourceKey = 10l;
			map.put("UD_PDIS_USR_SERVER", itResourceKey+""); 
			map.put("UD_PDIS_USR_PASSWORD", "S@ch!n35");
			data.setFormData(map);
			System.out.println(data);
			HashMap childFormData =new HashMap();
			String childFormName = "UD_PDIS_ROL";
			String hostPrefix = "adc2200";
			String slchostPrefix = "slc00";
			String suffix = ".us.oracle.com";
			int childTableRecords = 1000;
	        for (int i = 0; i <= childTableRecords; i++) {
	        	System.out.println("OIMUtils.setAccessPolicyConfiguration(): created "+i+"th childrecord..");
	        	childFormData = new HashMap();
	        	String host = hostPrefix+i+suffix;
	            childFormData.put(childFormName+"_ROLE_NAME",host);
	            //childFormData.put(childFormName+"_APPLICATION", "Application"+i+count);
	            System.out.println(childFormData);
	            data.addChildTableRecord(String.valueOf(19l), childFormName, "Add", childFormData);        
			}
	       
	        HashMap hm = new HashMap();
	    	hm.put("Access Policies.Name", prefix+"1000");
	    	hm.put("Access Policies.Note", prefix + " NOTE");
	    	hm.put("Access Policies.Description", prefix + " DESCRIPTION");
	    	hm.put("Access Policies.By Request", "0");
	    	hm.put("Access Policies.Retrofit Flag", "1");
	    	hm.put("Access Policies.Priority", "1");

 		apIntf.createAccessPolicy(hm, new long[] {9},  new boolean[]{true},new long[0], null, new AccessPolicyResourceData[]{data});
		System.out.println("Successfully created policy "+hm.get("Access Policies.Name"));
		//Update access policy
		/*
		AccessPolicyResourceData data1 = new AccessPolicyResourceData(4, "PDIT Server User", 8,"UD_PDIS_USR","P"); 
		String childFormName = "UD_PDIS_ROL";
		String hostPrefix = "adc220";
		String slchostPrefix = "slc00";
		String suffix = ".us.oracle.com";
		
		//Delete a few existing records
		AccessPolicyResourceData exisitingData = apIntf.getDataSpecifiedForObject(4, 4, 8);
		HashMap hm = exisitingData.getChildTables();
		Iterator it = hm.keySet().iterator();
		while(it.hasNext()) {
			String formKey = (String) it.next();
			String formName = (String) hm.get(formKey);
			formName = formName.substring(1, formName.length()-1);
			System.out.println("Form Key is :"+formKey+ " form name is "+formName);
			boolean test = childFormName.equalsIgnoreCase(formName);
			System.out.println("Boolean values is "+test+ " Child Form name "+childFormName+ " and form name is "+formName);
			if(test) {
				PolicyChildTableRecord[] childRecs = exisitingData.getChildTableRecords(formKey);
				System.out.println("PolicyChildTableRecords size is "+childRecs.length);
				for (int k = 0 ; k<=1000; k++) {
					PolicyChildTableRecord rec = childRecs[k];
					System.out.println ("Record Number is "+rec.getRecordNumber());
					System.out.println("Record data is "+rec.getRecordData().get(childFormName+"_ROLE_NAME"));
					rec.setAction("Delete");
					childRecs[k].setAction("Delete");
				}
			}
		} 
		HashMap cData = null;
		for(int j=0;j<=1000;j++){
			cData = new HashMap();
			String host = slchostPrefix+j+suffix;
            cData.put(childFormName+"_ROLE_NAME",host);
            System.out.println(cData);
			exisitingData.addChildTableRecord(String.valueOf(9l), childFormName, "Add", cData); 
			//data1.addChildTableRecord(String.valueOf(9l), childFormName, "Add", cData);
		}
		apIntf.setDataSpecifiedForObject(4l, 4l , 8l, exisitingData);
		System.out.println("Successfully updated policy ");
		*/
		
	}
}
