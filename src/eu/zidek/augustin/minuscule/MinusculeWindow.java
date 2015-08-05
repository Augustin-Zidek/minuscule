package eu.zidek.augustin.minuscule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A Minuscule window that holds a canvas in it.
 * 
 * @author Augustin Zidek
 * 
 */
public class MinusculeWindow {
	private final int width;
	private final int height;
	private final String title;
	private final JFrame frame;
	private final JPanel contentPane;
	private final Canvas canvas;
	private final Color bg;

	/**
	 * Creates a default canvas window with a canvas on which basic geometric
	 * shapes can be drawn using the methods provided in the {@link Canvas}.
	 * 
	 * The background color of the default canvas is set to white, the window
	 * title is set to "Minuscule Canvas".
	 * 
	 * @param width The width of the canvas (the width of the window will be
	 *            slightly bigger).
	 * @param height The height of the canvas (the width of the window will be
	 *            bigger by the control panel on the bottom).
	 */
	public MinusculeWindow(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.title = Constants.DEFAULT_WINDOW_TITLE;
		this.bg = Color.WHITE;
		// Initialize the frame and the canvas itself
		this.frame = new JFrame();
		this.contentPane = new JPanel();
		this.canvas = new Canvas(width, height);
		this.initUI();
	}

	/**
	 * Creates a canvas window with a canvas on which basic geometric shapes can
	 * be drawn using the methods provided in the {@link Canvas}.
	 * 
	 * @param width The width of the canvas (the width of the window will be
	 *            slightly bigger).
	 * @param height The height of the canvas (the width of the window will be
	 *            bigger by the control panel on the bottom).
	 * @param title The title of the window.
	 * @param bgColor The background color of the canvas.
	 */
	public MinusculeWindow(final int width, final int height,
			final String title, final Color bgColor) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.bg = bgColor;

		// Initialize the frame and the canvas
		this.frame = new JFrame();
		this.contentPane = new JPanel();
		this.canvas = new Canvas(width, height);
		this.initUI();
	}

	/**
	 * Initializes the GUI, i.e. sets the title, visibility and other various
	 * properties
	 */
	private void initUI() {
		this.frame.setTitle(this.title);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add the content panel
		this.frame.setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		// The panel with the canvas
		this.contentPane.add(this.canvas, BorderLayout.CENTER);
		this.canvas.setPreferredSize(new Dimension(this.width, this.height));

		// The panel with controls
		final JPanel controlPanel = new JPanel();
		this.contentPane.add(controlPanel, BorderLayout.SOUTH);
		controlPanel.setLayout(new GridLayout(1, 3));

		// The panel with information
		final JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		controlPanel.add(infoPanel, BorderLayout.NORTH);

		// Author and copyright info (please, don't remove)
		final JLabel lblInfo = new JLabel(Constants.AUTHOR);
		infoPanel.add(lblInfo, BorderLayout.SOUTH);

		// Info button, display information panel when clicked
		final JButton btnInfo = new JButton("Info");
		infoPanel.add(btnInfo, BorderLayout.WEST);
		btnInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoDialog(MinusculeWindow.this.frame, "Information");
			}
		});

		// Ugly, but works, adds space above help button
		final JLabel lblBlank = new JLabel(" ");
		infoPanel.add(lblBlank, BorderLayout.NORTH);

		// The zoom panel
		final JPanel zoomPanel = new JPanel();
		zoomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		controlPanel.add(zoomPanel);

		final JLabel lblZoom = new JLabel("Zoom:");
		zoomPanel.add(lblZoom);

		// The zoom slider
		final JSlider slider = new JSlider(1, 100, 25);
		zoomPanel.add(slider);
		final Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(new Integer(2), new JLabel("0.04"));
		labelTable.put(new Integer(Constants.ZOOM_1_VALUE), new JLabel("1"));
		labelTable.put(new Integer(35), new JLabel("2"));
		labelTable.put(new Integer(50), new JLabel("4"));
		labelTable.put(new Integer(75), new JLabel("9"));
		labelTable.put(new Integer(100), new JLabel("16"));
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(25);
		slider.setPaintTicks(true);

		// The listener for the slider, that zooms the canvas
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// Get the raw value from the slider
				final double sliderValue = slider.getValue();
				final double zoomFactor;
				// For values 0--25 use v/25, for higher values use (v/25)^2.
				if (sliderValue < Constants.ZOOM_1_VALUE) {
					zoomFactor = sliderValue / Constants.ZOOM_1_VALUE;
				}
				else {
					zoomFactor = (sliderValue / Constants.ZOOM_1_VALUE)
							* (sliderValue / Constants.ZOOM_1_VALUE);
				}
				MinusculeWindow.this.canvas.setZoom(zoomFactor);
			}
		});

		this.frame.pack();
		this.frame.setVisible(true);

		// Initialise the canvas
		this.canvas.clearAll();
		this.canvas.setBackground(this.bg);

		// The mouse drag listener responsible for mouse translation
		final MouseAdapter mouseDragL = new CanvasMouseMoveListener(this.canvas);
		this.canvas.addMouseListener(mouseDragL);
		this.canvas.addMouseMotionListener(mouseDragL);

		// The mouse wheel listener responsible for zooming
		final MouseWheelListener mouseWhL = new CanvasMouseWheelListener(slider);
		this.canvas.addMouseWheelListener(mouseWhL);

		// The keyboard listener responsible for translation
		final KeyListener keyListener = new CanvasKeyboardListener(this.canvas,
				slider);
		// Set focusable, so that keyboard events are captured
		this.canvas.setFocusable(true);
		this.canvas.requestFocus();
		// Don't allow the canvas to loose focus
		this.canvas.setFocusTraversalKeysEnabled(false);
		this.canvas.addKeyListener(keyListener);
	}

	/**
	 * @return The canvas within this window that can be drawn on
	 */
	public Canvas getCanvas() {
		return this.canvas;
	}
}
