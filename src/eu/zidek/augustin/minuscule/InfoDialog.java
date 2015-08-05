package eu.zidek.augustin.minuscule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The info dialog which loads the information box from the Internet.
 * 
 * @author Augustin Zidek
 * 
 */
class InfoDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	InfoDialog(final JFrame parent, final String title) {
		super(parent, title, true);
		this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

		try {
			final JEditorPane ep = new JEditorPane(Constants.INFO_PATH);
			ep.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES,
					Boolean.TRUE);
			ep.setFont(Constants.INFO_FONT);
			ep.setEditable(false);
			this.getContentPane().add(ep);
		}
		catch (IOException ioe) {
			JOptionPane.showMessageDialog(parent, Constants.INFO_NO_WEB);
			return;
		}

		final JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		final JButton button = new JButton("OK");
		buttonPane.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InfoDialog.this.setVisible(false);
				InfoDialog.this.dispose();
			}
		});

		this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(parent);
	}
}
