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
import sporemodder.util.Vector2;

public class cSPUIStdDrawable extends StdDrawable {
	private cSPUIStdDrawableImageInfo image0;
	private cSPUIStdDrawableImageInfo image1;
	private cSPUIStdDrawableImageInfo image2;
	private cSPUIStdDrawableImageInfo image3;
	private cSPUIStdDrawableImageInfo image4;
	private cSPUIStdDrawableImageInfo image5;
	private cSPUIStdDrawableImageInfo image6;
	private cSPUIStdDrawableImageInfo image7;

	public cSPUIStdDrawableImageInfo getImage(int index) {
		switch (index) {
		case 0: return image0;
		case 1: return image1;
		case 2: return image2;
		case 3: return image3;
		case 4: return image4;
		case 5: return image5;
		case 6: return image6;
		case 7: return image7;
		default: return null;
		}
	}

	public void setImage(int index, cSPUIStdDrawableImageInfo info) {
		switch (index) {
			case 0: image0 = info; break;
			case 1: image1 = info; break;
			case 2: image2 = info; break;
			case 3: image3 = info; break;
			case 4: image4 = info; break;
			case 5: image5 = info; break;
			case 6: image6 = info; break;
			case 7: image7 = info; break;
			default: break;
		}
	}


	@Override
	public boolean paintInternal(GraphicsContext graphics, int index, SPUIRectangle area, Color shadeColor) {
		cSPUIStdDrawableImageInfo image = getImage(index);
		if (image == null)
			image = image0;

		if (image == null)
			return false;


		SPUIRectangle backgroundArea = new SPUIRectangle();

		Vector2 backgroundOffset = image.backgroundOffset;
		backgroundArea.x1 = backgroundOffset.getX() + area.x1;
		backgroundArea.y1 = backgroundOffset.getY() + area.y1;

		Vector2 backgroundScale = image.backgroundScale;
		// Are we sure it works like this? Maybe it tiles it
		backgroundArea.x2 = backgroundArea.x1 + backgroundScale.getX() * area.getWidth();
		backgroundArea.y2 = backgroundArea.y1 + backgroundScale.getY() * area.getHeight();


		boolean backgroundPainted = paintBackground(graphics, image, backgroundArea, shadeColor);
		boolean iconPainted = paintIcon(graphics, image, backgroundArea, shadeColor);
		return backgroundPainted || iconPainted;
	}


	boolean paintBackground(GraphicsContext graphics, cSPUIStdDrawableImageInfo image, SPUIRectangle backgroundArea, Color shadeColor) {
		ISporeImage backgroundImage = image.backgroundImage;
		if (backgroundImage == null)
			return false;

		Color newShadeColor = ISporeImage.multiplyColor(image.backgroundColor, shadeColor);
		return ISporeImage.drawImage(graphics, backgroundImage, backgroundArea, newShadeColor, scaleType, scaleArea);
	}


	boolean paintIcon(GraphicsContext graphics, cSPUIStdDrawableImageInfo image, SPUIRectangle backgroundArea, Color shadeColor) {
		ISporeImage iconImage = image.iconImage;
		if ((iconImage == null) && (image0 != null)) {
			// Sometimes only the first one is specified
			iconImage = image0.iconImage;
		}

		if (iconImage == null)
			return false;

		SPUIRectangle iconArea = new SPUIRectangle();
		iconArea.setWidth(iconImage.getWidth() * image.iconScale.getX());
		iconArea.setHeight(iconImage.getHeight() * image.iconScale.getY());
		
		float h = (backgroundArea.getWidth() - iconArea.getWidth()) / 2;
		float v = (backgroundArea.getHeight() - iconArea.getHeight()) / 2;
		iconArea.translateX(backgroundArea.x1 + h + image.iconOffset.getX());
		iconArea.translateY(backgroundArea.y1 + v + image.iconOffset.getY());
		
		Color newShadeColor = ISporeImage.multiplyColor(image.iconColor, shadeColor);
		return ISporeImage.drawImage(graphics, iconImage, iconArea, newShadeColor);
	}


	@Override public Vector2 getDimensions(int imageIndex) {
		cSPUIStdDrawableImageInfo info = getImage(imageIndex);
		if (info == null || info.backgroundImage == null) return super.getDimensions(imageIndex);
		return new Vector2(info.backgroundImage.getWidth(), info.backgroundImage.getHeight());
	}

	@Override ISporeImage getInspectorNamesakeImage() {
		return image0 != null
			? image0.backgroundImage
			: null
		;
	}
}
