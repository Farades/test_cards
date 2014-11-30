import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CardPlayer {
    private JFrame frame; 
    private JTextArea display;
    private JButton nextButton;
    private ArrayList<Card> cardList;
    private int currentCardIndex;
    private Card currentCard;
    private boolean isShowAnswer;

    public static void main(String[] args) {
        CardPlayer player = new CardPlayer();
        player.go();
    }

    public void go() {
        frame = new JFrame("Card Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        display = new JTextArea(10, 20);
        display.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane(display);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        nextButton = new JButton("Show question");
        mainPanel.add(qScroller);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load card list");
        loadMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        frame.setSize(640, 500);
        frame.setVisible(true);
    }

    public class OpenMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }

    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            if (isShowAnswer) {
                display.setText(currentCard.getAnswer());
                nextButton.setText("Next card");
                isShowAnswer = false;
            } else {
                if (currentCardIndex < cardList.size()) {
                    showNextCard();
                } else {
                    display.setText("Больше вопросов нет!");
                    nextButton.setEnabled(false);
                }
            }
        }
    }

    private void loadFile(File file) {
        cardList = new ArrayList<Card>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ( (line = reader.readLine()) != null ) {
                makeCard(line);
            }
        } catch(Exception ex) {
            System.out.println("Couldn't open file");
        }
        System.out.println("Open Card list from file (" + file.getAbsolutePath() + ") - OK.");
        showNextCard();
    }

    private void makeCard(String line) {
        String[] result = line.split("/");
        Card card = new Card(result[0], result[1]);
        cardList.add(card);
        System.out.println("---Made card---");
        System.out.println(card.toString());
    }

    private void showNextCard() {
        currentCard = cardList.get(currentCardIndex++);
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }
}