package lemon.engine.math;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

public class Matrix {
	public static final Matrix ZERO_4 = Matrix.unmodifiableMatrix(new Matrix(4));
	public static final Matrix IDENTITY_4 = Matrix.unmodifiableMatrix(Matrix.getIdentity(4));
	private static final String unmodifiableMessage = "Cannot Modify Matrix";
	private static final String ERROR_CANNOT_MULTIPLY = "You cannot multiply [%d x %d] by [%d x %d]";
	private float[][] data;

	public Matrix(int size) {
		this(size, size);
	}
	public Matrix(int m, int n) {
		this.data = new float[m][n];
	}
	public Matrix(float[][] data) {
		this.data = new float[data.length][data[0].length];
		for (int i = 0; i < data.length; ++i) {
			for (int j = 0; j < data[0].length; ++j) {
				this.data[i][j] = data[i][j];
			}
		}
	}
	public Matrix(Matrix matrix) {
		this(matrix.data);
	}
	public void clear() {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				data[i][j] = 0;
			}
		}
	}
	public void set(int m, int n, float data) {
		this.data[m][n] = data;
	}
	public float get(int m, int n) {
		return data[m][n];
	}
	public int getRows() {
		return data.length;
	}
	public int getColumns() {
		return data[0].length;
	}
	public FloatBuffer toFloatBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length * data[0].length);
		addToFloatBuffer(buffer);
		buffer.flip();
		return buffer;
	}
	public void addToFloatBuffer(FloatBuffer buffer) {
		for (int j = 0; j < this.getColumns(); j++) {
			for (int i = 0; i < this.getRows(); i++) {
				buffer.put(this.get(i, j));
			}
		}
	}
	public Matrix multiply(Matrix matrix) {
		if (this.getColumns() != matrix.getRows()) {
			throw new IllegalArgumentException(String.format(ERROR_CANNOT_MULTIPLY,
					this.getRows(), this.getColumns(), matrix.getRows(), matrix.getColumns()));
		}
		Matrix product = new Matrix(getRows(), matrix.getColumns());
		multiply(product, this, matrix);
		return product;
	}
	public static void multiply(Matrix result, Matrix left, Matrix right) {
		for (int i = 0; i < left.getRows(); ++i) {
			for (int j = 0; j < right.getColumns(); ++j) {
				float sum = 0;
				for (int k = 0; k < right.getRows(); ++k) {
					sum += left.get(i, k) * right.get(k, j);
				}
				result.set(i, j, sum);
			}
		}
	}
	@Override
	public String toString() {
		return Arrays.deepToString(data);
	}
	public static Matrix unmodifiableMatrix(Matrix matrix) {
		return new Matrix(matrix) {
			@Override
			public void set(int x, int y, float data) {
				throw new IllegalStateException(unmodifiableMessage);
			}
			@Override
			public void clear() {
				throw new IllegalStateException(unmodifiableMessage);
			}
		};
	}
	public static Matrix getIdentity(int size) {
		Matrix matrix = new Matrix(size);
		for (int i = 0; i < size; ++i) {
			matrix.set(i, i, 1);
		}
		return matrix;
	}
}
