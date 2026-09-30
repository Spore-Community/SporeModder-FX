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

import javafx.scene.control.TreeCell;
import sporemodder.file.spui.components.IDrawable;
import sporemodder.file.spui.components.ISporeImage;

public class SpuiDrawableItemCell extends TreeCell<IDrawable> {
	
	@Override protected void updateItem(IDrawable item, boolean empty) {
		super.updateItem(item, empty);
		setGraphic(null);
		
		if (empty || item == null) {
			setText(null);
		}
		else {
			setText(item.toString());
			getStyleClass().setAll("tree-cell", "spui-editor-item-window");
		}
	}
}
