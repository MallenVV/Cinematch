package se.liu.axebj824.wilma870.towerdefese;

import javax.swing.*;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * A class for visualizing the board with Jframe
 *
 * @param component the component that contains all information for painting the board
 */
public class BoardViewer
{
    private TDComponent component;

    public void show(Board board) {
	JFrame frame = new JFrame();
	TDAction mouseAction = new TDAction(frame, board);
	component = new TDComponent(board, frame);

	final JMenuBar menuBar = new JMenuBar();
	final JMenu optionsMenu = new JMenu("Options");
	final JMenuItem exitItem = new JMenuItem("Exit");
	final JToggleButton autoStartButton = new JToggleButton("Auto waves");
	final JButton startButton = new JButton("Start");
	final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	exitItem.addActionListener(new ClickExit());

	autoStartButton.setSelected(false);
	autoStartButton.addItemListener(autoStartWavesListener);

	startButton.addActionListener(startWaveListener);

	buttonPanel.add(autoStartButton);
	buttonPanel.add(startButton);
	optionsMenu.add(exitItem);

	menuBar.setLayout(new BorderLayout());
	menuBar.add(buttonPanel, BorderLayout.WEST);
	menuBar.add(optionsMenu, BorderLayout.EAST);
	frame.setJMenuBar(menuBar);

	frame.setLayout(new BorderLayout());
	frame.add(component, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
    }

    public void repaint(Board board) {
	component.repaint();
    }

    public class ClickExit implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    Object[] options = { "Yes", "No" };
	    int optionChosen =
		    JOptionPane.showOptionDialog(component.getFrame(), "Are you sure?", "Confirm Exit", JOptionPane.YES_NO_OPTION,
						 JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    if (optionChosen == JOptionPane.YES_OPTION) {
		System.out.println(optionChosen);
		System.exit(0);
	    }
	}
    }

    public ItemListener autoStartWavesListener = new ItemListener()
    {
	public void itemStateChanged(ItemEvent e) {
	    GlobalVariable.autoStartWaves = !GlobalVariable.autoStartWaves;
	    System.out.println("autostart:" + GlobalVariable.autoStartWaves);
	}
    };

    public ActionListener startWaveListener = new ActionListener()
    {
	@Override public void actionPerformed(ActionEvent e) {
	    GlobalVariable.startWave = true;
	}
    };
}
