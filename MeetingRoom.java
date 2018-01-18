
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

public class MeetingRoom extends HomePage implements ActionListener {
	JFrame frame1, frame2;
	static JTable table;
	String[] columnNames = { "User", "room status", "Booking Date", "Booked For", "Booking Time", "Slot from",
			"Slot to", "room no", "Slot no", "society", "coordinator", "purpose", "fname", "lname", "mobile", "email" };
	String rn, sn, date, ids, textvalue, slotno, slotfrom, slotto, societynamevalue, sqldate;
	Time st, sf, bt;
	Date bd, bf, dat, bookdate;
	Timer tm, tm2;
	JButton slot1[] = new JButton[8];
	JButton slot2[] = new JButton[8];
	JButton slot3[] = new JButton[8];
	JButton slot4[] = new JButton[8];
	JButton slot5[] = new JButton[8];
	JButton slot6[] = new JButton[8];
	int w = 0, s = 0, ss = 0, i, j, hour, min, months, month1 = 0, slot = 0, slotnocheck = 0, year = 2017, color = 1,
			repeate, slotforroom1, slotbooked1;
	long sec1 = 000;
	int slotcheck1 = 0, slotcheck2 = 0, slotcheck3 = 0, slotcheck4 = 0, slotcheck5 = 0, slotcheck6 = 0;
	PreparedStatement preparedStmt, pst, idps, pst2;
	Connection con, con1;
	ResultSet rs;
	Time time;
	JTextField fname, lname, phn, e_mail, cordinator, staffnocancel, e_mailcancel;
	JLabel fnamelabel, lnamelabel, phnlabel, e_maillabel, cordinatorlabel, purposelabel, societylabel, from, to,
			e_maillabelcancel, staffnolabelcancel, selectslot, fill;
	JTextArea purpose;
	JComboBox<String> societyname, slot_time_from, slot_time_to;
	String socities[] = { "ATS", "Enactus", "ACM", "Encore", "ASQ" };

	String slot_from_time[] = { "08:30:00", "09:31:00", "10:31:00", "11:31:00", "12:31:00", "01:31:00", "02:31:00",
			"03:31:00" };
	String slot_to_time[] = { "09:30:00", "10:30:00", "11:30:00", "12:30:00", "01:30:00", "02:30:00", "03:30:00",
			"04:30:00" };

	JButton confirm, logout, mybookings, show, close, verify, closecancel, cancelcancel;
	JButton b[][] = new JButton[7][8];
	int x, y = 250;
	int r1 = 0, s1 = 0, r2 = 0, s2 = 0, r3 = 0, s3 = 0, r4 = 0, s4 = 0, r5 = 0, s5 = 0, r6 = 0, s6 = 0;
	int k = 0, t = 0, c, d1 = 1, d2 = 1, d3 = 1, d4 = 1, d5 = 1, d6 = 1, d11 = 1, d21 = 1, d31 = 1, d41 = 1, d51 = 1,
			d61 = 1, f, g, h, l, l1, l2, l3, l4, l5, l6;
	static int count = 1, flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0;
	JPanel room1, room2, room3, room4, room5, room6, BookingDetails, cancelbooking,emailsending;
	JLabel ro1, ro2, ro3, ro4, ro5, ro6, logoutlabel;
	Month month = null;
	ZoneId zone1 = ZoneId.of("Asia/Calcutta");
	LocalDateTime currenttimedate = LocalDateTime.now(zone1);
	LocalTime localcurrenttime = LocalTime.now(zone1);
	Time currenttime = Time.valueOf(localcurrenttime);
	LocalDate localcurrentdate = LocalDate.now(zone1);
	Image iconf;
	Pattern pa = Pattern.compile("[0-9]");// restrictions all symbols
	JLabel im;
	@SuppressWarnings("deprecation")
	MeetingRoom() throws SQLException {

		frame1 = new JFrame("Meeting Room");
		iconf = Toolkit.getDefaultToolkit().getImage("images.jpeg");
		frame1.setIconImage(iconf);
		frame1.setVisible(true);
		frame1.setLayout(null);
		frame1.setSize(1500, 1500);
		im=new JLabel(new ImageIcon("p3.jpg"));
		frame1.setContentPane(im);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2 = new JFrame("Database Search Result");
		frame2.setLayout(new BorderLayout());
		frame2.setVisible(false);

		room1 = new JPanel();
		room2 = new JPanel();
		room3 = new JPanel();
		room4 = new JPanel();
		room5 = new JPanel();
		room6 = new JPanel();

		emailsending=new JPanel();
		
		
		frame1.add(room1);
		frame1.add(room2);
		frame1.add(room3);
		frame1.add(room4);
		frame1.add(room5);
		frame1.add(room6);
		frame1.add(emailsending);
		room1.setBounds(100, 150, 700, 700);
		room2.setBounds(100, 150, 700, 700);
		room3.setBounds(100, 150, 700, 700);
		room4.setBounds(100, 150, 700, 700);
		room5.setBounds(100, 150, 700, 700);
		room6.setBounds(100, 150, 700, 700);
		emailsending.setBounds(500,150,150,100);
		room1.setLayout(null);
		room2.setLayout(null);
		room3.setLayout(null);
		room4.setLayout(null);
		room5.setLayout(null);
		room6.setLayout(null);
		
		emailsending.setLayout(null);
		emailsending.setVisible(false);
		
		room1.setVisible(false);
		room1.setOpaque(false);

		room2.setVisible(false);
		room2.setOpaque(false);

		room3.setVisible(false);
		room3.setOpaque(false);

		room4.setVisible(false);
		room4.setOpaque(false);

		room5.setVisible(false);
		room5.setOpaque(false);

		room6.setVisible(false);
		room6.setOpaque(false);

		ro1 = new JLabel("Auditorium");
		ro2 = new JLabel("Seminar Hall");
		ro3 = new JLabel("Room1");
		ro4 = new JLabel("Room2");
		ro5 = new JLabel("Room3");
		ro6 = new JLabel("Room4");
		
		JLabel mail=new JLabel("Sending mail");
		emailsending.add(mail);
		mail.setBounds(30,40,100,20);
		selectslot = new JLabel("Select From Available Slot");
		logoutlabel = new JLabel("Welcome  " + userid);
		logout = new JButton("Log Out");
		mybookings = new JButton("Select Room");
		show = new JButton("History");
		
		frame1.add(ro1);
		frame1.add(ro2);
		frame1.add(ro3);
		frame1.add(ro4);
		frame1.add(ro5);
		frame1.add(ro6);
		frame1.add(selectslot);
		frame1.add(logoutlabel);
		frame1.add(logout);
		frame1.add(mybookings);
		frame1.add(show);

		selectslot.setVisible(false);
		// show.setVisible(false);
		logout.setVisible(true);
		selectslot.setBounds(100, 160, 200, 30);
		ro1.setBounds(650, 160, 100, 30);
		ro2.setBounds(650, 160, 100, 30);
		ro3.setBounds(650, 160, 100, 30);
		ro4.setBounds(650, 160, 100, 30);
		ro5.setBounds(650, 160, 100, 30);
		ro6.setBounds(650, 160, 100, 30);

		ro1.setVisible(false);
		ro2.setVisible(false);
		ro3.setVisible(false);
		ro4.setVisible(false);
		ro5.setVisible(false);
		ro6.setVisible(false);

		// Cancel Booking start
		cancelbooking = new JPanel();
		frame1.add(cancelbooking);
		cancelbooking.setBounds(500, 200, 500, 500);
		cancelbooking.setLayout(null);
		cancelbooking.setVisible(false);

		staffnolabelcancel = new JLabel("Username *");
		e_maillabelcancel = new JLabel("E-mail *");

		staffnocancel = new JTextField();
		e_mailcancel = new JTextField();

		cancelcancel = new JButton("Cancel Booking");
		closecancel = new JButton("close");

		cancelbooking.add(staffnocancel);
		cancelbooking.add(e_mailcancel);
		cancelbooking.add(staffnolabelcancel);
		cancelbooking.add(e_maillabelcancel);
		cancelbooking.add(closecancel);
		cancelbooking.add(cancelcancel);

		staffnolabelcancel.setBounds(10, 50, 100, 30);
		e_maillabelcancel.setBounds(10, 100, 100, 30);

		staffnocancel.setBounds(120, 50, 100, 30);
		e_mailcancel.setBounds(120, 100, 100, 30);

		cancelcancel.setBounds(100, 150, 150, 40);
		closecancel.setBounds(250, 150, 100, 40);

		cancelcancel.addActionListener(this);
		closecancel.addActionListener(this);
		// Cancel Booking end

		///////// Booking details panel
		BookingDetails = new JPanel();
		frame1.add(BookingDetails);
		BookingDetails.setBounds(500, 150, 500, 500);
		BookingDetails.setLayout(null);
		BookingDetails.setVisible(false);

		verify = new JButton("Verify");
		close = new JButton("close");
		confirm = new JButton("Confirm");

		fname = new JTextField();
		lname = new JTextField();
		phn = new JTextField();
		e_mail = new JTextField();

		fill = new JLabel("Fill the details");
		fnamelabel = new JLabel("First name *");
		lnamelabel = new JLabel("Last name *");
		phnlabel = new JLabel("Mobile no *");
		e_maillabel = new JLabel("E-mail *");
		purposelabel = new JLabel("Purpose of Booking *");
		cordinatorlabel = new JLabel("Faculty coordinator *");
		societylabel = new JLabel("Socities *");

		societyname = new JComboBox<String>(socities);
		slot_time_from = new JComboBox<String>(slot_from_time);
		slot_time_to = new JComboBox<String>(slot_to_time);

		dat = Date.valueOf(LocalDate.now(zone1));
		purpose = new JTextArea();
		purpose.setBorder(new LineBorder(Color.black, 1));
		cordinator = new JTextField();

		BookingDetails.add(fill);
		BookingDetails.add(fname);
		BookingDetails.add(lname);
		BookingDetails.add(phn);
		BookingDetails.add(e_mail);
		BookingDetails.add(societyname);
		BookingDetails.add(verify);
		BookingDetails.add(purpose);
		BookingDetails.add(cordinator);

		BookingDetails.add(fnamelabel);
		BookingDetails.add(lnamelabel);
		BookingDetails.add(phnlabel);
		BookingDetails.add(e_maillabel);
		BookingDetails.add(societylabel);
		BookingDetails.add(purposelabel);
		BookingDetails.add(cordinatorlabel);

		BookingDetails.add(confirm);
		BookingDetails.add(close);

		fill.setBounds(10, 50, 200, 30);
		fnamelabel.setBounds(10, 90, 100, 30);
		lnamelabel.setBounds(10, 130, 100, 30);
		phnlabel.setBounds(10, 170, 100, 30);
		e_maillabel.setBounds(10, 210, 100, 30);
		societylabel.setBounds(10, 250, 100, 30);
		cordinatorlabel.setBounds(10, 280, 150, 30);
		purposelabel.setBounds(10, 320, 200, 30);

		fname.setBounds(120, 90, 100, 30);
		lname.setBounds(120, 130, 100, 30);
		phn.setBounds(120, 170, 100, 30);
		e_mail.setBounds(120, 210, 100, 30);
		societyname.setBounds(120, 250, 100, 30);
		verify.setBounds(230, 250, 100, 30);
		cordinator.setBounds(150, 280, 200, 30);
		purpose.setBounds(10, 350, 300, 70);

		confirm.setBounds(100, 450, 100, 40);
		close.setBounds(200, 450, 100, 40);

		verify.addActionListener(this);
		confirm.addActionListener(this);
		close.addActionListener(this);
		///// ending of Booking details panel
		logoutlabel.setBounds(1250, 20, 150, 40);
		logout.setBounds(1300, 60, 100, 40);
		mybookings.setBounds(400, 30, 200, 40);
		show.setBounds(270, 30, 100, 40);
		/// Room Created 6

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (i == 0) {
					if (j == 0) {
						b[i][j] = new JButton("Auditorium");
					} else if (j == 1) {
						b[i][j] = new JButton("Seminar Hall");
					} else {
						b[i][j] = new JButton(String.valueOf("Room" + count));
						count++;
					}
				} else {
					b[i][j] = new JButton(String.valueOf("Room" + count));
					count++;
				}
				frame1.add(b[i][j]);
				b[i][j].setBackground(Color.GREEN);
				b[i][j].setOpaque(true);
				b[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
				b[i][j].addActionListener(this);
				b[i][j].setFocusPainted(false);
				b[i][j].setVisible(false);
				if (j >= 4) {
					x = 120;
				} else if (j < 4) {
					x = 10;
				}
				b[i][j].setBounds(x + 150 * j, y + 150 * i, 100, 99);
				b[i][j].repaint();
			}
		}


		
		
		//// Room ended

		frame1.setLocationRelativeTo(null);

		// Created Slots
		int a = 8, b = 30, p = 9, q = 20;
		for (int i = 0; i < 8; i++) {
			if (p > 12) {
				p = 1;
			}
			if (a > 12) {
				a = 1;
			}
			slot1[k] = new JButton(String.valueOf(a + ":" + b + "-" + p + ":" + q));
			room1.add(slot1[k]);
			if (k == 0 || k == 1) {
				slot1[k].setBounds(10, 30 * d1 + 20 * d1, 150, 40);
				d1++;
			}
			if (k > 1) {
				slot1[k].setBounds(10, 30 * d1 + 20 * d1, 150, 40);
				d1++;
			}
			slot1[k].setBackground(Color.GREEN);
			slot1[k].setOpaque(true);
			slot1[k].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
			slot1[k].addActionListener(this);
			slot1[k].setFocusPainted(false);
			///
			slot2[k] = new JButton(String.valueOf(a + ":" + b + "-" + p + ":" + q));
			room2.add(slot2[k]);
			if (k == 0 || k == 1) {
				slot2[k].setBounds(10, 30 * d2 + 20 * d2, 150, 40);
				d2++;
			}
			if (k > 1) {

				slot2[k].setBounds(10, 30 * d2 + 20 * d2, 150, 40);
				d2++;
			}
			slot2[k].setBackground(Color.GREEN);
			slot2[k].setOpaque(true);
			slot2[k].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
			slot2[k].addActionListener(this);
			slot2[k].setFocusPainted(false);
			////
			slot3[k] = new JButton(String.valueOf(a + ":" + b + "-" + p + ":" + q));
			room3.add(slot3[k]);
			if (k == 0 || k == 1) {
				slot3[k].setBounds(10, 30 * d3 + 20 * d3, 150, 40);
				d3++;
			}
			if (k > 1) {

				slot3[k].setBounds(10, 30 * d3 + 20 * d3, 150, 40);
				d3++;
			}
			slot3[k].setBackground(Color.GREEN);
			slot3[k].setOpaque(true);
			slot3[k].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
			slot3[k].addActionListener(this);
			slot3[k].setFocusPainted(false);
			////
			slot4[k] = new JButton(String.valueOf(a + ":" + b + "-" + p + ":" + q));
			room4.add(slot4[k]);
			if (k == 0 || k == 1) {
				slot4[k].setBounds(10, 30 * d4 + 20 * d4, 150, 40);
				d4++;
			}
			if (k > 1) {

				slot4[k].setBounds(10, 30 * d4 + 20 * d4, 150, 40);
				d4++;
			}
			slot4[k].setBackground(Color.GREEN);
			slot4[k].setOpaque(true);
			slot4[k].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
			slot4[k].addActionListener(this);
			slot4[k].setFocusPainted(false);
			/////
			slot5[k] = new JButton(String.valueOf(a + ":" + b + "-" + p + ":" + q));
			room5.add(slot5[k]);
			if (k == 0 || k == 1) {
				slot5[k].setBounds(10, 30 * d5 + 20 * d5, 150, 40);
				d5++;
			}
			if (k > 1) {
				slot5[k].setBounds(10, 30 * d5 + 20 * d5, 150, 40);
				d5++;
			}
			slot5[k].setBackground(Color.GREEN);
			slot5[k].setOpaque(true);
			slot5[k].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
			slot5[k].addActionListener(this);
			slot5[k].setFocusPainted(false);
			//////
			slot6[k] = new JButton(String.valueOf(a + ":" + b + "-" + p + ":" + q));
			room6.add(slot6[k]);
			if (k == 0 || k == 1) {
				slot6[k].setBounds(10, 30 * d6 + 20 * d6, 150, 40);
				d6++;
			}
			if (k > 1) {
				slot6[k].setBounds(10, 30 * d6 + 20 * d6, 150, 40);
				d6++;
			}
			slot6[k].setBackground(Color.GREEN);
			slot6[k].setOpaque(true);
			slot6[k].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
			slot6[k].addActionListener(this);
			slot6[k].setFocusPainted(false);
			a++;
			p++;
			k++;
		}

		dat = Date.valueOf(LocalDate.now(zone1));
		logout.addActionListener(this);
		mybookings.addActionListener(this);
		show.addActionListener(this);
		e_mail.addActionListener(this);
		connection();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {

				if (e.getSource() == b[i][j]) {
					r1 = i;
					s1 = j;

					selectdate();
					if (date.equals("")) {
						JOptionPane.showMessageDialog(null, "Select date first");
					}

					else {
						// getstatus();

						selectroom(); // Function called for room selection
						selectslot.setVisible(true);

					}
				}
			}
		}
		// 111111
		for (c = 0; c < 8; c++) {
			if (e.getSource() == slot1[c]) {
				f = c;
				l1 = f;
				l = 1;
				System.out.println("l==" + l);
				selectslot.setVisible(false);

				if (slot1[c].getBackground() == Color.RED) {
					cancelbooking.setVisible(true);
					room1.setVisible(false);

				} else {
					room1.setVisible(false);
					BookingDetails.setVisible(true);

				}
			} else if (e.getSource() == slot2[c]) {
				l2 = c;
				f = c;
				l = 2;
				System.out.println("l==" + l);
				selectslot.setVisible(false);
				if (slot2[c].getBackground() == Color.RED) {
					cancelbooking.setVisible(true);
					room2.setVisible(false);

				} else {
					room2.setVisible(false);
					BookingDetails.setVisible(true);
				}

			} else if (e.getSource() == slot3[c]) {
				l3 = c;
				f = c;
				l = 3;
				System.out.println("l==" + l);
				selectslot.setVisible(false);
				if (slot3[c].getBackground() == Color.RED) {
					cancelbooking.setVisible(true);
					room3.setVisible(false);

				} else {
					room3.setVisible(false);
					BookingDetails.setVisible(true);
				}
			} else if (e.getSource() == slot4[c]) {
				l4 = c;
				l = 4;
				f = c;
				selectslot.setVisible(false);
				if (slot4[c].getBackground() == Color.RED) {
					cancelbooking.setVisible(true);
					room4.setVisible(false);

				} else {
					room4.setVisible(false);
					BookingDetails.setVisible(true);
				}
			} else if (e.getSource() == slot5[c]) {
				l5 = c;
				l = 5;
				f = c;
				selectslot.setVisible(false);
				if (slot5[c].getBackground() == Color.RED) {
					cancelbooking.setVisible(true);
					room5.setVisible(false);

				} else {
					room5.setVisible(false);
					BookingDetails.setVisible(true);
				}
			} else if (e.getSource() == slot6[c]) {
				l6 = c;
				f = c;
				l = 6;
				selectslot.setVisible(false);
				if (slot6[c].getBackground() == Color.RED) {
					cancelbooking.setVisible(true);
					room6.setVisible(false);

				} else {
					room6.setVisible(false);
					BookingDetails.setVisible(true);
				}
			}
		}
		if (e.getSource() == verify) {
			societynamevalue = societyname.getItemAt(societyname.getSelectedIndex());
		}
		if (e.getSource() == confirm) {
			
			confirmed();
		}

		if (e.getSource() == close || e.getSource() == closecancel) {

			BookingDetails.setVisible(false);
			cancelbooking.setVisible(false);
		}
		if (e.getSource() == logout) { // else
			frame1.setVisible(false);
			new HomePage();
		}

		if (e.getSource() == cancelcancel) {
			cancelbooking();
			cancelbooking.setVisible(false);
		}

		else if (e.getSource() == mybookings) {

			b[0][0].setVisible(true);
			b[0][1].setVisible(true);
			b[1][0].setVisible(true);
			b[1][1].setVisible(true);
			b[2][0].setVisible(true);
			b[2][1].setVisible(true);

			ro1.setVisible(false);
			ro2.setVisible(false);
			ro3.setVisible(false);
			ro4.setVisible(false);
			ro5.setVisible(false);
			ro6.setVisible(false);

			room1.setVisible(false);
			room2.setVisible(false);
			room3.setVisible(false);
			room4.setVisible(false);
			room5.setVisible(false);
			room6.setVisible(false);

			BookingDetails.setVisible(false);
			cancelbooking.setVisible(false);

		} else if (e.getSource() == show) {
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columnNames);
			table = new JTable();
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.setFillsViewportHeight(true);
			JScrollPane scroll = new JScrollPane(table);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			try {
				BookingDetails.setVisible(false);
				cancelbooking.setVisible(false);
				room1.setVisible(false);
				room2.setVisible(false);
				room3.setVisible(false);
				room4.setVisible(false);
				room5.setVisible(false);
				room6.setVisible(false);
				frame2.setVisible(true);
				String username = "", status = "", roomno = "", society, coordinator, purpose, fname, lname, mobile,
						email;
				Date bookingdate, bookedfor;
				Time bookingtime, slot_from, slot_to;
				long slot_no;

				System.out.println("I am user" + userid);
				String sql = "select * from olddatasave where staff_no ='" + userid + "'";

				PreparedStatement psr = con.prepareStatement(sql);
				ResultSet res = psr.executeQuery();
				int wp = 0;
				while (res.next()) {
					username = res.getString("staff_no");
					status = res.getString("roomstatus");
					bookingdate = res.getDate("bookingdate");
					bookedfor = res.getDate("bookedfor");
					bookingtime = res.getTime("bookingtime");
					slot_from = res.getTime("slot_from");
					slot_to = res.getTime("slot_to");
					roomno = res.getString("roomno");
					slot_no = res.getLong("slot_no");
					society = res.getString("society");
					coordinator = res.getString("coordinator");
					purpose = res.getString("purpose");
					fname = res.getString("fname");
					lname = res.getString("lname");
					mobile = res.getString("mobile");
					email = res.getString("email");

					model.addRow(new Object[] { username, status, bookingdate, bookingtime, bookedfor, slot_from,
							slot_to, roomno, slot_no, society, coordinator, purpose, fname, lname, mobile, email });
					wp++;
				}
				if (wp < 1) {
					JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
				}
				if (wp == 1) {
					System.out.println(wp + " Record Found");
				} else {
					System.out.println(wp + " Records Found");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			frame2.add(scroll);
			frame2.setVisible(true);
			frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}

	}

	public void getvalueof_k() throws SQLException {
		if (f == 0 || slot == 1) {
			slotto = "08:30:00";
			slotfrom = "09:30:00";
			hour = 9;
			min = 30;
			slotno = "1";

		} else if (f == 1) {
			slotto = "09:31:00";
			slotfrom = "10:30:00";
			hour = 10;
			min = 30;

			slotno = "2";
		} else if (f == 2) {
			slotto = "10:31:00";
			slotfrom = "11:30:00";
			hour = 11;
			min = 30;

			slotno = "3";
		} else if (f == 3) {
			slotto = "11:31:00";
			slotfrom = "12:30:00";
			hour = 12;
			min = 30;

			slotno = "4";
		} else if (f == 4) {
			slotto = "12:31:00";
			slotfrom = "01:30:00";
			hour = 01;
			min = 30;

			slotno = "5";
		} else if (f == 5) {
			slotto = "01:31:00";
			slotfrom = "02:30:00";
			hour = 02;
			min = 30;

			slotno = "6";
		} else if (f == 6) {
			slotto = "02:31:00";
			slotfrom = "03:30:00";
			hour = 03;
			min = 30;

			slotno = "7";
		} else if (f == 7) {
			slotto = "03:31:00";
			slotfrom = "04:30:00";
			hour = 04;
			min = 30;

			slotno = "8";
		}
	}

	public void selectroom() {
		if (r1 == 0 && s1 == 0) {
			try {
				System.out.println("get in here");
				String timecheck = "select slot_to,bookedfor,slot_no from RoomBooking where roomno='C1'";
				Statement st1 = (Statement) con.createStatement();
				ResultSet rss1 = st1.executeQuery(timecheck);
				slotcheck1 = 0;
				
				while (rss1.next()) {
					Date getsqldate = rss1.getDate(2);
					sqldate=getsqldate.toString();
					System.out.println("Is the date"+sqldate);
					if (date.equals(sqldate)) {
						time = rss1.getTime(1);
						dat = rss1.getDate(2);
						slotnocheck = rss1.getInt("slot_no");
						int day1 = dat.getDate();
						month1 = dat.getMonth() + 1;
						monthcal();
						int min1 = time.getMinutes();
						int hour1 = time.getHours();

						LocalDateTime newDate1 = LocalDateTime.of(2018, month, day1, hour1, min1, 00);
						Duration duration = Duration.between(currenttimedate, newDate1);

						sec1 = duration.getSeconds();
						System.out.println("Second >>>>>>>" + sec1);
						if (sec1 >= 0) {
							slot1[slotnocheck - 1].setBackground(Color.RED);
							slotcheck1++;

							if (slotcheck1 == 8) {
								this.b[0][0].setBackground(Color.red);
							}

							else if (slotcheck1 > 1 && slotcheck1 < 4) {
								this.b[0][0].setBackground(Color.GREEN);
							}

							else if (slotcheck1 > 4 && slotcheck1 < 8) {
								this.b[0][0].setBackground(Color.YELLOW);
							}
						} else if (sec1 < 0) {
							slot = rss1.getInt("slot_no");
							String add = "INSERT into olddatasave (SELECT * FROM RoomBooking WHERE slot_no=?)";
							String av = "update olddatasave set roomstatus='Avilable'";
							PreparedStatement pstt = con.prepareStatement(add);
							PreparedStatement pstt1 = con.prepareStatement(av);

							pstt.setLong(1, slot);
							pstt.execute();
							pstt1.execute();
							String remove = "delete from RoomBooking where slot_no=?";
							PreparedStatement pstt2 = con.prepareStatement(remove);
							pstt2.setLong(1, slot);
							pstt2.execute();

						}
						System.out.println("time....." + time + " \n booked for..." + dat + "\n Month of booked for..."
								+ month1 + "\nhours....." + hour1 + "\nmin....." + min1);

					}
					else 
						repeate =0;

				}

				
			} catch (Exception ti) {

			}
			room1.setVisible(true);
			room2.setVisible(false);
			room3.setVisible(false);
			room4.setVisible(false);
			room5.setVisible(false);
			room6.setVisible(false);

			ro1.setVisible(true);
			ro2.setVisible(false);
			ro3.setVisible(false);
			ro4.setVisible(false);
			ro5.setVisible(false);
			ro6.setVisible(false);

			b[0][0].setVisible(false);
			b[0][1].setVisible(false);
			b[1][0].setVisible(false);
			b[1][1].setVisible(false);
			b[2][0].setVisible(false);
			b[2][1].setVisible(false);
		} else if (r1 == 0 && s1 == 1) {
			
			try {
				System.out.println("get in here");
				String timecheck = "select slot_to,bookedfor,slot_no from RoomBooking where roomno='C2'";
				Statement st2 = (Statement) con.createStatement();
				ResultSet rss2 = st2.executeQuery(timecheck);
				slotcheck2 = 0;
				
				while (rss2.next()) {
					Date getsqldate = rss2.getDate(2);
					sqldate=getsqldate.toString();
					System.out.println("Is the date"+sqldate);
					if (date.equals(sqldate)) {
						time = rss2.getTime(1);
						dat = rss2.getDate(2);
						slotnocheck = rss2.getInt("slot_no");
						int day1 = dat.getDate();
						month1 = dat.getMonth() + 1;
						monthcal();
						int min1 = time.getMinutes();
						int hour1 = time.getHours();

						LocalDateTime newDate2 = LocalDateTime.of(2018, month, day1, hour1, min1, 00);
						Duration duration = Duration.between(currenttimedate, newDate2);

						sec1 = duration.getSeconds();
						System.out.println("Second >>>>>>>" + sec1);
						if (sec1 >= 0) {
							slot2[slotnocheck - 1].setBackground(Color.RED);
							slotcheck2++;

							if (slotcheck2 == 8) {
								this.b[0][0].setBackground(Color.red);
							}

							else if (slotcheck2 > 1 && slotcheck2 < 4) {
								this.b[0][0].setBackground(Color.GREEN);
							}

							else if (slotcheck2 > 4 && slotcheck2 < 8) {
								this.b[0][0].setBackground(Color.YELLOW);
							}
						} else if (sec1 < 0) {
							slot = rss2.getInt("slot_no");
							String add = "INSERT into olddatasave (SELECT * FROM RoomBooking WHERE slot_no=?)";
							String av = "update olddatasave set roomstatus='Avilable'";
							PreparedStatement pstt = con.prepareStatement(add);
							PreparedStatement pstt1 = con.prepareStatement(av);

							pstt.setLong(1, slot);
							pstt.execute();
							pstt1.execute();
							String remove = "delete from RoomBooking where slot_no=?";
							PreparedStatement pstt2 = con.prepareStatement(remove);
							pstt2.setLong(1, slot);
							pstt2.execute();

						}
						System.out.println("time....." + time + " \n booked for..." + dat + "\n Month of booked for..."
								+ month1 + "\nhours....." + hour1 + "\nmin....." + min1);

					}
					else 
						repeate =0;

				}

				
			} catch (Exception ti) {

			}
			
			
			
			
			
			room2.setVisible(true);
			room1.setVisible(false);
			room3.setVisible(false);
			room4.setVisible(false);
			room5.setVisible(false);
			room6.setVisible(false);

			ro2.setVisible(true);
			ro1.setVisible(false);
			ro3.setVisible(false);
			ro4.setVisible(false);
			ro5.setVisible(false);
			ro6.setVisible(false);

			b[0][0].setVisible(false);
			b[0][1].setVisible(false);
			b[1][0].setVisible(false);
			b[1][1].setVisible(false);
			b[2][0].setVisible(false);
			b[2][1].setVisible(false);

		} else if (r1 == 1 && s1 == 0) {
			
			
			try {
				System.out.println("get in here");
				String timecheck = "select slot_to,bookedfor,slot_no from RoomBooking where roomno='C3'";
				Statement st3 = (Statement) con.createStatement();
				ResultSet rss3 = st3.executeQuery(timecheck);
				slotcheck3 = 0;
				
				while (rss3.next()) {
					Date getsqldate = rss3.getDate(2);
					sqldate=getsqldate.toString();
					System.out.println("Is the date"+sqldate);
					if (date.equals(sqldate)) {
						time = rss3.getTime(1);
						dat = rss3.getDate(2);
						slotnocheck = rss3.getInt("slot_no");
						int day1 = dat.getDate();
						month1 = dat.getMonth() + 1;
						monthcal();
						int min1 = time.getMinutes();
						int hour1 = time.getHours();

						LocalDateTime newDate3 = LocalDateTime.of(2018, month, day1, hour1, min1, 00);
						Duration duration = Duration.between(currenttimedate, newDate3);

						sec1 = duration.getSeconds();
						System.out.println("Second >>>>>>>" + sec1);
						if (sec1 >= 0) {
							slot3[slotnocheck - 1].setBackground(Color.RED);
							slotcheck3++;

							if (slotcheck3 == 8) {
								this.b[0][0].setBackground(Color.red);
							}

							else if (slotcheck3 > 1 && slotcheck3 < 4) {
								this.b[0][0].setBackground(Color.GREEN);
							}

							else if (slotcheck3 > 4 && slotcheck3 < 8) {
								this.b[0][0].setBackground(Color.YELLOW);
							}
						} else if (sec1 < 0) {
							slot = rss3.getInt("slot_no");
							String add = "INSERT into olddatasave (SELECT * FROM RoomBooking WHERE slot_no=?)";
							String av = "update olddatasave set roomstatus='Avilable'";
							PreparedStatement pstt = con.prepareStatement(add);
							PreparedStatement pstt1 = con.prepareStatement(av);

							pstt.setLong(1, slot);
							pstt.execute();
							pstt1.execute();
							String remove = "delete from RoomBooking where slot_no=?";
							PreparedStatement pstt2 = con.prepareStatement(remove);
							pstt2.setLong(1, slot);
							pstt2.execute();

						}
						System.out.println("time....." + time + " \n booked for..." + dat + "\n Month of booked for..."
								+ month1 + "\nhours....." + hour1 + "\nmin....." + min1);

					}
					else 
						repeate =0;

				}

				
			} catch (Exception ti) {

			}
			
			room3.setVisible(true);
			room2.setVisible(false);
			room1.setVisible(false);
			room4.setVisible(false);
			room5.setVisible(false);
			room6.setVisible(false);

			ro3.setVisible(true);
			ro2.setVisible(false);
			ro1.setVisible(false);
			ro4.setVisible(false);
			ro5.setVisible(false);
			ro6.setVisible(false);

			b[0][0].setVisible(false);
			b[0][1].setVisible(false);
			b[1][0].setVisible(false);
			b[1][1].setVisible(false);
			b[2][0].setVisible(false);
			b[2][1].setVisible(false);
		} else if (r1 == 1 && s1 == 1) {
			
			
			try {
				System.out.println("get in here");
				String timecheck = "select slot_to,bookedfor,slot_no from RoomBooking where roomno='C4'";
				Statement st4 = (Statement) con.createStatement();
				ResultSet rss4 = st4.executeQuery(timecheck);
				slotcheck4= 0;
				
				while (rss4.next()) {
					Date getsqldate = rss4.getDate(2);
					sqldate=getsqldate.toString();
					System.out.println("Is the date"+sqldate);
					if (date.equals(sqldate)) {
						time = rss4.getTime(1);
						dat = rss4.getDate(2);
						slotnocheck = rss4.getInt("slot_no");
						int day1 = dat.getDate();
						month1 = dat.getMonth() + 1;
						monthcal();
						int min1 = time.getMinutes();
						int hour1 = time.getHours();

						LocalDateTime newDate4 = LocalDateTime.of(2018, month, day1, hour1, min1, 00);
						Duration duration = Duration.between(currenttimedate, newDate4);

						sec1 = duration.getSeconds();
						System.out.println("Second >>>>>>>" + sec1);
						if (sec1 >= 0) {
							slot4[slotnocheck - 1].setBackground(Color.RED);
							slotcheck4++;

							if (slotcheck4 == 8) {
								this.b[0][0].setBackground(Color.red);
							}

							else if (slotcheck4 > 1 && slotcheck4< 4) {
								this.b[0][0].setBackground(Color.GREEN);
							}

							else if (slotcheck4 > 4 && slotcheck4 < 8) {
								this.b[0][0].setBackground(Color.YELLOW);
							}
						} else if (sec1 < 0) {
							slot = rss4.getInt("slot_no");
							String add = "INSERT into olddatasave (SELECT * FROM RoomBooking WHERE slot_no=?)";
							String av = "update olddatasave set roomstatus='Avilable'";
							PreparedStatement pstt = con.prepareStatement(add);
							PreparedStatement pstt1 = con.prepareStatement(av);

							pstt.setLong(1, slot);
							pstt.execute();
							pstt1.execute();
							String remove = "delete from RoomBooking where slot_no=?";
							PreparedStatement pstt2 = con.prepareStatement(remove);
							pstt2.setLong(1, slot);
							pstt2.execute();

						}
						System.out.println("time....." + time + " \n booked for..." + dat + "\n Month of booked for..."
								+ month1 + "\nhours....." + hour1 + "\nmin....." + min1);

					}
					else 
						repeate =0;

				}

				
			} catch (Exception ti) {

			}
			
			room4.setVisible(true);
			room2.setVisible(false);
			room3.setVisible(false);
			room1.setVisible(false);
			room5.setVisible(false);
			room6.setVisible(false);

			ro4.setVisible(true);
			ro2.setVisible(false);
			ro3.setVisible(false);
			ro1.setVisible(false);
			ro5.setVisible(false);
			ro6.setVisible(false);

			b[0][0].setVisible(false);
			b[0][1].setVisible(false);
			b[1][0].setVisible(false);
			b[1][1].setVisible(false);
			b[2][0].setVisible(false);
			b[2][1].setVisible(false);

		} else if (r1 == 2 && s1 == 0) {
			
			try {
				System.out.println("get in here");
				String timecheck = "select slot_to,bookedfor,slot_no from RoomBooking where roomno='C5'";
				Statement st5 = (Statement) con.createStatement();
				ResultSet rss5 = st5.executeQuery(timecheck);
				slotcheck1 = 0;
				
				while (rss5.next()) {
					Date getsqldate = rss5.getDate(2);
					sqldate=getsqldate.toString();
					System.out.println("Is the date"+sqldate);
					if (date.equals(sqldate)) {
						time = rss5.getTime(1);
						dat = rss5.getDate(2);
						slotnocheck = rss5.getInt("slot_no");
						int day1 = dat.getDate();
						month1 = dat.getMonth() + 1;
						monthcal();
						int min1 = time.getMinutes();
						int hour1 = time.getHours();

						LocalDateTime newDate5 = LocalDateTime.of(2018, month, day1, hour1, min1, 00);
						Duration duration = Duration.between(currenttimedate, newDate5);

						sec1 = duration.getSeconds();
						System.out.println("Second >>>>>>>" + sec1);
						if (sec1 >= 0) {
							slot5[slotnocheck - 1].setBackground(Color.RED);
							slotcheck5++;

							if (slotcheck5 == 8) {
								this.b[0][0].setBackground(Color.red);
							}

							else if (slotcheck5 > 1 && slotcheck5 < 4) {
								this.b[0][0].setBackground(Color.GREEN);
							}

							else if (slotcheck5 > 4 && slotcheck5 < 8) {
								this.b[0][0].setBackground(Color.YELLOW);
							}
						} else if (sec1 < 0) {
							slot = rss5.getInt("slot_no");
							String add = "INSERT into olddatasave (SELECT * FROM RoomBooking WHERE slot_no=?)";
							String av = "update olddatasave set roomstatus='Avilable'";
							PreparedStatement pstt = con.prepareStatement(add);
							PreparedStatement pstt1 = con.prepareStatement(av);

							pstt.setLong(1, slot);
							pstt.execute();
							pstt1.execute();
							String remove = "delete from RoomBooking where slot_no=?";
							PreparedStatement pstt2 = con.prepareStatement(remove);
							pstt2.setLong(1, slot);
							pstt2.execute();

						}
						System.out.println("time....." + time + " \n booked for..." + dat + "\n Month of booked for..."
								+ month1 + "\nhours....." + hour1 + "\nmin....." + min1);

					}
					else 
						repeate =0;

				}

				
			} catch (Exception ti) {

			}
			
			room5.setVisible(true);
			room2.setVisible(false);
			room3.setVisible(false);
			room4.setVisible(false);
			room1.setVisible(false);
			room6.setVisible(false);

			ro5.setVisible(true);
			ro2.setVisible(false);
			ro3.setVisible(false);
			ro4.setVisible(false);
			ro1.setVisible(false);
			ro6.setVisible(false);

			b[0][0].setVisible(false);
			b[0][1].setVisible(false);
			b[1][0].setVisible(false);
			b[1][1].setVisible(false);
			b[2][0].setVisible(false);
			b[2][1].setVisible(false);

		} else if (r1 == 2 && s1 == 1) {
			try {
				String timecheck = "select slot_to,bookedfor,slot_no from RoomBooking where roomno='C6'";
				Statement st6 = (Statement) con.createStatement();
				ResultSet rss6 = st6.executeQuery(timecheck);
				slotcheck6 = 0;
				
				while (rss6.next()) {
					Date getsqldate = rss6.getDate(2);
					sqldate=getsqldate.toString();
					System.out.println("Is the date"+sqldate);
					if (date.equals(sqldate)) {
						time = rss6.getTime(1);
						dat = rss6.getDate(2);
						slotnocheck = rss6.getInt("slot_no");
						int day1 = dat.getDate();
						month1 = dat.getMonth() + 1;
						monthcal();
						int min1 = time.getMinutes();
						int hour1 = time.getHours();

						LocalDateTime newDate6 = LocalDateTime.of(2018, month, day1, hour1, min1, 00);
						Duration duration = Duration.between(currenttimedate, newDate6);

						sec1 = duration.getSeconds();
						System.out.println("Second >>>>>>>" + sec1);
						if (sec1 >= 0) {
							slot1[slotnocheck - 1].setBackground(Color.RED);
							slotcheck6++;

							if (slotcheck6 == 8) {
								this.b[0][0].setBackground(Color.red);
							}

							else if (slotcheck6 > 1 && slotcheck6 < 4) {
								this.b[0][0].setBackground(Color.GREEN);
							}

							else if (slotcheck6 > 4 && slotcheck6 < 8) {
								this.b[0][0].setBackground(Color.YELLOW);
							}
						} else if (sec1 < 0) {
							slot = rss6.getInt("slot_no");
							String add = "INSERT into olddatasave (SELECT * FROM RoomBooking WHERE slot_no=?)";
							String av = "update olddatasave set roomstatus='Avilable'";
							PreparedStatement pstt = con.prepareStatement(add);
							PreparedStatement pstt1 = con.prepareStatement(av);

							pstt.setLong(1, slot);
							pstt.execute();
							pstt1.execute();
							String remove = "delete from RoomBooking where slot_no=?";
							PreparedStatement pstt2 = con.prepareStatement(remove);
							pstt2.setLong(1, slot);
							pstt2.execute();

						}
						System.out.println("time....." + time + " \n booked for..." + dat + "\n Month of booked for..."
								+ month1 + "\nhours....." + hour1 + "\nmin....." + min1);

					}
					else 
						repeate =0;

				}

				
			} catch (Exception ti) {

			}
			room6.setVisible(true);
			room2.setVisible(false);
			room3.setVisible(false);
			room4.setVisible(false);
			room5.setVisible(false);
			room1.setVisible(false);

			ro6.setVisible(true);
			ro2.setVisible(false);
			ro3.setVisible(false);
			ro4.setVisible(false);
			ro5.setVisible(false);
			ro1.setVisible(false);

			b[0][0].setVisible(false);
			b[0][1].setVisible(false);
			b[1][0].setVisible(false);
			b[1][1].setVisible(false);
			b[2][0].setVisible(false);
			b[2][1].setVisible(false);
		}
	}

	public void connection() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database........");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FacilityManagement", "root", "");

			System.out.println("Connected to database........");
			pst = con.prepareStatement("select * from user where Id=? and password=?");

			idps = con.prepareStatement("select userid from RoomBook where userid=?");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Boolean verifyuser(String Id) {
		try {
			idps.setString(1, Id);
			rs = idps.executeQuery();
			if (rs.next()) {
				System.out.println("Success");
			} else {
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error");
			return false;
		}
	}

	public void monthcal() {
		if (month1 == 1 || months == 1) {
			month = Month.JANUARY;

		}
		if (month1 == 2 || months == 2) {
			month = Month.FEBRUARY;

		}
		if (month1 == 3 || months == 3) {
			month = Month.MARCH;

		}
		if (month1 == 4 || months == 4) {
			month = Month.APRIL;

		}
		if (month1 == 5 || months == 5) {
			month = Month.MAY;

		}
		if (month1 == 6 || months == 6) {
			month = Month.JUNE;

		}
		if (month1 == 7 || months == 7) {
			month = Month.JULY;

		}
		if (month1 == 8 || months == 8) {
			month = Month.AUGUST;

		}
		if (month1 == 9 || months == 9) {
			month = Month.SEPTEMBER;

		}
		if (month1 == 10 || months == 19) {
			month = Month.OCTOBER;

		}
		if (month1 == 11 || months == 11) {
			month = Month.NOVEMBER;

		}
		if (month1 == 12 || months == 12) {
			month = Month.DECEMBER;

		}
	}

	public void booked() {
		int dayy = Integer.parseInt(date.substring(8, 10));
		months = Integer.parseInt(date.substring(5, 7));
		monthcal();
		System.out.println("Day for book=" + dayy + "Month=" + months);
		LocalDateTime newt = LocalDateTime.of(2018, month, dayy, hour, min, 00);
		Duration du = Duration.between(currenttimedate, newt);
		long sec = du.getSeconds();
		System.out.println("Time period" + sec);
		if (sec >= 0) {
			try {
				String query = "insert into RoomBooking values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, userid);

				preparedStmt.setString(2, "Booked");
				preparedStmt.setDate(3, Date.valueOf(LocalDate.now(zone1)));

				preparedStmt.setString(4, date);

				preparedStmt.setTime(5, Time.valueOf(LocalTime.now(zone1)));
				getvalueof_k();
				System.out.println("f===  " + f);
				preparedStmt.setString(6, slotto);
				preparedStmt.setString(7, slotfrom);
				if (r1 == 0 && s1 == 0) {
					preparedStmt.setString(8, "C1");

					slotcheck1++;
					if (slotcheck1 == 8) {
						this.b[0][0].setBackground(Color.red);
					}

					else if (slotcheck1 >= 1 && slotcheck1 < 4) {
						this.b[0][0].setBackground(Color.GREEN);
					}

					else if (slotcheck1 >= 4 && slotcheck1 < 8) {
						this.b[0][0].setBackground(Color.YELLOW);
					}
				} else if (r1 == 0 && s1 == 1) {
					preparedStmt.setString(8, "C2");
					slotcheck2++;
					if (slotcheck2 == 8) {
						this.b[0][1].setBackground(Color.red);
					}

					else if (slotcheck2 > 1 && slotcheck2 < 4) {
						this.b[0][1].setBackground(Color.GREEN);
					}

					else if (slotcheck2 > 4 && slotcheck2 < 8) {
						this.b[0][1].setBackground(Color.YELLOW);
					}
				} else if (r1 == 1 && s1 == 0) {
					preparedStmt.setString(8, "C3");
					slotcheck3++;
					if (slotcheck3 == 8) {
						this.b[1][0].setBackground(Color.red);
					}

					else if (slotcheck3 > 1 && slotcheck3 < 4) {
						this.b[1][0].setBackground(Color.GREEN);
					}

					else if (slotcheck3 > 4 && slotcheck3 < 8) {
						this.b[1][0].setBackground(Color.YELLOW);
					}
				} else if (r1 == 1 && s1 == 1) {
					preparedStmt.setString(8, "C4");
					slotcheck4++;
					if (slotcheck4 == 8) {
						this.b[1][1].setBackground(Color.red);
					}

					else if (slotcheck4 > 1 && slotcheck4 < 4) {
						this.b[1][1].setBackground(Color.GREEN);
					}

					else if (slotcheck4 > 4 && slotcheck4 < 8) {
						this.b[1][1].setBackground(Color.YELLOW);
					}
				} else if (r1 == 2 && s1 == 0) {
					preparedStmt.setString(8, "C5");
					slotcheck5++;
					if (slotcheck5 == 8) {
						this.b[2][0].setBackground(Color.red);
					}

					else if (slotcheck5 > 1 && slotcheck5 < 4) {
						this.b[2][0].setBackground(Color.GREEN);
					}

					else if (slotcheck5 > 4 && slotcheck5 < 8) {
						this.b[2][0].setBackground(Color.YELLOW);
					}
				} else if (r1 == 2 && s1 == 1) {
					preparedStmt.setString(8, "C6");
					slotcheck6++;
					if (slotcheck6 == 8) {
						this.b[2][1].setBackground(Color.red);
					}

					else if (slotcheck6 > 1 && slotcheck6 < 4) {
						this.b[2][1].setBackground(Color.GREEN);
					}

					else if (slotcheck6 > 4 && slotcheck6 < 8) {
						this.b[2][1].setBackground(Color.YELLOW);
					}
				}
				preparedStmt.setString(9, slotno);
				preparedStmt.setString(10, societynamevalue);
				preparedStmt.setString(11, cordinator.getText());
				preparedStmt.setString(12, purpose.getText());
				preparedStmt.setString(13, fname.getText());
				preparedStmt.setString(14, lname.getText());
				preparedStmt.setString(15, phn.getText());
				preparedStmt.setString(16, e_mail.getText());

				try {

					b[r1][s1].setEnabled(true);
					if (l == 1) {
						slot1[l1].setBackground(Color.RED);
						// slot1[l1].setEnabled(false);
						room1.setVisible(true);
					} else if (l == 2) {
						slot2[l2].setBackground(Color.RED);
						// slot2[l2].setEnabled(false);
						room2.setVisible(true);
					} else if (l == 3) {
						slot3[l3].setBackground(Color.RED);
						// slot3[l3].setEnabled(false);
						room3.setVisible(true);
					}

					else if (l == 4) {
						slot4[l4].setBackground(Color.RED);
						// slot4[l4].setEnabled(false);
						room4.setVisible(true);
					} else if (l == 5) {
						slot5[l5].setBackground(Color.RED);
						// slot5[l5].setEnabled(false);
						room5.setVisible(true);
					} else if (l == 6) {
						slot6[l6].setBackground(Color.RED);
						// slot6[l6].setEnabled(false);
						room6.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				b[0][0].setEnabled(true);
				b[0][1].setEnabled(true);
				b[1][0].setEnabled(true);
				b[1][1].setEnabled(true);
				b[2][0].setEnabled(true);
				b[2][1].setEnabled(true);
				preparedStmt.execute();
				
				emailsending.setVisible(true);
				BookingDetails.setVisible(false);
				//JOptionPane.showConfirmDialog(null, "waiting");
				//System.out.println("WAITINGs");
				EmailSender.send("emailexampl1200@gmail.com", "J.@8800.k", e_mail.getText(), "Helo " + fname.getText(),
						"You have successfully booked");
				emailsending.setVisible(false);
				
				JOptionPane.showMessageDialog(null, "Booked");

				repeate = 0;

			} catch (Exception r) {
				System.out.println(r);
			}
		} else if (sec < 0) {
			JOptionPane.showMessageDialog(null, "Enter correct date");
			b[r1][s1].setEnabled(true);
		}
	}

	public void confirmed() {
		String input = e_mail.getText();
		Pattern patt = Pattern.compile("\\b[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,4}\\b");// restrictions all
															// symbols
		Matcher m = patt.matcher(input);
		if (m.find()) {
			if (societynamevalue == null || fname.getText().equals("") || e_mail.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter the valid input");
			}
			else if (societynamevalue != "") {
				BookingDetails.setVisible(false);
				
				b[r1][s1].setEnabled(true);
				booked();
				room1.setVisible(false);
				room2.setVisible(false);
				room3.setVisible(false);
				room4.setVisible(false);
				room5.setVisible(false);
				room6.setVisible(false);
				ro1.setVisible(false);
				ro2.setVisible(false);
				ro3.setVisible(false);
				ro4.setVisible(false);
				ro5.setVisible(false);
				ro6.setVisible(false);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please enter the valid input");
		}
	}

	public void selectdate() {
		JFrame frame = new JFrame("Date Picker");

		date = new DatePicker(frame).setPickedDate();
		
		System.out.println(date);
		// BookingDetails.setVisible(true);
	}

	public void cancelbooking() {
		try {
			String bookingcancel = "delete from RoomBooking where staff_no=? and bookedfor=? and slot_to=?";
			PreparedStatement p = con.prepareStatement(bookingcancel);
			p.setString(1, staffnocancel.getText());
			System.out.println("Username=========" + staffnocancel.getText());
			p.setString(2, date);
			getvalueof_k();
			System.out.println("Slot From===" + slotfrom + "and date is" + date);
			p.setString(3, slotfrom);
			p.execute();
		} catch (Exception E) {
			System.out.println(E);
		}
	}

}