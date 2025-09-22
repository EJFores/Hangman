import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class HangmanGame extends JFrame {
    private JLabel nuce;

    private JLabel msgs;

    private JLabel guessed;

    private JLabel word;

    private JTextField letterbox;

    private JButton guess;

    private JButton guessword;

    private JButton create;

    private String[] wordlist;

    String mtyword = "";

    String soonword = "";

    int fails = 1;

    public HangmanGame() {
        Actions listener = new Actions();
        this.guess = new JButton("Guess!");
        this.guessword = new JButton("Guess the Word");
        this.create = new JButton(" Click me to create new word ");
        this.guess.addActionListener(listener);
        this.guessword.addActionListener(listener);
        this.create.addActionListener(listener);
        this.guess.setActionCommand("g");
        this.guessword.setActionCommand("gw");
        this.create.setActionCommand("c");
        this.msgs = new JLabel("<html> Please guess a letter <br> to begin game. <html>", 0);
        this.word = new JLabel(this.soonword, 0);
        this.word.setFont(new Font("TAHOMA", 1, 25));
        this.guessed = new JLabel("GUESSED LETTERS:", 0);
        this.nuce = new JLabel(new ImageIcon(getClass().getResource("hang1.png")));
        this.letterbox = new JTextField(10);
        this.wordlist = new String[104];
        try {
            InputStream is = getClass().getResourceAsStream("dictinary.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            int k = 0;
            String line;
            while ((line = br.readLine()) != null) {
                this.wordlist[k] = line;
                k++;
            }
            br.close();
            isr.close();
            is.close();
            int rand = (int)(Math.random() * 29.0D);
            this.mtyword = this.wordlist[rand];
            this.soonword = "";
            for (int i = 0; i < this.mtyword.length(); i++)
                this.soonword = String.valueOf(this.soonword) + "-";
            this.word.setText(this.soonword);
        } catch (IOException e) {
            System.out.println(e);
        }
        Container pane = getContentPane();
        pane.setLayout((LayoutManager)null);
        pane.setBackground(Color.gray);
        this.nuce.setBounds(200, 100, 150, 200);
        this.msgs.setBounds(50, 100, 150, 100);
        this.letterbox.setBounds(50, 200, 150, 90);
        this.guess.setBounds(350, 110, 150, 90);
        this.guessword.setBounds(350, 200, 150, 90);
        this.word.setBounds(150, 300, 200, 200);
        this.guessed.setBounds(150, 350, 200, 200);
        this.create.setBounds(170, 20, 220, 80);
        pane.add(this.nuce);
        pane.add(this.msgs);
        pane.add(this.guessed);
        pane.add(this.letterbox);
        pane.add(this.word);
        pane.add(this.guess);
        pane.add(this.guessword);
        pane.add(this.create);
        setSize(600, 600);
        setTitle(" Hangman (JFrame- courtesy of JavaLabs LLLC.) ");
        setVisible(true);
        setDefaultCloseOperation(3);
    }

    private class Actions implements ActionListener {
        private Actions() {}

        public void actionPerformed(ActionEvent a) {
            String temp = "";
            int index = 0;
            String event = a.getActionCommand();
            if (event.equals("c")) {
                HangmanGame.this.guess.setVisible(true);
                HangmanGame.this.guessword.setVisible(true);
                HangmanGame.this.msgs.setText("<html> Please guess a letter <br> to begin game. <html>");
                HangmanGame.this.nuce.setIcon(new ImageIcon(getClass().getResource("hang1.png")));
                HangmanGame.this.fails = 1;
                int rand = (int)(Math.random() * 29.0D);
                HangmanGame.this.mtyword = HangmanGame.this.wordlist[rand];
                HangmanGame.this.soonword = "";
                for (int i = 0; i < HangmanGame.this.mtyword.length(); i++)
                    HangmanGame.this.soonword = String.valueOf(HangmanGame.this.soonword) + "-";
                HangmanGame.this.guessed.setText("GUESSED LETTERS:");
                HangmanGame.this.word.setText(HangmanGame.this.soonword);
            }
            if (event.equals("gw")) {
                String userword = JOptionPane.showInputDialog(null, " Please enter the word \n If there is no guess just type nvm.", "Hangman", -1);
                if (userword.equals(null))
                    System.out.println("");
                if (userword.equals(HangmanGame.this.mtyword)) {
                    HangmanGame.this.msgs.setText("<html> YOU WON : D <br> PLEASE CLICK <br> NEW WORD BUTTON <br> TO START AGAIN <html>");
                    HangmanGame.this.guess.setVisible(false);
                    HangmanGame.this.guessword.setVisible(false);
                } else if (!userword.equals(HangmanGame.this.mtyword)) {
                    HangmanGame.this.fails++;
                    HangmanGame.this.nuce.setIcon(new ImageIcon(getClass().getResource("hang" + HangmanGame.this.fails + ".png")));
                    if (HangmanGame.this.fails >= 7) {
                        HangmanGame.this.msgs.setText("<html> YOU LOST! PLEASE <br> PLEASE CLICK <br> NEW WORD BUTTON<br> TO START AGAIN <html>");
                        HangmanGame.this.guess.setVisible(false);
                        HangmanGame.this.guessword.setVisible(false);
                        HangmanGame.this.word.setText(HangmanGame.this.mtyword);
                    }
                }
            }
            if (event.equals("g")) {
                String letter = HangmanGame.this.letterbox.getText();
                HangmanGame.this.guessed.setText(String.valueOf(HangmanGame.this.guessed.getText()) + " " + letter);
                index = HangmanGame.this.mtyword.indexOf(letter, index);
                HangmanGame.this.letterbox.setText("");
                if (index == -1) {
                    HangmanGame.this.fails++;
                    HangmanGame.this.nuce.setIcon(new ImageIcon(getClass().getResource("hang" + HangmanGame.this.fails + ".png")));
                }
                if (HangmanGame.this.fails >= 7) {
                    HangmanGame.this.msgs.setText("<html> YOU LOST! PLEASE <br> PLEASE CLICK <br> NEW WORD BUTTON<br> TO START AGAIN <html>");
                    HangmanGame.this.word.setText(HangmanGame.this.mtyword);
                    HangmanGame.this.guess.setVisible(false);
                    HangmanGame.this.guessword.setVisible(false);
                }
                while (index >= 0) {
                    temp = HangmanGame.this.soonword.substring(0, index);
                    temp = String.valueOf(temp) + letter;
                    temp = String.valueOf(temp) + HangmanGame.this.soonword.substring(index + 1);
                    HangmanGame.this.soonword = temp;
                    index = HangmanGame.this.mtyword.indexOf(letter, index + 1);
                }
                if (HangmanGame.this.soonword.equals(HangmanGame.this.mtyword)) {
                    HangmanGame.this.msgs.setText("<html> YOU WON : D <br> PLEASE CLICK <br> NEW WORD BUTTON <br> TO START AGAIN <html>");
                    HangmanGame.this.guess.setVisible(false);
                    HangmanGame.this.guessword.setVisible(false);
                }
                if (HangmanGame.this.fails < 7)
                    HangmanGame.this.word.setText(HangmanGame.this.soonword);
            }
        }
    }
}
