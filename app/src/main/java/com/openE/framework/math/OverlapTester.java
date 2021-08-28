package com.openE.framework.math;

public class OverlapTester {
	public static boolean overlapCircles(Circle c1, Circle c2) {
		float distance = c1.center.distSquared(c2.center);
		float radiusSum = c1.radius + c2.radius;
		return distance <= radiusSum * radiusSum;
	}

	public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
		if (r1.lowerLeft.x < r2.lowerLeft.x + r2.width
				&& r1.lowerLeft.x + r1.width > r2.lowerLeft.x
				&& r1.lowerLeft.y < r2.lowerLeft.y + r2.height
				&& r1.lowerLeft.y + r1.height > r2.lowerLeft.y)
			return true;
		else
			return false;
	}

	public static boolean overlapCircleRectangle(Circle c, Rectangle r) {
		float closestX = c.center.x;
		float closestY = c.center.y;

		if (c.center.x < r.lowerLeft.x) {
			closestX = r.lowerLeft.x;
		} else if (c.center.x > r.lowerLeft.x + r.width) {
			closestX = r.lowerLeft.x + r.width;
		}

		if (c.center.y < r.lowerLeft.y) {
			closestY = r.lowerLeft.y;
		} else if (c.center.y > r.lowerLeft.y + r.height) {
			closestY = r.lowerLeft.y + r.height;
		}

		return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
	}

	public static boolean pointInCircle(Circle c, Vector2 p) {
		return c.center.distSquared(p) < c.radius * c.radius;
	}

	public static boolean pointInCircle(Circle c, float x, float y) {
		return c.center.distSquared(x, y) < c.radius * c.radius;
	}

	public static boolean pointInRectangle(Rectangle r, Vector2 p) {
		return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x
				&& r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
	}

	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x
				&& r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
	}

	public static boolean xlineInRectangle(Rectangle r, float x) {
		return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x;
	}

	public static boolean ylineInRectangle(Rectangle r, float y) {
		return r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
	}

	public static boolean overlapLineRectangle(Line l, Rectangle r) {

		double rectMinX = r.lowerLeft.x;
		double rectMinY = r.lowerLeft.y;
		double rectMaxX = r.lowerLeft.x + r.width;
		double rectMaxY = r.lowerLeft.y + r.height;
		double lineStartX = l.startPosition.x;
		double lineStartY = l.startPosition.y;
		double lineEndX = l.endPosition.x;
		double lineEndY = l.endPosition.y;

		double minX = lineStartX;
		double maxX = lineEndX;

		if (lineStartX > lineEndX) {
			minX = lineEndX;
			maxX = lineStartX;
		}

		// Find the intersection of the segment's and rectangle's x-projections

		if (maxX > rectMaxX) {
			maxX = rectMaxX;
		}

		if (minX < rectMinX) {
			minX = rectMinX;
		}

		if (minX > maxX) // If their projections do not intersect return false
		{
			return false;
		}

		// Find corresponding min and max Y for min and max X we found before

		double minY = lineStartY;
		double maxY = lineEndY;

		double dx = lineEndX - lineStartX;

		if (Math.abs(dx) > 0.0000001) {
			double a = (lineEndY - lineStartY) / dx;
			double b = lineStartY - a * lineStartX;
			minY = a * minX + b;
			maxY = a * maxX + b;
		}

		if (minY > maxY) {
			double tmp = maxY;
			maxY = minY;
			minY = tmp;
		}

		// Find the intersection of the segment's and rectangle's y-projections

		if (maxY > rectMaxY) {
			maxY = rectMaxY;
		}

		if (minY < rectMinY) {
			minY = rectMinY;
		}

		if (minY > maxY) // If Y-projections do not intersect return false
		{
			return false;
		}

		return true;
	}
}
