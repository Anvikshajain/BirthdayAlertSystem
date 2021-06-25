import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.toedter.calendar.JDateChooser;

import jaco.mp3.player.MP3Player;
import net.proteanit.sql.DbUtils;

import java.awt.event.*;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.*;

class alert_code{

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public Statement st = null;
    String date;
    public static final String path = "res\\hbd.mp3";
    MP3Player mp3 = new MP3Player(new File(path));
    int flag = 1, flag2 = 0;

    JFrame frame;
    JLabel bdayLabel,name_label,date_label,today_label;
    JButton add,delete,clear,stop,refresh;
    JTextField name_Field,today_bday;
    JTable bday_table;
    JScrollPane jsp;
    JDateChooser dcDate;

    Font font=new Font("Liberatian Serif",30,40);
    Font f=new Font("Liberatian Serif",30,15);
    Font fheading=new Font("Liberatian Serif",30,15);
    public alert_code(){
        initComponents();
        showTableData();
        alert();
    }

    public void initComponents(){
        frame=new JFrame("Birthday Alert System");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JLabel(new ImageIcon("res\\bdayphoto.jpg")));

        bdayLabel=new JLabel();
        bdayLabel.setText("Birthday Alert System");
        bdayLabel.setFont(font);
        bdayLabel.setBounds(200, 50, 500, 50);
        
        name_label=new JLabel();
        name_label.setText("Name       :");
        name_label.setFont(fheading);
        name_label.setBounds(20, 160, 80, 30);

        name_Field=new JTextField();
        name_Field.setFont(f);
        name_Field.setBounds(100, 160, 250, 30);

        date_label=new JLabel();
        date_label.setText("Birth Date :");
        date_label.setFont(fheading);
        date_label.setBounds(20, 260, 80, 30);

        dcDate=new JDateChooser();
        dcDate.setFont(f);
        dcDate.setDateFormatString("yyyy-MM-dd");
        dcDate.setBounds(100, 260, 250, 30);

        add=new JButton();
        add.setText("Add");
        add.setFont(fheading);
        add.setBounds(100, 360, 100, 30);
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{

                    String sql = "INSERT INTO testBirth"+"(Name,Bdate)"+"VALUES (?,?)";
        
                    SimpleDateFormat f = new SimpleDateFormat("yyyy");
                    SimpleDateFormat f1 = new SimpleDateFormat("MM");
                    SimpleDateFormat f2 = new SimpleDateFormat("dd");
                    
                    date = f.format(dcDate.getDate())+"-"+f1.format(dcDate.getDate())+"-"+f2.format(dcDate.getDate());
        
                    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/birthday_management?autoReconnect=true&useSSL=false","//your username//","//your password//");
                    pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setString(1,name_Field.getText());
                    pst.setString(2, date);
                    pst.executeUpdate();
                    
                    JOptionPane.showMessageDialog(frame, "Insert Successfull");
                    showTableData();
        
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Exception "+ex);
                }
            }
        });

        delete=new JButton();
        delete.setText("Delete");
        delete.setFont(fheading);
        delete.setBounds(220, 360, 100, 30);
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
            
                    String sql = "DELETE FROM testbirth WHERE Name=?";
                    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/birthday_management?autoReconnect=true&useSSL=false","//your username//","//your password//");
                    pst = (PreparedStatement) con.prepareStatement(sql);
                    
                    pst.setString(1,name_Field.getText());
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog( frame, "Delete Successfull");
                    showTableData();
                } catch (SQLException ex) {
        
                    JOptionPane.showMessageDialog( frame, "!!!!!!!!!! Unsuccessfull Delete");
                }
            }
        });

        clear=new JButton();
        clear.setText("Clear");
        clear.setFont(fheading);
        clear.setBounds(100, 400, 100, 30);
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                name_Field.setText("");
                dcDate.setCalendar(null);
                showTableData();
            }
        });

        stop=new JButton();
        stop.setText("Stop");
        stop.setFont(fheading);
        stop.setBounds(220, 400, 100, 30);
        stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                flag=0;
                mp3.stop();
            }
        });

        refresh=new JButton();
        refresh.setText("Refresh");
        refresh.setFont(fheading);
        refresh.setBounds(650, 600, 100, 30);
        refresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                flag=1;
                alert();
                showTableData();
            }
        }); 

        today_label=new JLabel();
        today_label.setText("Today is birthday of");
        today_label.setFont(fheading);
        today_label.setBounds(40, 460, 200, 30);

        today_bday=new JTextField();
        today_bday.setFont(fheading);
        today_bday.setBounds(40, 500, 280, 30);

        jsp=new JScrollPane();
        JLabel personList=new JLabel();
        personList.setText("Person's Birthday List");
        personList.setFont(new FontUIResource("Liberatian Serif", Font.BOLD, 15));
        personList.setBounds(400, 130, 200, 30);

        bday_table=new JTable();
        bday_table.setFont(fheading);
        
        bday_table.setBounds(400, 160, 350, 400);
        bday_table.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bday_table.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                int numberOfRow = bday_table.getSelectedRow();
        
                TableModel model = bday_table.getModel();
                
                String n = model.getValueAt(numberOfRow,0).toString();
                String bg = model.getValueAt(numberOfRow,1).toString();
                
                name_Field.setText(n);
                Date date;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(""+bg);
                    dcDate.setDate(date);
                } catch (ParseException ex) {
                    
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }});
        jsp.setViewportView(bday_table);

        frame.add(bdayLabel);
        frame.add(name_label);
        frame.add(name_Field);
        frame.add(date_label);
        frame.add(personList);
        frame.add(dcDate);
        frame.add(add);
        frame.add(delete);
        frame.add(jsp);
        frame.add(new JScrollPane(bday_table));
        frame.add(clear);
        frame.add(stop);
        frame.add(refresh);
        frame.add(today_label);
        frame.add(today_bday);
        frame.add(bday_table);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    public void showTableData() {
        try{
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/birthday_management?autoReconnect=true&useSSL=false","//your username//","//your password//");
        
            String sql = "SELECT * FROM testbirth"; 
            pst = (PreparedStatement) con.prepareStatement(sql);
            rs = pst.executeQuery();
            bday_table.setModel(DbUtils.resultSetToTableModel(rs));
            bday_table.setRowHeight(30);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"!!!!! Error to show");
        }
    }

    public void alert(){
        
        long millis=System.currentTimeMillis();  
        java.sql.Date d = new java.sql.Date(millis);  
        String date = d.toString().substring(5);
            
        ArrayList<String> ss = new ArrayList<String>();
    
    for (int i = 0; i < bday_table.getRowCount(); i++) {
            if (date.equals(bday_table.getValueAt(i, 1).toString().substring(5))) {
                ss.add(bday_table.getValueAt(i, 0).toString());
            }        
    }
    
    today_bday.setText(""+ss);
    
    Thread t;t = new Thread(){
        public void run() {
            
            for (;;) {
                
                int ct=0;
                long millis=System.currentTimeMillis();
                java.sql.Date d=new java.sql.Date(millis);
                
                String date = d.toString().substring(5);
                
                for (int i = 0; i < bday_table.getRowCount(); i++) {
                    if (date.equals(bday_table.getValueAt(i, 1).toString().substring(5))&&flag==1) {                   
                        mp3.play();    
                    }
                    
                    else  
                        ct++;
                }
                if (ct==0) {
                    JOptionPane.showMessageDialog(null, "No one");
                }
                try {
                    sleep(16000);
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    };
    t.start();
}
}
