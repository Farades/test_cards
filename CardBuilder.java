import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CardBuilder {
	private JFrame frame;
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<Card> cardList;
	
	public static void main(String[] args) {
		CardBuilder builder = new CardBuilder();
		builder.go();
	}
	
	public void go() {
		frame = new JFrame("KMZ - Card Builder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		
		question = new JTextArea(6, 20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer = new JTextArea(6, 20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);	

		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);	

		JButton nextButton = new JButton("��������� ��������");
		
		cardList = new ArrayList<Card>();
		
		JLabel qLabel = new JLabel("������:");
		JLabel aLabel = new JLabel("�����:");
		
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("����");
		JMenuItem newMenuItem = new JMenuItem("����� ����");
		JMenuItem saveMenuItem = new JMenuItem("���������");
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500, 600);
		frame.setVisible(true);
	}
	
	public class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Card card = new Card(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
			System.out.println("Card create - OK.");
			System.out.println(card.toString());
		}
	}
	
	public class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Card card = new Card(question.getText(), answer.getText());
			cardList.add(card);
			
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
			System.out.println("Save Card list to file - OK.");
		}
	}
	
	public class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			cardList.clear();
			clearCard();
			System.out.println("Card list clear - OK.");
		}
	}
	
	public void saveFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (Card card : cardList) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close();
		} catch(IOException ex) {
			System.out.println("Couldn't write cardList to File");
			ex.printStackTrace();
		}
	}
	
	public void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
}