package tablelayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Powerful layout manager that displays components in rows or columns, where each row or column can take up a different percentage of the height or width respectively.
 * The percentage used is passed as a (Double) constraint when Components are added to the Container using this layout manager.
 * Alternatively components can be added to an overlay layer where they will not use the rows or columns, instead appearing in a fixed position determined by a (Point) constraint when added to the container.
 * This layout manager does not honour preferred size for row or column components (it does for overlay components), use (Double) constraints parameter in Container.add method to size component in only 1 dimension (based on whether rows or columns are being used).
 * This layout manager does not currently give a preferred size, it will take up as much room as it can. This will be changed so that JFrame.pack will make the window as large as possible
 * Hints:
 *	JFrame should have size set or content pane wont be visible without resizing as preferred size is 0
 *	Overlay components must be added BEFORE any row or column components that they should appear over (trying to remove this as a condition, contact me if you have a solution)
 * 
 * @author gilesholdsworth
 */
public class TableLayout implements LayoutManager2 {
	
	// Internal classes

	/**
	 * Simple class to hold all data about a row or column
	 */
	public class Item {
	  public Component comp;
		public double size;

		public Item(Component comp, double size) {
			this.comp = comp;
			this.size = size;
		}
	}

	/**
	 * Simple class to hold all data about an overlay component
	 */
	public class OverlayItem {
		public Component comp;
		public Point position;

		public OverlayItem(Component comp, Point position) {
			this.comp = comp;
			this.position = position;
		}
	}
	
	// Member variables

	public static final int ROWS = 0;
	public static final int COLUMNS = 1;
	public static final int X = 0;
	public static final int Y = 1;

	/**
	 * Whether the components should be laid out in rows or columns, equal to 0 for rows or 1 for columns
	 */
	public int layoutType = 0;

	/**
	 * Items to be used as rows or columns
	 */
	private List<Item> items = new ArrayList<>();
	/**
	 * Items to be placed over the top of those in the list items, placed in specified position
	 */
	private List<OverlayItem> overlayItems = new ArrayList<>();



	/**
	 * Initialise object and set whether components should be displayed in rows or columns
	 * @param layoutType Choose rows (int 0) or columns (int 1), constants available for readability
	 */
	public TableLayout(int layoutType) {
		this.layoutType = layoutType;
	}
	
	// New methods

	/**
	 * Add component to the layout as a row or column
	 * @param comp Component to add
	 * @param size Percentage of height (rows) or width (columns) for component to take up
	 */
	private void add(Component comp, double size) {
		Item newItem = new Item(comp, size);
		items.add(newItem);
	}

	/**
	 * Add component to the parent as an overlay
	 * @param comp Component to add
	 * @param position Coordinates to place top left of comp relative to the container's origin
	 */
	private void add(Component comp, Point position) {
		OverlayItem newItem = new OverlayItem(comp, position);
		overlayItems.add(newItem);
	}
	
	/**
	 * Creates string representation of the state of the object
	 * @return String representation of the state of the object
	 */
	public String toString() {
		return String.format("TableLayout[layoutType=%d,items=%s]", layoutType, items.toString());
	}

	// Overridden methods

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This is not supported by this LayoutManager.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints != null) {
			if (constraints instanceof Double) {
				add(comp, (Double) constraints);
				return;
			}
			if (constraints instanceof Point) {
				add(comp, (Point) constraints);
				return;
			}
		}
		throw new InvalidParameterException("Container.add requires (Double or Point) constraint parameter for TableLayout containers");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < items.size(); ++i) {
			if (items.get(i).comp == comp) {
				items.remove(i);
				return;
			}
		}
		for (int i = 0; i < overlayItems.size(); ++i) {
			if (overlayItems.get(i).comp == comp) {
				overlayItems.remove(i);
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(0,0);

		/*if (parent instanceof Frame || (parent.getParent().getParent().getParent() != null && parent.getParent().getParent().getParent() instanceof JFrame)) {
			DisplayMode d = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
			return new Dimension(d.getWidth(),d.getHeight());
		} else {
			return new Dimension(0,0);
		}
		Insets insets = parent.getInsets();
		Dimension preferredSize = new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		
		Dimension compSize;
		for (int i = 0; i < parent.getComponentCount(); ++i) {
			compSize = parent.getComponent(i).getPreferredSize();
			preferredSize.width += compSize.width;
			preferredSize.height += compSize.height;
		}
		
		return preferredSize;*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {return new Dimension(0,0);}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container parent) {return new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE);}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {return 0.5f;}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {return 0.5f;}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {
		int x = 0;
		int y = 0;
		// lay out rows/columns
		for (int i = 0 ; i < items.size(); i++) {
			Item item = items.get(i);
			if (item.comp.isVisible()) {
				Dimension preferredSize = new Dimension(0,0);
				if (layoutType == ROWS) {
					preferredSize.height = (int) Math.round((parent.getHeight() / 100.0) * item.size);
					preferredSize.width = parent.getWidth();
				} else {
					preferredSize.width = (int) Math.round((parent.getWidth() / 100.0) * item.size);
					preferredSize.height = parent.getHeight();
				}
				//System.out.println("Layout " + layoutType + " Component " + i + " preferred size = " + preferredSize.toString());
				item.comp.setBounds(x, y, preferredSize.width, preferredSize.height);
				if (layoutType == ROWS) {
					y += preferredSize.height;
				} else {
					x += preferredSize.width;
				}
			}
		}
		// Lay out overlay items
		for (int i = 0 ; i < overlayItems.size(); i++) {
			OverlayItem item = overlayItems.get(i);
			if (item.comp.isVisible()) {
				Dimension preferredSize = item.comp.getPreferredSize();
				if (preferredSize == null) {preferredSize = new Dimension(0,0);}
				//System.out.println("Overlay Component " + i + " preferred size = " + preferredSize.toString() + " position " + item.position.toString());
				item.comp.setBounds(item.position.x, item.position.y, preferredSize.width, preferredSize.height);
			}
		}
	}
    

}
