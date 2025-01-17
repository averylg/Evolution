package lemon.engine.function;

import lemon.engine.math.MutableLine;
import lemon.engine.math.Vector3D;

import java.util.function.BinaryOperator;

public enum LineLineIntersection implements BinaryOperator<MutableLine> {
	INSTANCE;

	@Override
	public MutableLine apply(MutableLine line, MutableLine line2) {
		System.out.println(line + " - " + line2);
		Vector3D n = line.direction().crossProduct(line2.direction());
		float nAbsSquared = n.lengthSquared();
		if (nAbsSquared == 0) {
			//parallel
		}
		float nAbs = (float) Math.sqrt(nAbsSquared);
		var n1 = line.direction().crossProduct(n);
		var n2 = line2.direction().crossProduct(n);
		var offset = line2.origin().subtract(line.origin());
		System.out.println("Distance: " + Math.abs(n.divide(nAbs).dotProduct(offset)));
		// Warning: Not updated for new Vector
		return new MutableLine(line.origin().add(line.direction().multiply(offset.dotProduct(n2) / line.direction().dotProduct(n2))),
				line2.origin().add(line.direction().multiply(offset.dotProduct(n1) / line2.direction().dotProduct(n1))));
	}
}
