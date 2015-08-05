package eu.zidek.augustin.minuscule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Queue storing the geometric objects. It is optimized for frequent inserts and
 * occasional sorted iteration. The geometric objects are sorted by their layer.
 * Moreover, multiple threads can access this queue, but the thread asking for
 * sorted data might not get some data that have been very recently put into the
 * buffer. However, this is sufficient for the current purposes.
 * 
 * This data structure is used in the following scenario: One thread adds new
 * objects into the queue. Once per while EDT schedules repaint on the canvas
 * and objects need to be retrieved sorted by their layer and displayed.
 * 
 * @author Augustin Zidek
 *
 */
public class GeometricObjectQueue {
	private List<MGeometricObject> objects = new ArrayList<>();
	private List<MGeometricObject> buffer = new ArrayList<>();
	private boolean mustBeSorted = false;

	/**
	 * Adds the given object into the queue. The cost of this operation is
	 * amortized <i>O(1)</i>.
	 * <p>
	 * Implementation note: The object is added into a internal buffer (in block
	 * synchronized on the buffer), which is copied into the whole queue only
	 * when <code>getSortedList()</code> executed.
	 * </p>
	 * 
	 * @param object The object to be added.
	 */
	public void add(final MGeometricObject object) {
		synchronized (this.buffer) {
			this.buffer.add(object);
		}
	}

	/**
	 * Forces the internal buffer to be flushed into the whole queue. The cost
	 * of this operation is amortized <i>O(n)</i> where <i>n</i> is the size of
	 * the buffer.
	 */
	public void flushBuffer() {
		synchronized (this.buffer) {
			// Buffer empty
			if (this.buffer.size() == 0) {
				return;
			}
			synchronized (this.objects) {
				this.objects.addAll(this.buffer);
			}
			this.buffer.clear();
		}

	}

	/**
	 * Gets the sorted list of the elements in the queue, sorted by the object's
	 * layers. The cost of this operation is <i>O(n*log(n))</i> if objects have
	 * been added/modified in the queue, <i>O(1)</i> if not.
	 * <p>
	 * Implementation note: <code>flushBuffer()</code> is called and then if
	 * modified, internal list holding the objects gets sorted using the
	 * built-in Java sort. If objects not modified, internal list returned
	 * without sorting.
	 * </p>
	 * 
	 * @return The sorted list of the objects in the queue, sorted by their
	 *         layers. A copy of the queue is actually returned, so that it can
	 *         be modified without any risk of illegal concurrent access to the
	 *         queue's internal structure.
	 */
	public synchronized List<MGeometricObject> getSortedList() {
		this.flushBuffer();

		if (this.mustBeSorted) {
			Collections.sort(this.objects);
		}
		// Return a copy, so that it can be safely manipulated
		final List<MGeometricObject> objCopy = new ArrayList<>(this.objects);
		return objCopy;
	}

	/**
	 * Removes the given object.
	 * <p>
	 * Implementation note: the method tries to remove the object from the
	 * internal queue. If successful <code>remove(object) == true</code>, then
	 * done. Otherwise it attempts to remove it from the buffer.
	 * </p>
	 * 
	 * @param object The object to be removed. If it doesn't exist within the
	 *            queue, nothing happens.
	 */
	public synchronized void remove(final MGeometricObject object) {
		final boolean success = this.objects.remove(object);
		// Try removing from the buffer only if not removed from objects
		if (!success) {
			this.buffer.remove(object);
		}
	}

	/**
	 * Tells the queue that layer of one of its elements has been modified.
	 */
	public void markDirty() {
		this.mustBeSorted = true;
	}

	/**
	 * Clears the queue, i.e. removes all elements from the buffer and from the
	 * internal list.
	 */
	public void clear() {
		synchronized (this.buffer) {
			this.buffer.clear();
		}
		synchronized (this.objects) {
			this.objects.clear();
		}
	}

}
