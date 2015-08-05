package eu.zidek.augustin.minuscule;

import static eu.zidek.augustin.minuscule.Constants.POPUP_MENU_RESET_BOTH;
import static eu.zidek.augustin.minuscule.Constants.POPUP_MENU_RESET_TRANSLANSLATION;
import static eu.zidek.augustin.minuscule.Constants.POPUP_MENU_RESET_ZOOM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Pop-up menu which shows on a right click on the canvas.
 * 
 * @author Augustin Zidek
 *
 */
public class CanvasPopUpMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	private final JMenuItem itmResetZoom = new JMenuItem(POPUP_MENU_RESET_ZOOM);
	private final JMenuItem itmResetTranslation = new JMenuItem(
			POPUP_MENU_RESET_TRANSLANSLATION);
	private final JMenuItem itmResetBoth = new JMenuItem(POPUP_MENU_RESET_BOTH);

	/**
	 * Creates a new pop up menu
	 * 
	 * @param canvas The canvas which this listener operates with
	 * @param window The window which this listener operates with (for resetting
	 *            the zoom slider)
	 */
	public CanvasPopUpMenu(final Canvas canvas, final MinusculeWindow window) {
		// The zoom reset button and its listener
		this.add(this.itmResetZoom);
		this.itmResetZoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.resetZoom();
				window.resetZoomFactor();
			}
		});

		// The translation reset button and its listener
		this.add(this.itmResetTranslation);
		this.itmResetTranslation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.resetTranslation();
			}
		});

		// The reset zoom and view button and its listener
		this.add(this.itmResetBoth);
		this.itmResetBoth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.resetTranslationAndZoom();
				window.resetZoomFactor();
			}
		});

	}
}
