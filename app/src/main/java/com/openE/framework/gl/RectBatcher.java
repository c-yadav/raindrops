package com.openE.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import com.openE.framework.implementation.GLGraphics;

public class RectBatcher {
	final float[] verticesBuffer;
	int bufferIndex;
	final Vertices vertices;
	int maxRect; 

	public RectBatcher(GLGraphics glGraphics, int maxRect) {
		this.verticesBuffer = new float[maxRect * 4 * 2]; // maxSprites * no of
															// vertices * no of
															// places ( i.e x,y )
		this.vertices = new Vertices(glGraphics, maxRect * 4, maxRect * 6,
				false, false);
		this.bufferIndex = 0;
		this.maxRect = maxRect;

		short[] indices = new short[maxRect * 6];
		int len = indices.length;
		short j = 0;
		for (int i = 0; i < len; i += 6, j += 4) { // j+=4, as 4 vertices
			indices[i + 0] = (short) (j + 0);
			indices[i + 1] = (short) (j + 1);
			indices[i + 2] = (short) (j + 2);
			indices[i + 3] = (short) (j + 2);
			indices[i + 4] = (short) (j + 3);
			indices[i + 5] = (short) (j + 0);
		}
		vertices.setIndices(indices, 0, indices.length);
	}

	public void beginBatch() {
		bufferIndex = 0;
	}

	public void endBatch() {
		vertices.setVertices(verticesBuffer, 0, bufferIndex);
		vertices.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, maxRect * 6);
		vertices.unbind();
	}

	public void drawRect(float x, float y, float width, float height) { // center
																		// location
																		// is
																		// passed
																		// as
																		// argument
		float halfWidth = width / 2;
		float halfHeight = height / 2;
		float x1 = x - halfWidth;
		float y1 = y - halfHeight;
		float x2 = x + halfWidth;
		float y2 = y + halfHeight;

		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y1;

		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y1;

		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y2;

		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y2;

	}
}