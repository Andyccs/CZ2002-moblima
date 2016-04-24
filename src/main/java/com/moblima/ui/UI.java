package com.moblima.ui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.moblima.exception.CineplexException;
import com.moblima.exception.MovieListingException;
import com.moblima.exception.ShowTimeException;
import com.moblima.main.Main;
import com.moblima.movie.Cineplex;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieListing.Status;
import com.moblima.movie.ShowTime;
import com.moblima.users.Public;
import com.moblima.users.User;

public class UI extends JFrame implements ActionListener{
	private static final JButton BUTTON_FIND = new JButton("Find");;
	private static final JLabel LABEL_MOVIE = new JLabel("Movie");
	private static final JLabel LABEL_CINEPLEX = new JLabel("Location");
	private static final JLabel LABEL_DATE = new JLabel("Date");
	private static final JLabel LABEL_SHOWTIME = new JLabel("Date       Time    Location     Movie");
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel mainPanel;

	private JList<String> movieList;
	private JList<String> cineplexList;
	private JList<String> dateList;
	private JList<String> showtimeList;
	private ArrayList<ShowTime> showtimes;
	
	
	User person = new Public();
	
	public UI() throws HeadlessException {
		try {
			leftPanel = new JPanel();
			rightPanel = new JPanel();
			mainPanel = new JPanel();
			BUTTON_FIND.addActionListener(this);
			String movieName;
			
			//MOVIE LIST
			DefaultListModel<String> list1 = new DefaultListModel<String>();
			ArrayList<MovieListing> ml = person.queryMovie(Status.NOW_SHOWING);
			for (MovieListing movieListing : ml) {
				movieName = movieListing.getMovie().getName();
				list1.addElement(movieName);
			}
			movieList = new JList<String>(list1);
			movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			movieList.setLayoutOrientation(JList.VERTICAL);
			movieList.setVisibleRowCount(10);
			
			//Cineplex List
			DefaultListModel<String> list2 = new DefaultListModel<String>();
			ArrayList<Cineplex> cineplexes = person.getCineplex();
			String cineplexName;
			for (Cineplex cineplex : cineplexes) {
				cineplexName = cineplex.getName();
				list2.addElement(cineplexName);
			}
			cineplexList = new JList<String>(list2);
			cineplexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cineplexList.setLayoutOrientation(JList.VERTICAL);
			cineplexList.setVisibleRowCount(10);
			
			//Date List
			DefaultListModel<String> list3 = new DefaultListModel<String>();
			GregorianCalendar date = new GregorianCalendar(2013,10,20);
			String dateName;
			for(int i=0;i<10;i++){
				StringBuilder sb = new StringBuilder();
				
				if((date.get(Calendar.DAY_OF_MONTH))<10){
					sb.append("0");
				}
				sb.append((date.get(Calendar.DAY_OF_MONTH)));
				sb.append("\\");
				if((date.get(Calendar.MONTH)+1)<10){
					sb.append("0");
				}
				sb.append((date.get(Calendar.MONTH)+1));
				sb.append("\\");
				sb.append(date.get(Calendar.YEAR));
				dateName = sb.toString();
				list3.addElement(dateName);
				date.add(Calendar.DAY_OF_MONTH, 1);
			}
			dateList = new JList<String>(list3);
			dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dateList.setLayoutOrientation(JList.VERTICAL);
			dateList.setVisibleRowCount(10);
			
			//LEFT PANEL
			leftPanel.add(LABEL_DATE);
			leftPanel.add(dateList);
			leftPanel.add(LABEL_CINEPLEX);
			leftPanel.add(cineplexList);
			leftPanel.add(LABEL_MOVIE);
			leftPanel.add(movieList);
			leftPanel.add(BUTTON_FIND);
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
			
			//RIGHT PANEL
			rightPanel.add(LABEL_SHOWTIME);
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
			
			//MAIN PANEL
			mainPanel.add(leftPanel);
			mainPanel.add(rightPanel);
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
			this.add(mainPanel);
		} catch (MovieListingException e) {
			rightPanel.removeAll();
			JLabel errorLabel = new JLabel("No Show Time Found");
			rightPanel.add(errorLabel);
			rightPanel.revalidate();
			e.printStackTrace();
		} catch (CineplexException e) {
			rightPanel.removeAll();
			JLabel errorLabel = new JLabel("No Show Time Found");
			rightPanel.add(errorLabel);
			rightPanel.revalidate();
			e.printStackTrace();
		}

	}

	public UI(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public UI(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public UI(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		UI ui = new UI();
		ui.setTitle("Search movie for showtimes");
		ui.setSize(450, 500);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setVisible(true);
		ui.setLocation(new Point(400, 150));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == BUTTON_FIND){
			try {
				rightPanel.removeAll();
				String movieName = (String) movieList.getSelectedValue();
				String cineplexName = (String) cineplexList.getSelectedValue();
				String date = (String) dateList.getSelectedValue();
				int year,month,day;
				day = Integer.parseInt(date.substring(0, 2));
				month = Integer.parseInt(date.substring(3,5));
				month -= 1;
				year = Integer.parseInt(date.substring(6));
				showtimes = person.queryMovie(movieName, cineplexName, new GregorianCalendar(year,month,day));
				DefaultListModel<String> list = new DefaultListModel<String>();
				StringBuilder sb;
				for (ShowTime showTime : showtimes) {
					sb = new StringBuilder();
					sb.append(Main.calendarToSting(showTime.getDateTime()));
					sb.append("   ");
					sb.append(showTime.getCineplex().getName());
					sb.append("   ");
					sb.append(showTime.getNoOfCinema());
					sb.append("   ");
					sb.append(showTime.getMovie().getName());
					list.addElement(sb.toString());
				}
				showtimeList = new JList<String>(list);
				dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				dateList.setLayoutOrientation(JList.VERTICAL);
				dateList.setVisibleRowCount(10);
				rightPanel.add(LABEL_SHOWTIME);
				rightPanel.add(showtimeList);
				rightPanel.revalidate();
				
//				
//				mainPanel.add(leftPanel);
//				mainPanel.add(rightPanel);
//				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
//				this.add(mainPanel);
			} catch (NumberFormatException e1) {
				rightPanel.removeAll();
				JLabel errorLabel = new JLabel("No Show Time Found");
				rightPanel.add(errorLabel);
				rightPanel.revalidate();
				e1.printStackTrace();
			} catch (MovieListingException e1) {
				rightPanel.removeAll();
				JLabel errorLabel = new JLabel("No Show Time Found");
				rightPanel.add(errorLabel);
				rightPanel.revalidate();
				e1.printStackTrace();
			} catch (ShowTimeException e1) {
				rightPanel.removeAll();
				JLabel errorLabel = new JLabel("No Show Time Found");
				rightPanel.add(errorLabel);
				rightPanel.revalidate();
				e1.printStackTrace();
			}
		}
	}

}
