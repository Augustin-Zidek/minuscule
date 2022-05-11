# Minuscule Manual

The manual covers the creation of custom `MGeometricObjects` as this can not be
easily read from the Javadoc. Creation of custom geometric objects should not be
necessary since the `MShape` object covers most cases, but this manual describes
how to do so if you need it.

To begin, create a class extending `MGeometricObject` say `CustomShape`. In
normal scenarios this would be the end of the work and one would be able to
start filling in the method bodies. However, due to the Builder pattern and
inheritance a few things are different here.

## Creating new custom `MShape`

Here is a template file, below you can find some comments on the things that
were done. TL;DR: Just copy this and modify it to your needs:

```java
import java.awt.Color;
import java.awt.Stroke;

public class CustomShape extends MGeometricObject {

  public CustomShape() {
    super(DEFAULT_CUSTOM_SHAPE_COLOR,
          DEFAULT_CUSTOM_SHAPE_FILL,
          DEFAULT_CUSTOM_SHAPE_LABEL,
          DEFAULT_CUSTOM_SHAPE_STROKE,
          DEFAULT_CUSTOM_SHAPE_LAYER);
  }

  @Override
  public CustomShape draw(Canvas canvas) {
    super.doDraw(canvas);
    return this;
  }

  @Override
  public CustomShape color(Color color) {
    super.setColor(color);
    return this;
  }

  @Override
  public CustomShape fill(boolean value) {
    super.setFill(value);
    return this;
  }

  @Override
  public CustomShape stroke(Stroke stroke) {
    super.setStroke(stroke);
    return this;
  }

  @Override
  public CustomShape layer(int layer) {
    super.setLayer(layer);
    return this;
  }

  @Override
  public CustomShape label(String labelText) {
    return this.label(labelText, DEFAULT_CUSTOM_SHAPE_LABEL_POSITION);
  }

  @Override
  public CustomShape label(String labelText, double angleDeg) {
    final MLabel label = new MLabel(labelText);
    // Set text, parent and position set by parent to true. Since position
    // set by parent set, no need to set coordinates.
    label.text(labelText);
    label.parent(this);
    label.positionSetByParent(true, angleDeg);
    super.setLabel(label);
    return this;
  }

  @Override
  public CustomShape label(MLabel label) {
    super.setLabel(label);
    return this;
  }

  @Override
  public CustomShape translate(double dx, double dy) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public MCoordinate getLabelBaseCoordinate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MCoordinate getLabelCoordinates(double angleDeg) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MBoundingBox getBoundingRectangle() {
    // TODO Auto-generated method stub
    return null;
  }
}
```

### 1. Constructor

First, you have to call the custom constructor of the super class
`MGeometricObject`, as it manages for you:

- Drawing
- Color
- Fill
- Stroke
- Layer
- Label

```java
public CustomShape() {
  // Define these variables as you wish
  super(DEFAULT_CUSTOM_SHAPE_COLOR,
        DEFAULT_CUSTOM_SHAPE_FILL,
        DEFAULT_CUSTOM_SHAPE_LABEL,
        DEFAULT_CUSTOM_SHAPE_STROKE,
        DEFAULT_CUSTOM_SHAPE_LAYER);
}
```

### 2. Builder methods

The `MGeometricObject` provides some setters and they can be used inside your
custom object builder methods. So for every of the methods

- `draw(Canvas canvas)`: use `super.doDraw`
- `color(Color color)`: use `super.setColor`
- `fill(boolean value)`: use `super.setFill`
- `stroke(Stroke stroke)`: use `super.setStroke`
- `layer(int layer)`: use `super.setLayer`
- `label(String labelText)`: see below
- `label(String labelText, double angleDeg)`: see below
- `label(MLabel label)`: use `super.setLabel`

you have to change their return type to `CustomShape` and in their bodies call
adequate method of the superclass and then return `this`.

```java
@Override
public CustomShape draw(Canvas canvas) {
  super.doDraw(canvas);
  return this;
}
```

### 3. Label

For the two label methods just use the provided code.

### 4. Custom methods

You have to define the remaining three methods and possibly add you own custom
methods that modify your custom object. However, try to keep the style of the
library consistent and use the Builder pattern. That means basically that all
your setters should return `this` so that they can be chained (and naming
convention: they should not be prefixed with "set"). It is recommend to look at
the code for some of the built-in geometric objects before implementing your
own.

```java
@Override
public CustomShape label(String labelText) {
  return this.label(labelText, DEFAULT_CUSTOM_SHAPE_LABEL_POSITION);
}

@Override
public CustomShape label(String labelText, double angleDeg) {
  final MLabel label = new MLabel(labelText);
  // Set text, parent and position set by parent to true. Since position
  // set by parent set, no need to set coordinates.
  label.text(labelText);
  label.parent(this);
  label.positionSetByParent(true, angleDeg);
  super.setLabel(label);
  return this;
}
```

### 5. Creating new custom `MShapePainter`

Each geometric object in Minuscule has to have its painter. The painter has to
implement the `MShapePainter` interface. Also, once you have written, it **has
to be registered** with the `PainterManager`.

In the painter you have direct access to the underlying **Graphics2D** canvas on
which you can draw according to the custom object's properties. Have a look at
`MPointPainter` for inspiration.
