package chattingapp;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Client extends JFrame implements ActionListener{
    JTextField text;
    static JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client(){
        f.setLayout(null);
        
        JPanel p1= new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0,0,450,70);
        f.add(p1);
        p1.setLayout(null);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2= i1.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT );
        ImageIcon i3= new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter(){
        public void mouseClicked( MouseEvent ae){
                System.exit(0);
                }
        }
    );
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/Sanvi.png"));
        Image i5= i4.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT );
        ImageIcon i6= new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(30, 10, 50, 50);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/Video.png"));
        Image i8= i7.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT );
        ImageIcon i9= new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(320, 20, 25, 25);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11= i10.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT );
        ImageIcon i12= new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(370, 20, 25, 25);
        p1.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14= i13.getImage().getScaledInstance(35, 45,Image.SCALE_DEFAULT );
        ImageIcon i15= new ImageIcon(i14);
        JLabel dots = new JLabel(i15);
        dots.setBounds(400, 10, 35, 45);
        p1.add(dots);
        
        JLabel name = new JLabel("Sanvi");
        name.setBounds(83, 5, 200, 50);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Poppins", Font.BOLD, 18));
        p1.add(name);
        
        JLabel status = new JLabel("Active now");
        status.setBounds(83, 25, 200, 50);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Poppins",Font.PLAIN, 10));
        p1.add(status);
        
        p2= new JPanel();
        p2.setBounds(0,70,450,575);
        f.add(p2);
        
        text= new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("Poppins", Font.PLAIN, 14));
        f.add(text);
        
        JButton send= new JButton("Send");
        send.addActionListener(this);
        send.setForeground(Color.WHITE);
        send.setBackground(new Color(7, 94, 84));
        send.setBounds(320, 655, 123, 40);
        f.add(send);
      
   
        f.setSize(450, 700);
        f.setUndecorated(true);
        f.setLocation(800, 50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
         @Override
    public void actionPerformed(ActionEvent ae) {
        
        try {
            String out=text.getText();
            JPanel p3 = formatLabel(out);
            p2.setLayout(new BorderLayout());
            JPanel right= new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END );
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            p2.add(vertical, BorderLayout.PAGE_START);
            text.setText("");
            
            dout.writeUTF(out);
            
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        panel.add(output);
        output.setFont(new Font("Poppins", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
              
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        return panel;
  
    }
    public static void main(String [] args){
          
        new Client();
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true) {
                p2.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                p2.add(vertical, BorderLayout.PAGE_START);
                
                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


    

   