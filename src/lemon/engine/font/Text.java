package lemon.engine.font;

import java.nio.FloatBuffer;

import lemon.engine.draw.Drawable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import lemon.engine.font.Font.CharData;
import lemon.engine.render.VertexArray;

public class Text implements Drawable {
	private VertexArray vertexArray;
	private Font font;
	private String text;

	public Text(Font font, String text) {
		if (text.isEmpty()) {
			throw new IllegalStateException("Text is empty!");
		}
		this.font = font;
		this.text = text;
		vertexArray = new VertexArray();
		vertexArray.bind(vao -> {
			vertexArray.generateVbo().bind(GL15.GL_ARRAY_BUFFER, (target, vbo) -> {
				GL15.glBufferData(target, this.getFloatBuffer(), GL15.GL_STATIC_DRAW);
				GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * 4, 0);
				GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * 4, 2 * 4);
			});
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		});
	}
	private FloatBuffer getFloatBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(text.length() * 4 * 6);
		char prevChar = text.charAt(0);
		int cursor = 0;
		{
			CharData data = font.getCharData(prevChar);
			putChar(buffer, font.getCharData(prevChar), cursor);
			cursor += data.getXAdvance();
		}
		for (int i = 1; i < text.length(); ++i) {
			char currentChar = text.charAt(i);
			CharData data = font.getCharData(currentChar);
			int kerning = font.getKerning(prevChar, currentChar);
			putChar(buffer, data, cursor + kerning);
			cursor += (data.getXAdvance() + kerning);
			prevChar = currentChar;
		}
		buffer.flip();
		return buffer;
	}
	private void putChar(FloatBuffer buffer, CharData data, int cursor) {
		float scaleWidth = font.getScaleWidth();
		float scaleHeight = font.getScaleHeight();
		float textureX = data.getX() / scaleWidth;
		float textureY = data.getY() / scaleHeight;
		float textureWidth = data.getWidth() / scaleWidth;
		float textureHeight = data.getHeight() / scaleHeight;
		float x = cursor + data.getXOffset();
		float y = font.getLineHeight() - data.getYOffset();
		float width = data.getWidth();
		float height = data.getHeight();

		put(buffer, x, y, textureX, textureY);
		put(buffer, x + width, y, textureX + textureWidth, textureY);
		put(buffer, x + width, y - height, textureX + textureWidth, textureY + textureHeight);
		put(buffer, x, y, textureX, textureY);
		put(buffer, x, y - height, textureX, textureY + textureHeight);
		put(buffer, x + width, y - height, textureX + textureWidth, textureY + textureHeight);
	}
	private void put(FloatBuffer buffer, float... floats) {
		buffer.put(floats);
	}
	@Override
	public void draw() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		font.getTexture().bind(GL11.GL_TEXTURE_2D, () -> {
			vertexArray.bind(vao -> {
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.length() * 6);
			});
		});
		GL11.glDisable(GL11.GL_BLEND);
	}
}
