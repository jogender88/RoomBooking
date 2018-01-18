

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jogenderyadav
 */
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;

public class HomePage implements ActionListener {
	JButton login, signin, cancel;
	JLabel  user, user2, pass, forgot,room,homelogin,headerimage,jLabel;
	JTextField username, username2;
	JPasswordField p, p2;
	ImageIcon  loginpanelback;
	Timer tm, tm2;
	int x = 0, count = 0,counter;
	PreparedStatement pst,idps,ps;
	Connection con,con1;
	ResultSet rs;
	String d1, d2;
	JPanel  loginpanel;
	String staffid;
	public static String userid,userid1;
	JFrame frame;
	Image iconf,back;
	ImageIcon image;
	HomePage() {
		frame=new JFrame("The Northcap University");
		iconf = Toolkit.getDefaultToolkit().getImage("images.jpeg");
		frame.setIconImage(iconf);
		frame.setVisible(true);
		frame.setLayout(null);
		
		frame.setContentPane(new JLabel(new ImageIcon("backcopy.jpg")));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setOpacity(1.0f);
		
		headerimage=new JLabel(new ImageIcon("Logo.png"));
		frame.add(headerimage);
		headerimage.setBounds(100, 10,1196,225);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Initialize Label
		user = new JLabel("Username");
		user2 = new JLabel("Username");
		
		pass = new JLabel("Password");
		homelogin = new JLabel("Home");

		// Initialize TextFileds
		username = new JTextField(); // Text Filed in login frame
		username2 = new JTextField();


		p = new JPasswordField();
		p2 = new JPasswordField();
		
		signin = new JButton(); // Enter Button for Login


		frame.add(homelogin);
		frame.add(username2);
		frame.add(p2);
		frame.add(signin);
		//frame.add(cancel);
		// Login Panel
		username2.setBounds(275, 385, 290, 30);
		username2.setBackground(new Color(0, 0, 0, 0));
		username2.setBorder(null);

		p2.setBounds(275, 465, 290, 30);
		p2.setBackground(new Color(0, 0, 0, 0));
		p2.setBorder(null);

		signin.setBounds(260, 510, 144, 42);
		signin.setContentAreaFilled(false);
		signin.setBorder(null);
		
		 
		signin.addActionListener(this);
		
		username2.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
			    username2.setBackground(Color.WHITE);
			    
			  }
			});
		p2.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
			    p2.setBackground(Color.WHITE);
			    
			  }
			});
		
		homelogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				if (m.getSource() == homelogin) {
					frame.setVisible(true);
					loginpanel.setVisible(false);
					username2.setText("");
					p2.setText("");
				}
			}
		});
		
		
		
		frame.getContentPane().setBackground(Color.decode("#bdb67b"));
		frame.setLocationRelativeTo(null);

		connection(); // Database Coonection calling method
	}

	
	// Action Listener for Login and register button

	@Override

	public void actionPerformed(ActionEvent e) {
		
				// checks if the button clicked
		
		 if (e.getSource() == signin) {
			userid=username2.getText();
			//userid1=userid;
			char[] temp_pwd = p2.getPassword();
			String pwd = null;
			pwd = String.copyValueOf(temp_pwd);
			System.out.println("Username,Pwd:" + username2.getText() + "," + pwd);

			// The entered username and password are sent via "checkLogin()"
			// which return boolean
			if (username2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Enter User name or Password", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			else if (checkLogin(username2.getText(), pwd)) {
					
								// a pop-up box
				try {
					
					frame.setVisible(false);
					
					new MeetingRoom();
					if(verifyuser(userid)) {
						JOptionPane.showMessageDialog(null, "Welcome  "+userid);
						}
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				} else {
				// a pop-up box
				JOptionPane.showMessageDialog(null, "Username or Password wrong", "Failed!!", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
				}
	

	public void connection() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FacilityManagement", "root", "");
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/FacilityManagement", "root", "");
			pst = con.prepareStatement("select * from userdetails where userid=? and pass=?");
			ps=con.prepareStatement("insert into RoomBook (userid,staff_no,fname,lname,mail_id,phn_no)"+ "values(?,?,?,?,?,?)");
			idps=con.prepareStatement("select userid from userdetails where userid=?");
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public Boolean checkLogin(String Id, String pass) {
		try {

			pst.setString(1, Id); // this replaces the 1st "?" in the query for
									// username
			pst.setString(2, pass); // this replaces the 2st "?" in the query
									// for password
			// executes the prepared statement
			rs = pst.executeQuery();
			if (rs.next()) {
				// TRUE iff the query founds any corresponding data
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			//  Auto-generated catch block
			System.out.println("error while validating" + e);
			return false;
		}
	}
	public Boolean verifyuser(String Id){
		try{
			idps.setString(1,Id);
			rs=idps.executeQuery();
			if(rs.next()){
			System.out.println("Success");
			}
			else {
				return false;
			}
			return true;
		}catch(Exception e){
			System.out.println("Error");
			return false;
		}
	}
	
	

}
