package test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.event.*;
import javax.swing.text.*;

public class SimpleFrame extends JFrame {
	private JLabel jLabel;
	private JButton jButton;
	private JTextField jText;
	private JLabel jLabel2;
	private JButton jButton2;
	private JTextField jText2;
	private JLabel jLabel3;
	private JButton jButton3;
	private JTextField jText3;
	private JTextField jText4;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton jButton4;
	private JButton jButton5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JTextField jText5;
	private JTextField jText6;
	private JButton jButton6;
	private JLabel jLabel8;
	private JTextField jText7;
	private JButton jButton7;
	private String s1="please input the class type you need:";
	private String s2="please input id number you need:";
	private String s3="please input the name you need:";
	private String s4="first name:";
	private String s5="last name:";
	private String s6="please input the discount percentage:";
	private String s7="please input the age group of the discount:";
	private String s8="please input the class type:";
	
	
	public SimpleFrame(String title){
		super(title);
	
		jLabel = new JLabel(s1);
		jButton= new JButton("chek type, return total revenue");
		jText=new JTextField(30);
		jLabel2=new JLabel(s2);
		jButton2=new JButton("check id, return class title and total revenue");
		jText2=new JTextField(30);
		jLabel3=new JLabel(s3);
		jButton3=new JButton("check name, return class title and total revenue");
		jText3=new JTextField(30);
		jText4=new JTextField(30);
		jLabel4=new JLabel(s4);
		jLabel5=new JLabel(s5);
		jButton4=new JButton("check address and phone based on user id");
		jButton5=new JButton("check address and phone based on user name");
		jLabel6=new JLabel(s6);
		jLabel7=new JLabel(s7);
		jText5=new JTextField(30);
		jText6=new JTextField(30);
		jButton6=new JButton("check the revenue based on certain age group after discount");
		jLabel8=new JLabel(s8);
		jText7=new JTextField(30);
		jButton7=new JButton("check the revenue based on certain class type:");
		
		
		jButton.addActionListener(new ActionListenerB());
		jButton2.addActionListener(new ActionListenerB2());
		jButton3.addActionListener(new ActionListenerB3());
		jButton4.addActionListener(new ActionListenerB4());
		jButton5.addActionListener(new ActionListenerB5());
		jButton6.addActionListener(new ActionListenerB6());
		jButton7.addActionListener(new ActionListenerB7());
		Container contentPane=getContentPane();
		contentPane.setLayout(new GridLayout(22,1));
		
		contentPane.add(jLabel);
		contentPane.add(jText);
		contentPane.add(jButton);
		contentPane.add(jLabel2);
		contentPane.add(jText2);
		contentPane.add(jButton2);
		contentPane.add(jButton4);
		contentPane.add(jLabel3);
		contentPane.add(jLabel4);
		contentPane.add(jText3);
		contentPane.add(jLabel5);
		contentPane.add(jText4);
		contentPane.add(jButton3);	
		contentPane.add(jButton5);
		contentPane.add(jLabel6);
		contentPane.add(jText5);
		contentPane.add(jLabel7);
		contentPane.add(jText6);
		contentPane.add(jButton6);
		contentPane.add(jLabel8);
		contentPane.add(jText7);
		contentPane.add(jButton7);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
		}
/**
 * 1.
 * when the user inputs a class type, your program will list all the offered classes of that type 
 * and the total revenue obtained from the classes
 *
 */
	class ActionListenerB implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			jText.setEditable(true);
			String content="";
			String content2="";
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	         String str=jText.getText();
	        
	         ResultSet r=s.executeQuery("select sum(Enrollment.cost) from Class, Enrollment where Class.id=Enrollment.class_id and Class.type='"+str+"'");
   
	         while(r.next()){
	        	 content+=r.getString("sum(Enrollment.cost)");  
	         }
	         ResultSet rr=s.executeQuery("select id from Class where type='"+str+"'");
	         while(rr.next()){
	        	 content2+=rr.getString("id")+",";
	         }
	         System.out.println("when the type is "+str+", all the classes' id of that type is:"+"\n"+content2+"\n"+"its' total cost is:  "+content);
	         
	         s.close();  
	         c.close();

	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}

	/**
	 * 2.
	 * when the user input an id number, the program will return the list of classes that the person has taken 
	 * or the fact that the person isn't taking the class. if the person has taken the class, the listing 
	 * will show the class titles and the total cost 
	 *
	 */
	
	class ActionListenerB2 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			jText2.setEditable(true);
			
			String content="";
			String content2="";
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	         
	         ArrayList<String> list1=new ArrayList<String>();
	        
	         ResultSet rr=s.executeQuery("select member_id from Enrollment");
	         
	         while(rr.next()){
	         list1.add(rr.getString("member_id")); 
	         }
	         
	         int id=Integer.parseInt(jText2.getText());
	         ResultSet r=s.executeQuery("select Class.id,Class.title from Class, Enrollment where Class.id=Enrollment.class_id and Enrollment.member_id in(select Enrollment.member_id from Enrollment, RecCenterMember where RecCenterMember.id=Enrollment.member_id and RecCenterMember.id='"+id+"')");
	      

	         while(r.next()){	        	
	            	    content+=r.getString("Class.title")+"\n";  
	         }
	         
	         ResultSet r2=s.executeQuery("select sum(Enrollment.cost) from RecCenterMember, Enrollment where RecCenterMember.id=Enrollment.member_id and RecCenterMember.id='"+id+"'");
	         while(r2.next()){
	        	 content2+=r2.getString("sum(Enrollment.cost)");
	         }
	         
               
	       if(list1.contains(jText2.getText()))
	       { System.out.println("the user's id is: "+jText2.getText()+"\n"+"the class's title that the user takes is:"+"\n"+content+"\n"+"the total cost is:"+"\n"+content2+"\n");}
	       else
	       {System.out.println("the person is not taking this class");}
	       
	         
	         s.close();  
	         c.close();
	       
	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}
	
	
	/**
	 * 
	 * when the user input the user's first name and last name, the program will return the list of classes that the person has taken 
	 * or the fact that the person isn't taking the class. if the person has taken the class, the listing 
	 * will show the class titles and the total cost 
	 */
	class ActionListenerB3 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			jText3.setEditable(true);
			jText4.setEditable(true);
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	         String str1=jText3.getText();
	         String str2=jText4.getText();
	         String content="";
	         String content2="";
	        
	         ResultSet r=s.executeQuery("select Class.title from Class, Enrollment where Class.id=Enrollment.class_id and Enrollment.member_id in(select Enrollment.member_id from Enrollment, RecCenterMember where RecCenterMember.id=Enrollment.member_id and RecCenterMember.f_name='"+str1+"'and RecCenterMember.l_name='"+str2+"')");
	     	 
		       
	         while(r.next()){
	                 content+=r.getString("Class.title")+"\n";
	        	  
	         }
	         
	         ResultSet r2=s.executeQuery("select sum(Enrollment.cost) from RecCenterMember, Enrollment where RecCenterMember.id=Enrollment.member_id and RecCenterMember.f_name='"+str1+"'and RecCenterMember.l_name='"+str2+"'");
	         while(r2.next()){
	        	 content2+=r2.getString("sum(Enrollment.cost)");
	         }
               
	         
	         ArrayList<String> list=new ArrayList<String>();      
	         ResultSet rr=s.executeQuery("select distinct RecCenterMember.id from RecCenterMember, Enrollment where RecCenterMember.id=Enrollment.member_id");
	         while(rr.next()){
	         list.add(rr.getString("RecCenterMember.id")); 
	        
	         }
	         

	         ResultSet rr2=s.executeQuery("select id, f_name, l_name from RecCenterMember where f_name='"+str1+"'and l_name='"+str2+"'");
	         ArrayList<String> list2=new ArrayList<String>();
	         ArrayList<String> list3=new ArrayList<String>();
	         boolean exist=false;
	         while(rr2.next()){
	        	 list2.add(rr2.getString("f_name"));
	        	 list3.add(rr2.getString("l_name"));
	        	 if(list.contains(rr2.getString("id")))
	        	 { exist=true;}
	        	 
	         }
	         if(exist&&list2.contains(str1)&&list3.contains(str2))
	        	 System.out.println("the user's name is: "+jText3.getText()+" "+jText4.getText()+"\n"+"the class's title that the user takes is:"+"\n"+content+"\n"+"the total cost is:"+"\n"+content2+"\n");
	         else System.out.println("the person is not taking the classes");
	         
	         s.close();  
	         c.close();
	        
	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * 
	 * 3. 
	 * when given an id number, the program will returns the address and phone of that person 
	 * or the fact that no contact information in the database, if there is no contact information,
	 * the program will asks user whether insert the contact information or not.
	 * note: there are two cases:
	 * 1. if the the id has exists in the RecCenterMember, but the corresponding family_id is null
	 *    in this case, just insert the family_id
	 * 2. the id doesn't existed in the RecCenterMember
	 *    in this case, you should insert the id, f_name, l_name, dob, family_id
	 *
	 */
	class ActionListenerB4 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			String content="";
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	        
	        
	         ResultSet r=s.executeQuery("select FamilyPackage.address, FamilyPackage.phone from FamilyPackage, RecCenterMember where RecCenterMember.family_id=FamilyPackage.id and RecCenterMember.id='"+jText2.getText()+"'");
	         while(r.next()){
	        	 content+=r.getString("address")+",  "+r.getString("phone");  
	         }
	   
	         	         
	         boolean n=false;
	         boolean n1=false;
	         ResultSet rr=s.executeQuery("select RecCenterMember.family_id from RecCenterMember where id='"+jText2.getText()+"'");
	         ArrayList<String> list=new ArrayList<String>();
	         while(rr.next()){
	        	 
	        	 list.add(rr.getString("family_id")); 	 
	        	
	         }
	         ResultSet rr1=s.executeQuery("select * from RecCenterMember");
	         String ss="";
	         while(rr1.next()){
	        	 ss=rr1.getString("id");
	         }
	         

	          if(list.contains(null)){n=true;}
	         if(Integer.parseInt(jText2.getText())>Integer.parseInt(ss)){n1=true;}
	        
            // if the the id has exists in the RecCenterMember, but the corresponding family_id is null
	 		if(n==true){
	 			System.out.println("no contact information in the family package");
	 			System.out.println("if you don't want to insert the contact information into the datebase, please input -1, otherwise please just insert the family_id(note: the family_id can just be 1,2,3,4,5):");
	 			Scanner input=new Scanner(System.in);
	 			int f_id=input.nextInt();	
	 			switch (f_id)
	 			{
	 			case 1:
	 			case 2:
	 			case 3:
	 			case 4:
	 			case 5:	
	 				String sql1="update RecCenterMember set family_id='"+f_id+"' where id='"+jText2.getText()+"'";
	        	     s.executeUpdate(sql1);
	        	     System.out.println("have inserted successfully");
	        	     break;
	 			case -1: 
	 				System.out.println("no inserting, please continue other operations");
	 				break;     
	 			default: System.out.println("invalid input, please input family_id again");
	 			}
	 			
	 		}
	 		else{
	 			//the id doesn't existed in the RecCenterMember
	 			if(n1==true){
	 				System.out.println("no contact information in the family package");
		 			System.out.println("if you don't want to insert the contact information into the datebase, please input -1, if you want to insert contact information, please input 0");
		 			Scanner input2=new Scanner(System.in);
		 			int num=input2.nextInt();
		 			switch(num)
		 			{
		 			case -1:
		 				System.out.println("no inserting, please continue other operations");
		 				break;
		 			case 0: 
		 				System.out.println("please input the first name");
		 				String first=input2.next();
		 				System.out.println("please input the last name:");
		 			    String last=input2.next();
		 			    
		 			   System.out.println("please input the dob:");
		 			    String date=input2.next();
		 			   
		 			  Date dob = java.sql.Date.valueOf(date); 
		 			  
		 			   System.out.println("please input the family_id:");
		 			    int f_id=input2.nextInt();
		 			    String sql2="insert into RecCenterMember values('"+Integer.parseInt(jText2.getText())+"','"+first+"','"+last+"','"+dob+"','"+f_id+"')";
		 			    s.executeUpdate(sql2);	 
		 			   System.out.println("have inserted successfully");
		 			    break;
		 			default:  System.out.println("invalid input, please input again");
		 			
		 			}
		 					
	 			}
	 			else
	 			{System.out.println("the address and phone are:"+"\n"+content);}
	 		}
	        
	          
	         
	         s.close();  
	         c.close();

	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * 
	 *when given first name and last name, the program will returns the address and phone of that person 
	 * or the fact that no contact information in the database, if there is no contact information,
	 * the program will asks user whether insert the contact information or not.
	 * note: there are two cases:
	 * 1. if the first name and last name has exists in the RecCenterMember, but the corresponding family_id is null
	 *    in this case, just insert the family_id
	 * 2. the first name and last name don't existed in the RecCenterMember
	 *    in this case, you should insert id, f_name, l_name, dob, family_id
	 *
	 */
	class ActionListenerB5 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			String content="";
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	        
	        
	         ResultSet r=s.executeQuery("select FamilyPackage.address, FamilyPackage.phone from FamilyPackage, RecCenterMember where RecCenterMember.family_id=FamilyPackage.id and RecCenterMember.f_name='"+jText3.getText()+"' and RecCenterMember.l_name='"+jText4.getText()+"'");
	     	 
	         while(r.next()){
	        	 content+=r.getString("address")+",  "+r.getString("phone");  
	         }
	         
	         ResultSet rr_id=s.executeQuery("select id from RecCenterMember where f_name='"+jText3.getText()+"' and l_name='"+jText4.getText()+"'");
	         int id=0;
	         while(rr_id.next()){
	        	 id=Integer.parseInt(rr_id.getString("id"));
	         }
	         
	         boolean n=false;
	         boolean n1=false;
	         ResultSet rr=s.executeQuery("select RecCenterMember.family_id from RecCenterMember where id='"+id+"'");
	         ArrayList<String> list=new ArrayList<String>();
	         while(rr.next()){
	        	 
	        	 list.add(rr.getString("family_id")); 	 
	        	
	         }
	         ResultSet rr1=s.executeQuery("select * from RecCenterMember");
	         
	         ArrayList<String> namef=new ArrayList<String>();
	         ArrayList<String> namel=new ArrayList<String>();
	         while(rr1.next()){
	        	
	        	 namef.add(rr1.getString("f_name"));
	        	 namel.add(rr1.getString("l_name"));
	         }
	        
	         

	          if(list.contains(null)){n=true;}
	         if(!namef.contains(jText3.getText())||!namel.contains(jText4.getText())){n1=true;}
	      
	        
            //if the first name and last name has exists in the RecCenterMember, but the corresponding family_id is null
	 		if(n==true){
	 			System.out.println("no contact information in the family package");
	 			System.out.println("if you don't want to insert the contact information into the datebase, please input -1, otherwise please just insert the family_id(note: the family_id can just be 1,2,3,4,5):");
	 			Scanner input=new Scanner(System.in);
	 			int f_id=input.nextInt();	
	 			switch (f_id)
	 			{
	 			case 1:
	 			case 2:
	 			case 3:
	 			case 4:
	 			case 5:	
	 				String sql1="update RecCenterMember set family_id='"+f_id+"' where id='"+id+"'";
	        	     s.executeUpdate(sql1);
	        	     System.out.println("have inserted successfully");
	        	     break;
	 			case -1: 
	 				System.out.println("no inserting, please continue other operations");
	 				break;     
	 			default: System.out.println("invalid input, please input family_id again");
	 			}
	 			
	 		}
	 		else{
	 			//the first name and last name don't existed in the RecCenterMember
	 			if(n1==true){
	 				System.out.println("no contact information in the family package");
		 			System.out.println("if you don't want to insert the contact information into the datebase, please input -1, if you want to insert contact information, please input 0");
		 			Scanner input2=new Scanner(System.in);
		 			int num=input2.nextInt();
		 			switch(num)
		 			{
		 			case -1: 
		 				System.out.println("no inserting, please continue other operations");
		 				break;
		 			case 0: 
		 				System.out.println("please input the id:");
		 				int rec_id=input2.nextInt();
		 			   System.out.println("please input the dob:");
		 			    String date=input2.next();
		 			   
		 			  Date dob =  java.sql.Date.valueOf(date); 
		 			  
		 			   System.out.println("please input the family_id:");
		 			    int f_id=input2.nextInt();
		 			    String sql2="insert into RecCenterMember values('"+rec_id+"','"+jText3.getText()+"','"+jText4.getText()+"','"+dob+"','"+f_id+"')";
		 			    s.executeUpdate(sql2);	 
		 			   System.out.println("have inserted successfully");
		 			    break;
		 			default:  System.out.println("invalid input, please input again");
		 			
		 			}
		 					
	 			}
	 			else
	 			{System.out.println("the address and phone are:"+"\n"+content);}
	 		}
	        
	        
	         
	         s.close();  
	         c.close();

	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}

	/**
	 * 
	 * 4. when the user input a discount percentage and age group, the program will output the impact on the revenue
	 *
	 */
	class ActionListenerB6 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			jText5.setEditable(true);
			jText6.setEditable(true);
			String percent=jText5.getText();
			String p=percent.substring(0, 2);
			double p1=Integer.parseInt(p)*0.01;
			int age=Integer.parseInt(jText6.getText());
			int year=2012-age;
			String content="";
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	        
	        
	         ResultSet r=s.executeQuery("select Enrollment.member_id, sum(Enrollment.cost), sum(Enrollment.cost)*'"+p1+"' as s from Enrollment, RecCenterMember where RecCenterMember.id=Enrollment.member_id and RecCenterMember.dob<'"+year+"' group by Enrollment.member_id");
	     	 
	       
	         while(r.next()){
	        	 content+=r.getString("Enrollment.member_id")+",  "+r.getString("sum(Enrollment.cost)")+",  "+r.getString("s")+"\n";  
	         }
	         System.out.println("the discount is: "+percent+", and the certain age group is: "+age+"\n"+"the member id, original revenue and the revenue after discount are:"+"\n"+content);
	         
	         s.close();  
	         c.close();

	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * 
	 * 5. similar to 4, but the discount now applies to class type
	 *
	 */
	class ActionListenerB7 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			jText7.setEditable(true);
			String type=jText7.getText();
			String percent=jText5.getText();
			String p=percent.substring(0, 2);
			double p1=Integer.parseInt(p)*0.01;
			String content="";
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=sql123");
	         Statement s=c.createStatement();
	        
	        
	         ResultSet r=s.executeQuery("select Enrollment.class_id, sum(Enrollment.cost), sum(Enrollment.cost)*'"+p1+"' as s from Enrollment, Class where Class.id=Enrollment.class_id and Class.type='"+type+"' group by Enrollment.class_id");
	     	 
	       
	         while(r.next()){
	        	 content+=r.getString("Enrollment.class_id")+",  "+r.getString("sum(Enrollment.cost)")+",  "+r.getString("s")+"\n";  
	         }
	         System.out.println("the discount is: "+percent+", and the class type is: "+type+"\n"+"the class id, original revenue and the revenue after discount are:"+"\n"+content);
	         
	         s.close();  
	         c.close();

	         }
			 catch(Exception e){e.printStackTrace();}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SimpleFrame("CS425 HW2");
		
	}

} 
