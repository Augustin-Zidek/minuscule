package eu.zidek.augustin.minuscule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private final JSlider slider = new JSlider(1, 100, 25);
	private final JLabel lblZoomValue = new JLabel("100%");

	/**
	 * Creates a default canvas window with a canvas on which basic geometric
	 * shapes can be drawn using the methods provided in the {@link Canvas}.
	 * 
	 * The background color of the default canvas is set to white, the window
	 * title is set to "Minuscule Canvas".
	 * 
	 * The window od the default canvas is maximized.
	 */
	public MinusculeWindow() {
		this(0, 0, Constants.DEFAULT_WINDOW_TITLE, Color.WHITE);
		// Set size to 800x600 so that when un-maximized, this size is used
		this.frame.setSize(800, 600);
		// Maximize the frame
		this.frame.setExtendedState(this.frame.getExtendedState()
				| Frame.MAXIMIZED_BOTH);
	}

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
		this(width, height, Constants.DEFAULT_WINDOW_TITLE, Color.WHITE);
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

		// Makes resizing of the window nicer - the canvas doesn't flicker
		this.canvas.setDoubleBuffered(true);

		// The lower panel with controls
		final JPanel controlPanel = new JPanel();
		this.contentPane.add(controlPanel, BorderLayout.SOUTH);
		controlPanel.setLayout(new BorderLayout());

		controlPanel.setDoubleBuffered(true);

		// The left lower panel with information
		final JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout(5, 0));
		// Add it some borders
		infoPanel.setBorder(new EmptyBorder(4, 2, 2, 0));
		controlPanel.add(infoPanel, BorderLayout.WEST);

		// Author and copyright info (please, don't remove)
		final JLabel lblInfo = new JLabel(Constants.AUTHOR);
		lblInfo.setFont(lblInfo.getFont().deriveFont(0, 9F));
		infoPanel.add(lblInfo, BorderLayout.EAST);

		// Info button, display information panel when clicked
		final JButton btnInfo = new JButton("Info");
		btnInfo.setMargin(new Insets(0, 2, 0, 2));
		infoPanel.add(btnInfo, BorderLayout.WEST);
		btnInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoDialog(MinusculeWindow.this.frame, "Information");
			}
		});

		final JButton btnSaveImage = new JButton("Save as PNG");
		btnSaveImage.setMargin(new Insets(0, 2, 0, 2));
		infoPanel.add(btnSaveImage, BorderLayout.CENTER);

		btnSaveImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the image from the canvas
				final BufferedImage img = MinusculeWindow.this.canvas
						.getImage();

				final JFileChooser fc = new JFileChooser();
				// Set up the filter for .png files
				final FileFilter filter = new FileNameExtensionFilter(
						"PNG File", "png");
				fc.setFileFilter(filter);

				// Get the return value from the save dialog
				int returnVal = fc.showSaveDialog(MinusculeWindow.this.frame);

				// If file selected, save the canvas image into the given path
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						// Write the image to the given file
						ImageIO.write(img, "png", fc.getSelectedFile());
						// Show a message confirming success
						JOptionPane.showMessageDialog(
								MinusculeWindow.this.frame, "Image "
										+ fc.getSelectedFile().toString()
										+ " saved successfully.");
					}
					catch (final IOException e1) {
						// On error, show error message
						JOptionPane
								.showMessageDialog(MinusculeWindow.this.frame,
										Constants.ERROR_MESSAGE_SAVE_IMAGE,
										"Error saving image",
										JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// The zoom panel
		final JPanel zoomPanel = new JPanel();
		zoomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		zoomPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		controlPanel.add(zoomPanel, BorderLayout.EAST);

		// The "Zoom" label
		final JLabel lblZoom = new JLabel("Zoom:");
		lblZoom.setFont(lblZoom.getFont().deriveFont(0));
		lblZoom.setToolTipText(Constants.TLT_ZOOM_LABEL);
		zoomPanel.add(lblZoom);

		// The zoom OUT button
		final JButton btnZoomOut = new JButton(Constants.BTN_ZOOM_IN);
		btnZoomOut.setMargin(new Insets(-2, 1, -2, 1));
		zoomPanel.add(btnZoomOut);

		btnZoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final double newZoom = MinusculeWindow.this.canvas.getZoom()
						* Constants.DEFAULT_ZOOM_OUT_FACTOR;
				MinusculeWindow.this.canvas.setZoomToPoint(newZoom,
						new MCoordinate(
								MinusculeWindow.this.canvas.getWidth() / 2,
								MinusculeWindow.this.canvas.getHeight() / 2));
				final String zoomValue = String.format("%.0f%%", newZoom * 100);
				MinusculeWindow.this.lblZoomValue.setText(zoomValue);
			}
		});

		// The zoom value label
		this.lblZoomValue.setToolTipText(Constants.TLT_ZOOM_VALUE);
		this.lblZoomValue.setFont(this.lblZoomValue.getFont().deriveFont(0));
		zoomPanel.add(this.lblZoomValue);

		// Listener for clicks on the zoom value label
		this.lblZoomValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Reset zoom to 100%
				MinusculeWindow.this.canvas.setZoom(1);
				// Set the zoom value label to default
				MinusculeWindow.this.lblZoomValue.setText("100%");
				// Set the slider to the position of 100%
				MinusculeWindow.this.slider.setValue(25);
			}
		});

		// The zoom IN button
		final JButton btnZoomIn = new JButton(Constants.BTN_ZOOM_OUT);
		btnZoomIn.setMargin(new Insets(-2, 1, -2, 1));
		zoomPanel.add(btnZoomIn);

		btnZoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final double newZoom = MinusculeWindow.this.canvas.getZoom()
						* Constants.DEFAULT_ZOOM_IN_FACTOR;
				MinusculeWindow.this.canvas.setZoomToPoint(newZoom,
						new MCoordinate(
								MinusculeWindow.this.canvas.getWidth() / 2,
								MinusculeWindow.this.canvas.getHeight() / 2));
				final String zoomValue = String.format("%.0f%%", newZoom * 100);
				MinusculeWindow.this.lblZoomValue.setText(zoomValue);
			}
		});

		this.frame.pack();
		this.frame.setVisible(true);

		// Initialise the canvas
		this.canvas.clearAll();
		this.canvas.setBackground(this.bg);

		// The mouse drag listener responsible for mouse translation
		final MouseAdapter mouseDragL = new CanvasMouseListener(this.canvas,
				this);
		this.canvas.addMouseListener(mouseDragL);
		this.canvas.addMouseMotionListener(mouseDragL);

		// The mouse wheel listener responsible for zooming
		final MouseWheelListener mouseWhL = new CanvasMouseWheelListener(
				this.canvas);
		this.canvas.addMouseWheelListener(mouseWhL);

		// The keyboard listener responsible for translation
		final KeyListener keyListener = new CanvasKeyboardListener(this.canvas,
				this.slider);
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

	/**
	 * Resets the zoom slider to the position of 100% and resets the zoom value
	 * label to 100%.
	 */
	void resetZoomFactor() {
		this.slider.setValue(25);
		this.lblZoomValue.setText("100%");
	}
}
