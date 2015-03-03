import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AccountGui extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JTextField balTf, idTf, amTf, dateTf, curbalTf;
	private Account account;
	private JRadioButton withdrawal, deposit;

    AccountGui(String title)
    {
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //UIManager.getSystemLookAndFeelClassName();
      try
      {
    	UIManager.getSystemLookAndFeelClassName();
        //UIManager.setLookAndFeel("javax.swing.plaf.metal");
        //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
      }
      catch (Exception e) {}
      

      
      JMenuBar mb = new JMenuBar();
      mb.setBackground(Color.blue);
      setJMenuBar(mb);
      
      mb.add(fileMenu());
      mb.add(new JMenu("Edit"));
      mb.add(new JMenu("Help"));
      
      add(createTitle(),BorderLayout.NORTH);
      add(newAccount(),BorderLayout.WEST);
      add(trans(),BorderLayout.EAST);
      
      JPanel report = new JPanel(new BorderLayout());
      report.setBorder(BorderFactory.createTitledBorder(" Transaction Report "));
      JPanel ep = new JPanel(new GridLayout(2,1));
      ep.add(new JLabel("Current Balance: ", JLabel.RIGHT));
      ep.add(new JLabel("Account Date Created: ", JLabel.RIGHT));
      report.add(ep, BorderLayout.WEST);
      
      JPanel cp = new JPanel(new GridLayout(2,1));
      cp.add((curbalTf=new JTextField(10)));
      cp.add((dateTf=new JTextField(10)));
      report.add(cp, BorderLayout.CENTER);
      
      JPanel wp = new JPanel(new GridLayout(2,1));
      final JTextArea reportText = new JTextArea(5, 20);

      JButton Report = new JButton("Report");
      Report.addActionListener(new ActionListener()
      {
    	  public void actionPerformed(ActionEvent arg0)
    	  {
    		  for (Transaction trans : account.getTransactionList())
    		  {
				reportText.append("Trans Type: " + trans.getType() + "\t");
				reportText.append("Trans Date: " + trans.getDate() + "\t");
				reportText.append("Trans Amount: " + trans.getAmount() + "\t");
				reportText.append("Trans Bal: " + trans.getBalance() + "\t");
				reportText.append("\n");
    		  }
    	  }
      });
      wp.add(Report);
      JButton close = new JButton("Close");
      close.addActionListener(new Close());
      wp.add(close);
      report.add(wp, BorderLayout.EAST);
      
      JScrollPane sp = new JScrollPane(reportText);
      report.add(sp, BorderLayout.SOUTH);
      
      add(report,BorderLayout.SOUTH);
      
      pack();
      setVisible(true);
    }
    
    private JMenu fileMenu()
    {
      JMenu fileMenu = new JMenu("File");
      JMenuItem item = new JMenuItem("Exit");
      fileMenu.add(item);
      item.addActionListener(new Close());
    	  
      item = new JMenuItem("Save");
      fileMenu.add(item);
      item.addActionListener(new SaveAccount());
      
      item = new JMenuItem("Open");
      fileMenu.add(item);
      item.addActionListener(new LoadAccount());
      
      return fileMenu;
    }
    
    private JPanel createTitle()
    {
      JPanel panel = new JPanel(new GridLayout(2,1));
      JLabel l1 = new JLabel("Page Bank of Fort Worth", JLabel.CENTER);
      l1.setFont(new Font("Serif", Font.BOLD, 14));
      l1.setForeground(Color.BLUE);
      
      JLabel l2 = new JLabel("(Member FDIC)", JLabel.CENTER);
      l2.setFont(new Font("Serif", Font.BOLD, 12));
      l2.setForeground(Color.BLUE);
      
      panel.add(l1);
      panel.add(l2);
      return panel;
    }
    
    private JPanel newAccount()
    {
        JPanel newAccount = new JPanel(new BorderLayout());
        newAccount.setBorder(BorderFactory.createTitledBorder(" New Account Information "));
        
        JPanel left = new JPanel(new GridLayout(3,1));
        left.add(new JLabel("Enter Id: ", JLabel.RIGHT));
        left.add(new JLabel("Enter Balance: ", JLabel.RIGHT));
        left.add(new JLabel("Select Type: ", JLabel.RIGHT));
        newAccount.add(left, BorderLayout.WEST);
        
        JPanel west = new JPanel(new GridLayout(3,1));
        west.add((idTf=new JTextField(10)));
        west.add((balTf=new JTextField(10)));
        west.add(new JComboBox(new String[] {"Checking", "Savings","Money Market","CD"}));
        newAccount.add(west, BorderLayout.EAST);
        
        JPanel s = new JPanel();
        JButton CreateButton = new JButton("Create");
        CreateButton.addActionListener(new CreateAccount());
        s.add(CreateButton);
        newAccount.add(s, BorderLayout.SOUTH);
        
        return newAccount;
    }
    private JPanel trans()
    {
        JPanel newAccount = new JPanel(new BorderLayout());
        newAccount.setBorder(BorderFactory.createTitledBorder(" Transactions "));
        
        JPanel left = new JPanel(new GridLayout(3,1));
        left.add(new JLabel("Amount: ", JLabel.RIGHT));
        left.add(new JLabel("Type: ", JLabel.RIGHT));
        left.add(new JLabel("      ", JLabel.RIGHT));
        newAccount.add(left, BorderLayout.WEST);
        
        JPanel west = new JPanel(new GridLayout(3,1));
        west.add((amTf=new JTextField(10)));
        deposit = new JRadioButton("Deposit: ");
        west.add(deposit);
        withdrawal = new JRadioButton("Withdrawal: ");
        west.add(withdrawal);
        //west.add(new JLabel(" ", JLabel.RIGHT));
        newAccount.add(west, BorderLayout.EAST);
        
        JPanel s = new JPanel();
        JButton Execute = new JButton("Execute");
        Execute.addActionListener(new ExecuteAction());
        s.add(Execute);
        newAccount.add(s, BorderLayout.SOUTH);
       
        
        return newAccount;
    }
    
//    class Report implements ActionListener
//    {
//    	public void actionPerformed(ActionEvent arg0)
//    	{
//    		for (Transaction trans : account.getTransactionList())
//    		{
//    			reportText.append("Trans Type: " + trans.getType() + "\t");
//    			reportText.append("Trans Date: " + trans.getDate() + "\t");
//    			reportText.append("Trans Amount: " + trans.getAmount() + "\t");
//    			reportText.append("Trans Bal: " + trans.getBalance() + "\t");
//    			reportText.append("\n");
//    		}
//    	}
//    }
    
    class Close implements ActionListener
    {
    	public void actionPerformed(ActionEvent arg0)
    	{
    		System.out.println("Closing.");
    		removeAll();
    		dispose();
    		System.exit(0);
    	}
    }
    
    class ExecuteAction implements ActionListener
    {
    	public void actionPerformed(ActionEvent arg0)
    	{
    		double amount = Double.parseDouble(amTf.getText());
    		
    		if(deposit.isSelected())
    		{
    			account.deposit(amount);
    		}
    		else
    		{
    			account.withdraw(amount);
    		}
    		
    		balTf.setText(Double.toString(account.getBalance()));
    		
    	}
    }

    class SaveAccount implements ActionListener
    {
		public void actionPerformed(ActionEvent arg0) 
		{
		  JFileChooser fc = new JFileChooser(".");
		  fc.showDialog(new JFrame(), "Save");
		  File file = fc.getSelectedFile();
		  System.out.println("Selected File: " + file.getName()) ;
		  try
		  {
		    FileOutputStream fis = new FileOutputStream(file);
		    ObjectOutputStream obj = new ObjectOutputStream(fis);
		    obj.writeObject(account);
		  }
		  catch (IOException e) {};
		}
    	  
    }
    
    class CreateAccount implements ActionListener
    {
    	public void actionPerformed(ActionEvent arg0)
    	{
    		account = new Account(idTf.getText(), Double.parseDouble(balTf.getText()));
    		
    		System.out.println("Account: "+ account);
    		
    		balTf.setText(Double.toString(account.getBalance()));
    		dateTf.setText(account.getDateCreated().toString());
    		curbalTf.setText(Double.toString(account.getBalance()));
    	}
    }
    
        
    class LoadAccount implements ActionListener
    {
    	public void actionPerformed(ActionEvent arg0)
    	{
    		JFileChooser fc = new JFileChooser(".");
    		fc.showDialog(new JFrame(), "Open");
    		File file = fc.getSelectedFile();
    		System.out.println("Selected File: " + file.getName());
    		System.out.println(fc.getApproveButtonText());
    		
    		if(fc.getApproveButtonText().equals("Open"))
    		{
    		
    			try
    			{
    				FileInputStream fis = new FileInputStream(file);
    				ObjectInputStream obj = new ObjectInputStream(fis);
    				try 
    				{
    					account = (Account) obj.readObject();
    				} 
    				catch (ClassNotFoundException e) 
    				{
    					e.printStackTrace();
    				}
    			}
    		
    			catch (IOException e) {System.out.println(e);}
    		}
    	}
    }
        
	public static void main(String[] args) 
	{
	  new AccountGui("Project - User Interface");
	}
	

}

