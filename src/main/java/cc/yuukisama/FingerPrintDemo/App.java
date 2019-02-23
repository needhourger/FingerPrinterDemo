package cc.yuukisama.FingerPrintDemo;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
	private static JLabel targetPic=null;
	private static JLabel databasePic=null;
	
	private static JButton targetButton=null;
	private static JButton databaseButton=null;
	
	private static JLabel targetJLabel=null;
	private static JLabel databaseJLabel=null;
	
	private static JFileChooser targetChooser=null;
	private static JFileChooser databaseChooser=null;
	
	private static File targetFile=null;
	private static File databaseFile=null;
	
	public App() {
		super();
		setTitle("Finger Printer demo");
		setSize(600, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		
		targetButton=new JButton("目标指纹");
		targetButton.setBounds(100, 10, 100, 25);
		targetButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				targetChooser=new JFileChooser(".\\");
				targetChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				targetChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
				targetChooser.setMultiSelectionEnabled(false);
				int ret=targetChooser.showOpenDialog(targetButton);
				if (ret==JFileChooser.APPROVE_OPTION) {
					targetFile=targetChooser.getSelectedFile();
				}
				targetJLabel.setText("target: "+targetFile.getPath());
				
				ImageIcon image=new ImageIcon(targetFile.getPath());
				image.setImage(image.getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT));
				targetPic=new JLabel(image);
				targetPic.setBounds(15, 80, 250, 300);
				getContentPane().add(targetPic);
				repaint();
				
				UserDetails retDetails=null;
				if (databaseFile!=null) {
					try {
						retDetails=Matcher.search(targetFile.getAbsolutePath());
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择指纹库目录","WARNING",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (retDetails!=null) {
					image=new ImageIcon(retDetails.path);
					image.setImage(image.getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT));
					databasePic=new JLabel(image);
					databasePic.setBounds(300, 80, 250, 300);
					getContentPane().add(databasePic);
					repaint();
					
					JOptionPane.showMessageDialog(null, retDetails.name+" Welcome","INFO",JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null,"非法用户","INFO",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		getContentPane().add(targetButton);
		
		databaseButton=new JButton("数据库指纹");
		databaseButton.setBounds(400, 10, 100, 25);
		databaseButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				databaseChooser=new JFileChooser(".\\");
				databaseChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				databaseChooser.setMultiSelectionEnabled(false);
				int ret=databaseChooser.showOpenDialog(databaseButton);
				if (ret==JFileChooser.APPROVE_OPTION) {
					databaseFile=databaseChooser.getSelectedFile();
					databaseJLabel.setText("database: loading...");
					Matcher.load(databaseFile.getAbsolutePath());
					databaseJLabel.setText("database: "+databaseFile.getAbsolutePath());
				}
			}
		});
		getContentPane().add(databaseButton);
		
		targetJLabel=new JLabel("target: ");
		targetJLabel.setBounds(10, 45, 280, 25);
		getContentPane().add(targetJLabel);
		
		databaseJLabel=new JLabel("database:");
		databaseJLabel.setBounds(300, 45, 280, 25);
		getContentPane().add(databaseJLabel);
		
		
		setVisible(true);
		
	}
	
	
    public static void main( String[] args )
    {
    	
    	App window=new App();
    }
    
}