import java.io.*;

public class Card implements Serializable {
	private String question;
	private String answer;
	
	public Card(String _question, String _answer) {
		question = _question;
		answer = _answer;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String toString() {
		return "Вопрос: " + question + "\nОтвет: " + answer;
	}
}