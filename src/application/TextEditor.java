package application;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Formatter;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class TextEditor {

	private JFrame frame;
	private JTextArea textArea;
	private File openFile = null;
	private final String Title = "TextEditor";
	Boolean bool1 = false;
	Boolean bool2 = false;
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					TextEditor window = new TextEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TextEditor() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("TextEditor");
		frame.setBounds(100, 100, 800, 710);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);



		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);

		JMenu FileMenu = new JMenu("File");
		FileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(FileMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Open");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("New");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newFile();
			}
		});
		FileMenu.add(mntmNewMenuItem_4);

		JSeparator separator_1 = new JSeparator();
		FileMenu.add(separator_1);
		FileMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save As");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});

		JSeparator separator = new JSeparator();
		FileMenu.add(separator);
		FileMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				save();
			}
		});
		FileMenu.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Exit");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});

		FileMenu.add(mntmNewMenuItem_5);

		JSeparator separator_2 = new JSeparator();
		FileMenu.add(separator_2);
		FileMenu.add(mntmNewMenuItem_3);

		JMenu mnNewMenu = new JMenu("Edit");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Cut");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Copy");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_7);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Paste");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_8);

		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);


	}
	private void open() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select a Text File to Open");
			chooser.showOpenDialog(null);

			openFile = chooser.getSelectedFile();
			if(openFile==null) {
				return;
			}
			if(!openFile.exists()) {
				JOptionPane.showMessageDialog(null, "File Does Not Exist");
				openFile = null;
				return;
			}

			FileInputStream f = new FileInputStream(openFile);
			BufferedInputStream b = new BufferedInputStream(f);

			String string = "";
			int i;
			while((i=b.read())!=-1) {
				string = string + (char)i;
			}
			textArea.setText(string);
			b.close();
			f.close();
			frame.setTitle(openFile.getName()+" - "+Title);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void saveAs() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Save As");
			chooser.showSaveDialog(null);
			openFile = chooser.getSelectedFile();
			if(openFile==null) {
				return;
			}
			bool1 = true;
			save();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void save() {
		String contents = textArea.getText();
		try {

			if(bool1==true) {
				Formatter form = new Formatter(openFile);
				form.format("%s", contents);
				form.close();
				frame.setTitle(Title+" - "+openFile.getName());
				bool1 = false;
				if(bool2) {
					bool2=false;
					frame.setVisible(false);
				}
			}

			else if(bool1==false) {

				if(contents.length()!=0) {
					Formatter form = new Formatter(openFile);
					form.format("%s", contents);
					form.close();
					frame.setTitle(Title+" - "+openFile.getName());
				}

				else if(contents.length()==0) {
					saveAs();
				}
			}

			if(openFile==null) {
				return;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void exit() {

		if(textArea.getText().length()==0) {
			frame.setVisible(false);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}
		else {

			try {
				int input = JOptionPane.showConfirmDialog(null, "Do You Want to Save this File ", "Wait!", JOptionPane.YES_NO_OPTION);
				if(input==0) {
					bool2 = true;
					saveAs();
				}
				else {
					frame.setVisible(false);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void newFile() {
		try {
			textArea.setText("");
			openFile=null;
			frame.setTitle(Title);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void cut() {
		String cutString = textArea.getSelectedText();
		StringSelection cutSelection = new StringSelection(cutString);
		clipboard.setContents(cutSelection, cutSelection);
		textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
	}

	private void copy() {
		String copyText = textArea.getSelectedText();
		StringSelection copySelection = new StringSelection(copyText);
		clipboard.setContents(copySelection, copySelection);
	}

	private void paste() {
		try {
			Transferable pasteText = clipboard.getContents(TextEditor.this);
			String sel = (String) pasteText.getTransferData(DataFlavor.stringFlavor);
			textArea.replaceRange(sel, textArea.getSelectionStart(), textArea.getSelectionEnd());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
