package chatApplication;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.InetAddress;
import java.net.Socket;


import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Client {

	private JFrame frame;
	private static JTextField txtMessage = new JTextField();;
	static JTextArea txtChat = new JTextArea();
	static boolean sendMessage = false;
	
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		
		InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        DataOutputStream oos = null;
        DataInputStream ois = null;
        
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		socket = new Socket(host.getHostName(), 9876);
		oos = new DataOutputStream(socket.getOutputStream());
		ois = new DataInputStream(socket.getInputStream());
		
	
		while(true) {
			
				
			if(ois.available()!=0) {
				String message = ois.readUTF();
				txtChat.setText(txtChat.getText()+"\n"+"Server: "+message);
			}
			
			if(sendMessage) {
				txtChat.setText(txtChat.getText()+"\n"+"Client: "+txtMessage.getText());
				oos.writeUTF(txtMessage.getText());
				oos.flush();
				
				
				sendMessage = false;
			}

			Thread.sleep(100);
		}
	}

	
	public Client() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Client");
		txtChat.setEditable(false);
		txtChat.setBounds(10, 11, 414, 190);
		frame.getContentPane().add(txtChat);
		
		
		txtMessage.setBounds(10, 212, 306, 20);
		frame.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("New button");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(sendMessage);
				sendMessage = true;
				System.out.println("sendMessage true");
			}
		});
		btnSend.setBounds(335, 212, 89, 23);
		frame.getContentPane().add(btnSend);
	}
}
