package se.liu.axebj824.wilma870.towerdefese;


import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TDComponent extends JComponent
{
    private final static int SQUARE_SIZE = GlobalVariable.SQUARE_SIZE;
    private Board board;
    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();
    private JFrame frame;
    private int startHp;

    public TDComponent(final Board board, JFrame frame) {
	this.board = board;
	this.frame = frame;
	this.startHp = board.getBase().getBaseHP();
    }

    private static EnumMap<SquareType, Color> createColorMap() {
	EnumMap<SquareType, Color> squarColors = new EnumMap<>(SquareType.class);
	squarColors.put(SquareType.GRASS, Color.DARK_GRAY);
	squarColors.put(SquareType.ROAD, Color.GRAY);
	squarColors.put(SquareType.BASE, Color.YELLOW);
	squarColors.put(SquareType.TRAIL, Color.GRAY);
	squarColors.put(SquareType.PLATFORM, Color.LIGHT_GRAY);
	return squarColors;
    }

    public void boardChanged() {
	repaint();
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(SQUARE_SIZE * (board.getWidth()), SQUARE_SIZE * (board.getHeight()));
    }

    @Override public void paintComponent(final Graphics g) {
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		if (board.isEntityPos(x, y)) {
		    g.setColor(board.getEntity(x, y).color);
		} else {
		    g.setColor(SQUARE_COLORS.get(board.getVisibleSquareAt(x, y)));
		}
		g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	    }
	}
	g.setColor(Color.GREEN);
	g.setFont(new Font("Arial", Font.BOLD, 30));
	double moneyValue = Math.floor(board.getMoney() * 10) / 10.0;
	String money = String.format("$%.1f%n",moneyValue);
	g.drawString(money,
		     GlobalVariable.MAP_WIDTH * GlobalVariable.SQUARE_SIZE-GlobalVariable.SQUARE_SIZE*30,
		     GlobalVariable.SQUARE_SIZE*20
		     );



	g.setColor(Color.RED);
	g.setFont(new Font("Arial", Font.BOLD, 28));
	int baseHpValue = board.getBase().getBaseHP();
	String baseHp = String.format("HP: %d/%d",baseHpValue, startHp);
	g.drawString(baseHp,
		     GlobalVariable.MAP_WIDTH * GlobalVariable.SQUARE_SIZE-GlobalVariable.SQUARE_SIZE*40,
		     GlobalVariable.MAP_HEIGHT * GlobalVariable.SQUARE_SIZE-GlobalVariable.SQUARE_SIZE*20
	);
    }

    public Component getFrame() {
    return frame;
    }
}