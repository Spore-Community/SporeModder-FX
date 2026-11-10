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

import java.util.List;

import sporemodder.file.spui.components.ISporeImage;

public class SpuiImageItemCell extends SpuiGraphicTreeCell<ISporeImage> {
    @Override
	public List<String> getStyleClasses() {
        List<String> classes = super.getStyleClasses();
		classes.add("spui-editor-image-item");
		return classes;
    }
}
