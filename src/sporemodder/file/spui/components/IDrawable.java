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
import sporemodder.file.spui.SPUIRectangle;
import sporemodder.file.spui.SporeUserInterface;
import sporemodder.file.spui.SpuiElement;
import sporemodder.file.spui.SpuiViewer;
import sporemodder.util.Vector2;
import sporemodder.view.editors.spui.ISpuiGraphicItem;

// Not an interface or abstract because non-implemented drawables will use this class
public class IDrawable extends SpuiElement implements ISpuiGraphicItem {
	public static final int TILING_NONE = 0;
	public static final int TILING_STANDARD = 1;
	public static final int TILING_EDGE = 2;


	public static final int STRETCH_IMAGE = 1;
	public static final int STRETCH_CENTER = 2;
	public static final int TILE_IMAGE = 3;
	public static final int TILE_CENTER = 4;


	public void paint(IWindow window, SpuiViewer viewer) {
		paint(viewer.getGraphicsContext2D(), window.getRealArea(), window.getShadeColor());
	}

	public boolean paint(GraphicsContext graphics, SPUIRectangle area, Color color) {
		return false;
	}
	
	public Vector2 getDimensions(int imageIndex) {
		return new Vector2();
	}

	@Override
	public Vector2 getSize() {
		return getDimensions(SporeUserInterface.INDEX_IDLE);
	}

	ISporeImage getInspectorNamesakeImage() {
		return null;
	}
	@Override
	public String getInspectorLabel() {
		ISporeImage image = getInspectorNamesakeImage();
		return toString() + "    " + (image != null
			? image.toString()
			: "(no image)"
		);
	}

	@Override
	public boolean drawInspectorIcon(GraphicsContext graphics, SPUIRectangle area) {
		return paint(graphics, area, Color.WHITE);
	}
}
