/****************************************************************************
* Copyright (C) 2019 Eric Mor
*
* This file is part of SporeModder FX.
*
* SporeModder FX is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
****************************************************************************/
package sporemodder.file.spui.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sporemodder.file.spui.RLEHitMask;
import sporemodder.file.spui.SPUIRectangle;
import sporemodder.file.spui.SporeUserInterface;
import sporemodder.file.spui.SpuiViewer;
import sporemodder.util.Vector2;

public class StdDrawable extends IDrawable {
	private final ISporeImage[] image = new ISporeImage[8];
	private RLEHitMask hitMask;  //TODO use this to detect hits
	protected int scaleType;
	private final Vector2 scaleFactor = new Vector2(1.0f, 1.0f);
	protected final Borders scaleArea = new Borders();
	// outline format as well, but we don't use it
	
	@Override
	public void paint(IWindow window, SpuiViewer viewer) {
		paintInternal(viewer.getGraphicsContext2D(), SporeUserInterface.getImageIndex(window), window.getRealArea(), window.getShadeColor());
	}

	@Override
	public boolean paint(GraphicsContext graphics, SPUIRectangle area, Color color) {
		return paintInternal(graphics, SporeUserInterface.INDEX_IDLE, area, color);
	}

	public boolean paintInternal(GraphicsContext graphics, int index, SPUIRectangle area, Color color) {
		ISporeImage img = image[index];
		if (img == null)
			img = image[index];

		return ISporeImage.drawImage(graphics, img, area, color, scaleType, scaleArea);
	}
	
	@Override public Vector2 getDimensions(int imageIndex) {
		if (image[imageIndex] == null) return super.getDimensions(imageIndex);
		return new Vector2(image[imageIndex].getWidth(), image[imageIndex].getHeight());
	}

	public int getScaleType() {
		return scaleType;
	}

	public void setScaleType(int scaleType) {
		this.scaleType = scaleType;
	}

	public Vector2 getScaleFactor() {
		return scaleFactor;
	}

	public Borders getScaleArea() {
		return scaleArea;
	}

	@Override ISporeImage getInspectorNamesakeImage() {
		return image[0];
	}


	@Override
	public boolean drawInspectorIcon(GraphicsContext graphics, SPUIRectangle area) {
		Vector2 size = this.getSize();

		double areaInitWidth = area.getWidth();
		double areaInitHeight = area.getHeight();

		double scaleX = size.getX() / areaInitWidth;
		double scaleY = size.getY() / areaInitHeight;

		if ((size.getX() <= areaInitWidth) && (size.getY() <= areaInitHeight))
			return super.drawInspectorIcon(graphics, area);

		scaleX = limitInspectorIconSize(scaleX);
		scaleY = limitInspectorIconSize(scaleY);

		graphics.scale(1d / scaleX, 1d / scaleY);
		area.scale(scaleX, scaleY);
		boolean result = super.drawInspectorIcon(graphics, area);
		graphics.scale(scaleX, scaleY);
		return result;
	}

	static double limitInspectorIconSize(double value) {
		return Math.min(Math.max(value, 1d), 64d);
	}
}
