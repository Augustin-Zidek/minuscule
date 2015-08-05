package eu.zidek.augustin.minuscule;

/**
 * Exception thrown when there is no registered painter for an object on the
 * canvas. This is usually thrown when the developer forgets to register their
 * Painter in the PainterManager.
 * 
 * @author Augustin Zidek
 *
 */
public class NoPainterException extends Exception {
	private static final long serialVersionUID = 1L;

}
