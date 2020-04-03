package com.ji.main;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NoSleepMain {

	public static void main(String[] args) throws AWTException, InterruptedException {
		
		//창 초기화
		JFrame frame = new JFrame("NOSLEEP_V.0.1");
		JLabel jLabel = new JLabel();
		jLabel.setText("시작 버튼을 클릭하시면 마우스가 자동으로 움직입니다.");
		jLabel.setSize(100,100);
		JPanel pn = new JPanel();

		JButton startBtn = new JButton("시작");
		startBtn.setPreferredSize(new Dimension(100, 100));

		final JButton finishBtn = new JButton("종료");
		finishBtn.setPreferredSize(new Dimension(100, 100));

		//버튼 이벤트 정의
		startButtonActionListen(startBtn, finishBtn);
		finishButtonActionListen(finishBtn);

		pn.add(startBtn);
		pn.add(finishBtn);
		pn.add(jLabel);

		frame.setContentPane(pn);
		frame.setSize(500, 200);
		frame.setVisible(true);

	}

	/**
	 * 종료 버튼 클릭시 프로그램 종료
	 * @param finishBtn
	 */
	private static void finishButtonActionListen(final JButton finishBtn) {
		finishBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
	}

	/**
	 * 시작 버튼 클릭 시 쓰레드 생성을 통해서 마우스 자동 이동
	 * @param startBtn
	 * @param finishBtn
	 */
	private static void startButtonActionListen(JButton startBtn, final JButton finishBtn) {
		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				finishBtn.requestFocus();
				Thread worker = autoMouseMoverWorker(finishBtn);
				worker.start();
			}

			private Thread autoMouseMoverWorker(final JButton btn) {
				Thread worker = new Thread() {

					public void run() {
						Robot robot;
						try {
							robot = new Robot();

							int index = 0;
							int time = 0;
							while (true) {
								robot.mouseMove(500, index);
								time++;
								index += 50;

								btn.setText("종료(" + time + "초)");
								robot.delay(1000);

							}

						} catch (AWTException e) {
							e.printStackTrace();
						}
					}

				};
				return worker;
			}

		});
	}

}
