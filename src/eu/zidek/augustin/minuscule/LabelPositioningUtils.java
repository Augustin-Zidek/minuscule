package eu.zidek.augustin.minuscule;

import static eu.zidek.augustin.minuscule.Constants.DEFAULT_FONT;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_LABEL2LABEL_SPACING;
import static eu.zidek.augustin.minuscule.Constants.DEG2RAD;
import static eu.zidek.augustin.minuscule.Constants.RAD2DEG;
import static java.lang.Math.atan;
import static java.lang.Math.tan;

import java.awt.Font;
import java.awt.FontMetrics;

/**
 * This class has a couple of methods which are used when labels are being
 * positioned automatically. Developers of their own geometric objects are
 * encouraged to use these utilities in their <code>getLabelCoordinates</code>
 * methods.
 * 
 * @author Augustin Zidek
 *
 */
public class LabelPositioningUtils {

	/**
	 * Determines relative coordinates of a label placed within certain distance
	 * from the base and at the given angle.
	 * 
	 * @param angleDeg The angle in degrees relative to the x-axis in the
	 *            positive direction with the origin in the label base
	 *            coordinates
	 * @param font The font of the label
	 * @param text The text of the label. If the text is <code>null</code> empty
	 *            string ("") will be used instead.
	 * @param baseDistance The distance at which the label should be from the
	 *            base coordinate. This method will make sure that the label is
	 *            NEVER within this circle and always has some extra small space
	 *            (so it is not touching the bounding circle).
	 * @return The 2D coordinate of the left bottom corner of the label
	 */
	public static MCoordinate getAutoPlacementToCircle(final double angleDeg,
			final Font font, final String text, final double baseDistance) {
		// Get label bounding dimensions
		final MCoordinate labelDim = getLabelDimensions(text, font);
		final double width = labelDim.x;
		final double height = labelDim.y;

		final double angleRad = angleDeg * DEG2RAD;

		// Calculate the raw coordinates
		double posX = Math.cos(angleRad) * baseDistance;
		double posY = -Math.sin(angleRad) * baseDistance;

		// Shift so that appropriate corner of bounding box is aligned
		// The y position is shifted by height/4: This is empirical, as the
		// height adds some extra space
		return alignAccordingToAngle(new MBoundingBox(posX, posY - height / 4,
				width, height), angleDeg);
	}

	/**
	 * Determines relative coordinates of a label placed on a circumference of a
	 * (bounding) rectangle at the given angle.
	 * 
	 * @param angleDeg The angle in degrees relative to the x-axis in the
	 *            positive direction with the origin in the label base
	 *            coordinates
	 * @param font The font of the label
	 * @param text The text of the label. If the text is <code>null</code> empty
	 *            string ("") will be used instead.
	 * @param rectangle The rectangle to which the label should be aligned
	 * @return The 2D coordinate of the left bottom corner of the label
	 */
	public static MCoordinate getAutoPlacementToRectangle(
			final double angleDeg, final MBoundingBox rectangle,
			final Font font, final String text) {
		// Make sure the angle is in range 0--360
		final double angleDg = (angleDeg % 360 + 360) % 360;

		// Get rectangle's upper right corner coordinates
		final double xBaseDst = rectangle.width / 2;
		final double yBaseDst = rectangle.height / 2;

		// Calculate the angle at which the upper right corner is
		final double cornerAngleDg = atan(yBaseDst / xBaseDst) * RAD2DEG;

		// The future lower-left coordinates of the label
		final double labelX;
		final double labelY;

		// Right side of the rectangle
		if ((angleDg >= 0 && angleDg < cornerAngleDg)
				|| (angleDg >= 360 - cornerAngleDg && angleDg < 360)) {
			labelX = xBaseDst + DEFAULT_LABEL2LABEL_SPACING;
			labelY = -(tan(angleDg * DEG2RAD) * xBaseDst + DEFAULT_LABEL2LABEL_SPACING);
		}
		// Top side of the rectangle
		else if (angleDg >= cornerAngleDg && angleDg < 180 - cornerAngleDg) {
			labelX = yBaseDst / tan(angleDg * DEG2RAD)
					+ DEFAULT_LABEL2LABEL_SPACING;
			labelY = -(yBaseDst + DEFAULT_LABEL2LABEL_SPACING);
		}
		// Left side of the rectangle
		else if (angleDg >= 180 - cornerAngleDg
				&& angleDg < 180 + cornerAngleDg) {
			labelX = -(xBaseDst + DEFAULT_LABEL2LABEL_SPACING);
			labelY = tan((angleDg - 180) * DEG2RAD) * xBaseDst
					+ DEFAULT_LABEL2LABEL_SPACING;
		}
		// Bottom side of the rectangle
		else {
			labelX = yBaseDst / tan(-angleDg * DEG2RAD)
					+ DEFAULT_LABEL2LABEL_SPACING;
			labelY = yBaseDst + DEFAULT_LABEL2LABEL_SPACING;
		}

		final MCoordinate childLabelDim = getLabelDimensions(text, font);

		// Return them shifted by label's bounding rectangle
		return LabelPositioningUtils.alignAccordingToAngle(new MBoundingBox(
				labelX, labelY, childLabelDim.x, childLabelDim.y), angleDg);
	}

	/**
	 * Calculates the dimensions of the given text using the give font.
	 * 
	 * @param text The text of the label. If <code>null</code>, then empty
	 *            string will be used.
	 * @param font The font of the label. If <code>null</code>, then the default
	 *            font will be used.
	 * @return The dimensions of the label (width, height) stored within the
	 *         {@link MCoordinate}
	 */
	public static MCoordinate getLabelDimensions(final String text,
			final Font font) {
		// Create a canvas to get the font metrics
		final java.awt.Canvas c = new java.awt.Canvas();
		// If font == null use default font instead
		final FontMetrics fM = c.getFontMetrics(font != null ? font
				: DEFAULT_FONT);
		// Get label width and height. If text == null use "" instead
		final double height = fM.getMaxAscent() + fM.getMaxDescent();
		final double width = fM.stringWidth(text != null ? text : "");
		return new MCoordinate(width, height);
	}

	/**
	 * This method takes the given bounding rectangle and shifts it according to
	 * the given angle so that: <br>
	 * If the angle is 0-90 the lower left corner of the rectangle is on the
	 * original position. I.e. no shifting happens in this case. <br>
	 * If the angle is 90-180 the lower right corner of the rectangle is on the
	 * original position. <br>
	 * If the angle is 180-270 the upper right corner of the rectangle is on the
	 * original position. <br>
	 * If the angle is 270-360 the upper left corner of the rectangle is on the
	 * original position.
	 * 
	 * @param boundingRect The bounding rectangle of the object, most likely the
	 *            label
	 * @param angleDeg The angle at which the bounding rectangle should be
	 *            positioned
	 * @return The coordinate where the lower left corner of the bounding box
	 *         should be placed. Note that the angle is used only to decide what
	 *         shifting shall be done, not for any positioning. For that use the
	 *         <code>getAutoPlacement</code> method.
	 */
	public static MCoordinate alignAccordingToAngle(
			final MBoundingBox boundingRect, final double angleDeg) {
		double posX = boundingRect.x;
		double posY = boundingRect.y;
		// Angles 0--90: no shifting
		// Angles 90--180: x-shift by text width
		if (angleDeg > 90 && angleDeg <= 180) {
			posX -= boundingRect.width;
		}
		// Angles 180--270: x-shift by text length, y-shift by text height
		else if (angleDeg > 180 && angleDeg <= 270) {
			posX -= boundingRect.width;
			posY += boundingRect.height;
		}
		// Angles 270--360: y-shift by text height
		else if (angleDeg > 270 && angleDeg <= 360) {
			posY += boundingRect.height;
		}
		return new MCoordinate(posX, posY);
	}

}
