package lemon.engine.math;

import java.nio.IntBuffer;
import java.util.Optional;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class MathUtil {
	public static final float PI = (float) Math.PI;

	private MathUtil() {}
	
	public static boolean inRange(float a, float min, float max) {
		return a >= min && a <= max;
	}
	public static boolean[] convertMods(int bitset) {
		boolean[] mods = new boolean[4];
		if ((bitset & (1 << 0)) == 1)
			mods[0] = true;
		if ((bitset & (1 << 1)) == 1)
			mods[1] = true;
		if ((bitset & (1 << 2)) == 1)
			mods[2] = true;
		if ((bitset & (1 << 3)) == 1)
			mods[3] = true;
		return mods;
	}
	public static float toRadians(float degrees) {
		return (float) Math.toRadians(degrees);
	}
	public static float toDegrees(float radians) {
		return (float) Math.toDegrees(radians);
	}
	public static float getAspectRatio(long window) {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(window, width, height);
		return ((float) width.get()) / ((float) height.get());
	}
	public static Matrix getPerspective(Projection projection) {
		Matrix matrix = new Matrix(4);
		float yScale = (float) (1f / Math.tan(projection.getFov() / 2f));
		float xScale = yScale / projection.getAspectRatio();
		matrix.set(0, 0, xScale);
		matrix.set(1, 1, yScale);
		matrix.set(2, 2, -(projection.getNearPlane() + projection.getFarPlane())
				/ (projection.getFarPlane() - projection.getNearPlane()));
		matrix.set(2, 3, (-2 * projection.getNearPlane() * projection.getFarPlane())
				/ (projection.getFarPlane() - projection.getNearPlane()));
		matrix.set(3, 2, -1);
		matrix.set(3, 3, 0);
		return matrix;
	}
	public static Matrix getOrtho(float width, float height, float near, float far) {
		Matrix matrix = new Matrix(4);
		matrix.set(0, 0, 2f / width);
		matrix.set(1, 1, 2f / height);
		matrix.set(2, 2, 1f / (far - near));
		matrix.set(0, 3, -1f);
		matrix.set(1, 3, -1f);
		matrix.set(2, 3, (-near) / (far - near));
		matrix.set(3, 3, 1);
		return matrix;
	}
	public static Matrix getTranslation(Vector3D vector) {
		Matrix matrix = Matrix.getIdentity(4);
		matrix.set(0, 3, vector.getX());
		matrix.set(1, 3, vector.getY());
		matrix.set(2, 3, vector.getZ());
		return matrix;
	}
	public static Matrix getRotationX(float angle) {
		Matrix matrix = new Matrix(4);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		matrix.set(0, 0, 1);
		matrix.set(1, 1, cos);
		matrix.set(2, 1, sin);
		matrix.set(1, 2, -sin);
		matrix.set(2, 2, cos);
		matrix.set(3, 3, 1);
		return matrix;
	}
	public static Matrix getRotationY(float angle) {
		Matrix matrix = new Matrix(4);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		matrix.set(0, 0, cos);
		matrix.set(1, 1, 1);
		matrix.set(2, 0, -sin);
		matrix.set(0, 2, sin);
		matrix.set(2, 2, cos);
		matrix.set(3, 3, 1);
		return matrix;
	}
	public static Matrix getRotationZ(float angle) {
		Matrix matrix = new Matrix(4);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		matrix.set(0, 0, cos);
		matrix.set(0, 1, sin);
		matrix.set(1, 0, -sin);
		matrix.set(1, 1, cos);
		matrix.set(2, 2, 1);
		matrix.set(3, 3, 1);
		return matrix;
	}
	public static Matrix getScalar(Vector3D vector) {
		Matrix matrix = new Matrix(4);
		matrix.set(0, 0, vector.getX());
		matrix.set(1, 1, vector.getY());
		matrix.set(2, 2, vector.getZ());
		matrix.set(3, 3, 1);
		return matrix;
	}

	/**
	 * @param value value to be clamped
	 * @param lower lower bound
	 * @param upper upper bound
	 * @return clamped value so it is in [a, b]
	 */
	public static float clamp(float value, float lower, float upper) {
		return Math.max(lower, Math.min(upper, value));
	}

	public static Optional<Float> getLowestRoot(float a, float b, float c, float maxRoot) {
		float determinant = b * b - 4.0f * a * c;

		if (determinant < 0.0f) {
			return Optional.empty();
		}

		float sqrtD = (float) Math.sqrt(determinant);
		float root1 = (-b - sqrtD) / (2 * a);
		float root2 = (-b + sqrtD) / (2 * a);

		// Swap so root1 <= root2
		if (root1 > root2) {
			float temp = root2;
			root2 = root1;
			root1 = temp;
		}

		if (root1 > 0 && root1 < maxRoot) {
			return Optional.of(root1);
		}
		if (root2 > 0 && root2 < maxRoot) {
			return Optional.of(root2);
		}
		return Optional.empty();
	}
}
