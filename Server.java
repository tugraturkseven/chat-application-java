package chatApplication;

import java.awt.EventQueue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Server {

	private JFrame frmServer;
	private static JTextField txtMessage = new JTextField();;
	static JTextArea txtChat = new JTextArea();
	
	private static ServerSocket server;
	private static int port = 9876;
	
	static boolean sendMessage = false;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		server = new ServerSocket(port);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
					window.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Socket socket = server.accept();
		
		DataInputStream ois = new DataInputStream(socket.getInputStream());
		DataOutputStream oos = new DataOutputStream(socket.getOutputStream());


		while(true) {
			
			
			if(sendMessage) {
				txtChat.setText(txtChat.getText()+"\n"+"Server: "+txtMessage.getText());
				oos.writeUTF(txtMessage.getText());
				oos.flush();
				
				sendMessage = false;
			}
			
				if(ois.available()!=0) {
					String message = ois.readUTF();
					txtChat.setText(txtChat.getText()+"\n"+"Client: "+message);
				}
				

			Thread.sleep(100);
		}
		
	}


	public Server() {
		initialize();
	}


	private void initialize() {
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 450, 300);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		frmServer.setLocationRelativeTo(null);
		txtChat.setLineWrap(true);
		
		txtChat.setBounds(10, 11, 414, 211);
		frmServer.getContentPane().add(txtChat);
		txtChat.setEditable(false);
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
			}
		});
		
		txtMessage.setBounds(10, 233, 313, 20);
		frmServer.getContentPane().add(txtMessage);
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
		btnSend.setBounds(335, 233, 89, 23);
		frmServer.getContentPane().add(btnSend);
	
	}
}
