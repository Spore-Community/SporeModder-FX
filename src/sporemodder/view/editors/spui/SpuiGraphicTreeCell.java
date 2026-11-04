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
package sporemodder.view.editors.spui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeCell;
import javafx.scene.paint.Color;
import sporemodder.file.spui.SPUIRectangle;

public abstract class SpuiGraphicTreeCell<T extends ISpuiGraphicItem> extends TreeCell<T> {
	private final float ICON_INSET = 2;
	private final float ICON_SIZE = 16;
	private final float ICON_FAR = ICON_SIZE + ICON_INSET;
	private final float ICON_SIZE_EXP = ICON_SIZE + (ICON_INSET * 2);
	private final Color ICON_BG = Color.GRAY;

	private Canvas iconCanvas = new Canvas(ICON_SIZE_EXP, ICON_SIZE_EXP);


	@Override protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		getStyleClass().setAll(getStyleClasses());
		
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		}
		else {
			setText(getLabelText(item));

			GraphicsContext graphics = iconCanvas.getGraphicsContext2D();
			graphics.clearRect(0, 0, ICON_SIZE_EXP, ICON_SIZE_EXP);
			graphics.setFill(ICON_BG);
			graphics.fillRect(0, 0, ICON_SIZE_EXP, ICON_SIZE_EXP);

			SPUIRectangle area = new SPUIRectangle(ICON_INSET, ICON_INSET, ICON_FAR, ICON_FAR);

			if (drawIcon(item, graphics, area))
				setGraphic(iconCanvas);
			else
				setGraphic(null);
		}
	}


    public List<String> getStyleClasses() {
        List<String> classes = new ArrayList<>();
        classes.add("tree-cell");
        classes.add("spui-editor-item-window");
        return classes;
    }


	public String getLabelText(T item) {
        return item.getInspectorLabel();
    }

	public boolean drawIcon(T item, GraphicsContext graphics, SPUIRectangle area) {
		return item.drawInspectorIcon(graphics, area);
    }
}
