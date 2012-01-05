package com.test.codingdojo.katas;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.junit.Test;

public class GameOfLifeKataTest {

	@Test
	public void shouldSolveKataGameOfLife() throws Exception {
		final GameOfLifeKata kata = new GameOfLifeKata();
		assertEquals("        \n   **   \n   **   \n        ", kata.tic("        \n    *   \n   **   \n        "));
//		show(kata);
	}

	public void show(final GameOfLifeKata kata) throws InterruptedException {
		final JFrame frame = new JFrame("Game of Life");
		frame.setSize(675, 375);
		frame.setLayout(new BorderLayout());
		final JTextArea jTextArea = new JTextArea();
		frame.add(jTextArea);
		jTextArea.setFont(new java.awt.Font("Lucida Console", 0, 28));
		frame.setVisible(true);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String input = 
								"                                      \n" + 
								"                         *            \n" + 
								"                       * *            \n" + 
								"             **      **            ** \n" + 
								"            *   *    **            ** \n" + 
								" **        *     *   **               \n" + 
								" **        *   * **    * *            \n" + 
								"           *     *       *            \n" + 
								"            *   *                     \n" + 
								"             **                       \n" + 
								"                                      \n";
				while (frame.isVisible()) {
					try {
						print(input);
						input = kata.tic(input);
					} catch (Exception e) {
					}
				}
			}

			private void print(String text) throws InterruptedException {
				jTextArea.setText(text);
				Thread.sleep(500);
			}
		});
		thread.start();
		thread.join();
	}

}
