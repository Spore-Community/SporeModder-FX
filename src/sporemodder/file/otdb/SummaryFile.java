package sporemodder.file.otdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import sporemodder.HashManager;
import sporemodder.file.ResourceKey;
import sporemodder.file.argscript.ArgScriptArguments;
import sporemodder.file.argscript.ArgScriptEnum;
import sporemodder.file.argscript.ArgScriptParser;
import sporemodder.file.argscript.ArgScriptStream;
import sporemodder.file.argscript.ArgScriptWriter;
import sporemodder.file.filestructures.StreamReader;
import sporemodder.file.filestructures.StreamWriter;

public class SummaryFile {
	
	public static final int TYPE_INT = 0x02E1A75D;
	public static final int TYPE_FLOAT = 0x02E1A7FF;
	
	public static final ArgScriptEnum ENUM_TYPE = new ArgScriptEnum();
	static {
		ENUM_TYPE.add(TYPE_INT, "int");
		ENUM_TYPE.add(TYPE_FLOAT, "float");
	}
	
	public static final ArgScriptEnum ENUM_PARAMETERS = new ArgScriptEnum();
	static {
		ENUM_PARAMETERS.add(0x2DD90AF, "type");
		ENUM_PARAMETERS.add(0x2DC9D1E, "subtype");
		ENUM_PARAMETERS.add(0x5B06E36, "cost");
		ENUM_PARAMETERS.add(0x2F05C60, "baseGear");
		ENUM_PARAMETERS.add(0x7358629A, "height");
		ENUM_PARAMETERS.add(0x2F05C58, "carnivore");
		ENUM_PARAMETERS.add(0x2F05C59, "herbivore");
		ENUM_PARAMETERS.add(0x2F05C5E, "cuteness");
		ENUM_PARAMETERS.add(0x2F05C5F, "totalSocial");
		ENUM_PARAMETERS.add(0x5B15AA5, "social");
		ENUM_PARAMETERS.add(0x2F05C61, "numGraspers");
		ENUM_PARAMETERS.add(0x3FEA210, "meanLookingScore");
		ENUM_PARAMETERS.add(0x3FEA1A0, "totalAttack");
		ENUM_PARAMETERS.add(0x5B15A92, "attack");
		ENUM_PARAMETERS.add(0x3FEA1C0, "numFeet");
		ENUM_PARAMETERS.add(0x4AB3BD8, "biteCapRange");
		ENUM_PARAMETERS.add(0x4AB3BD9, "strikeCapRange");
		ENUM_PARAMETERS.add(0x4AB3BDA, "chargeCapRange");
		ENUM_PARAMETERS.add(0x4AB3BDB, "spitCapRange");
		ENUM_PARAMETERS.add(0xF42136D5, "singCapRange");
		ENUM_PARAMETERS.add(0xF42136D6, "danceCapRange");
		ENUM_PARAMETERS.add(0xF42136D7, "charmCapRange");
		ENUM_PARAMETERS.add(0xF42136D8, "poseCapRange");
		ENUM_PARAMETERS.add(0x5AC2B96, "glideCapRange");
		ENUM_PARAMETERS.add(0x5AC2B97, "stealthCapRange");
		ENUM_PARAMETERS.add(0x5AC2B98, "sprintCapRange");
		ENUM_PARAMETERS.add(0x68DE3E8, "senseCapRange");
		ENUM_PARAMETERS.add(0x5AC2B99, "healthCapRange");
		
		ENUM_PARAMETERS.add(0x2E0FCC74, "editorModelSaveLastChild");
		ENUM_PARAMETERS.add(0x99524FAF, "editorModelForceSaveOver");
		ENUM_PARAMETERS.add(0xAE773A29, "editorModelCopyConsequence");
		ENUM_PARAMETERS.add(0xEC2942CD, "isMyCaptain");

		/// General
		ENUM_PARAMETERS.add(0x05fb4759, "published");
		ENUM_PARAMETERS.add(0x03cc89b1, "feedID");
		ENUM_PARAMETERS.add(0x05dc0b88, "contentSource");
		ENUM_PARAMETERS.add(0x061ef9b1, "timeDownloaded");
		ENUM_PARAMETERS.add(0xcd6e902c, "hasgaprop");
		ENUM_PARAMETERS.add(0x54a32960, "validation_playable");
		ENUM_PARAMETERS.add(0x54a32961, "validation_pollinatable");
		ENUM_PARAMETERS.add(0x0665f917, "validation_viewableLarge");
		ENUM_PARAMETERS.add(0x067b82d8, "validation_editable");
		ENUM_PARAMETERS.add(0x17a1c72d, "expansionPackage");

		/// Cell
		ENUM_PARAMETERS.add(0xdddd0000, "cellDangerous");
		ENUM_PARAMETERS.add(0xdddd0001, "cellSpeed");

		/// Creature
		ENUM_PARAMETERS.add(0x052def3f, "speciesArchetype");
		ENUM_PARAMETERS.add(0x052def4b, "speciesArchetypeQuery");

		/// Vehicle
		ENUM_PARAMETERS.add(0x0429d47d, "defense");
		ENUM_PARAMETERS.add(0x0429d47e, "speed");
		ENUM_PARAMETERS.add(0x0429d47c, "power");
		ENUM_PARAMETERS.add(0x05b950d7, "totalScore");
		ENUM_PARAMETERS.add(0x05c96ff0, "vehicleType");

		/// Vehicle/Building
		// F08 Build
		ENUM_PARAMETERS.add(0x05de94e6, "boundingBoxX");
		ENUM_PARAMETERS.add(0x05de94e7, "boundingBoxY");
		ENUM_PARAMETERS.add(0x05de94e8, "boundingBoxZ");
		ENUM_PARAMETERS.add(0x05de94e9, "boundingBoxTotal");

		// Release
		ENUM_PARAMETERS.add(0x05de94ea, "boundingBoxHeight"); 
		ENUM_PARAMETERS.add(0x05e62409, "boundingBoxTotal");

		// Adventure
		ENUM_PARAMETERS.add(0x15e8afc8, "completed");
		ENUM_PARAMETERS.add(0xb91fba14, "cannotEdit");
		ENUM_PARAMETERS.add(0xd22f5e35, "hasMissingAsset");
		ENUM_PARAMETERS.add(0x5a3584a7, "avatarLocked");
		ENUM_PARAMETERS.add(0xdb4675dd, "invalidVersion");

		//	version 3
		ENUM_PARAMETERS.add(0x0382b368, "authorLocalID");
	}
	
	public static final ArgScriptEnum ENUM_AMTAGS = new ArgScriptEnum();
	static {
		ENUM_AMTAGS.add(0x1F71E85, "BlockID");
		ENUM_AMTAGS.add(0x227F195, "AspectRatio");
		ENUM_AMTAGS.add(0x4EA801F, "EditorGroup");
		ENUM_AMTAGS.add(0x4EAB460, "GroupCount");
		ENUM_AMTAGS.add(0x4EBFEBD, "BlockTag");
		ENUM_AMTAGS.add(0x5833344, "TextureTag");
		ENUM_AMTAGS.add(0x518AF1D, "Creator");
		ENUM_AMTAGS.add(0x68375D7, "ACPSet");
		ENUM_AMTAGS.add(0x518B883, "Feed");
		ENUM_AMTAGS.add(0x52B27B5, "RegionColor");
		ENUM_AMTAGS.add(0x52C812A, "BaseColor");
		ENUM_AMTAGS.add(0x52C8CA5, "Texture");
	}

	public static final ArgScriptEnum ENUM_FEED_ID = new ArgScriptEnum();
	static {
		ENUM_FEED_ID.add(0x913b23be, "ShippingContent");
		ENUM_FEED_ID.add(0x5a3b873d, "LocalContent");
		ENUM_FEED_ID.add(0x058669b4, "PollinatedContent");
	}

	public static final ArgScriptEnum ENUM_CONTENTSOURCE = new ArgScriptEnum();
	static {
		ENUM_CONTENTSOURCE.add(0, "Assembled");
		ENUM_CONTENTSOURCE.add(1, "Matched");
		ENUM_CONTENTSOURCE.add(2, "Imported");
	}

	public static final ArgScriptEnum ENUM_VEHICLETYPES = new ArgScriptEnum();
	static {
		ENUM_VEHICLETYPES.add(0x05c972ee, "VehicleWater");
		ENUM_VEHICLETYPES.add(0x05c972ea, "VehicleLand");
		ENUM_VEHICLETYPES.add(0x05c972f1, "VehicleAir");
	}

	public static final ArgScriptEnum ENUM_BOOLEANS = new ArgScriptEnum();
	static {
		ENUM_BOOLEANS.add(0, "false");
		ENUM_BOOLEANS.add(1, "true");
	}
	
	public static class AMTag {
		public int tagGroup;
		public int tagID;
		public float value;
	}
	
	public final Map<Integer, Number> parameters = new LinkedHashMap<>();
	public final List<AMTag> amTags = new ArrayList<>();

	// version 3
	public int version;
	public final ResourceKey key = new ResourceKey(); 
	
	public void read(StreamReader stream) throws IOException {
		version = stream.readInt();
		if (version != 4 && version != 3)
			throw new IOException("Unsupported .summary version " + version);
		
		if (version == 3) {
			key.setTypeID(stream.readInt());
			key.setGroupID(stream.readInt());
			key.setInstanceID(stream.readInt());
		}

		int count = stream.readInt();
		for (int i = 0; i < count; i++) {
			int id = stream.readInt();
			int type = stream.readInt();
			
			if (type == TYPE_INT)
			{
				parameters.put(id, stream.readInt());
			}
			else if (type == TYPE_FLOAT)
			{
				parameters.put(id, stream.readFloat());
			}
			else {
				throw new IOException("Unknown param type " + HashManager.get().getFileName(type));
			}
		}
		
		count = stream.readInt();
		for (int i = 0; i < count; i++) {
			AMTag tag = new AMTag();
			tag.tagGroup = stream.readInt();
			tag.tagID = stream.readInt();
			tag.value = stream.readFloat();
			amTags.add(tag);
		}
	}
	
	public void write(StreamWriter stream) throws IOException {
		stream.writeInt(4);
		stream.writeInt(parameters.size());
		for (Map.Entry<Integer, Number> entry : parameters.entrySet()) {
			stream.writeInt(entry.getKey());
			if (entry.getValue() instanceof Integer) {
				stream.writeInt(TYPE_INT);
				stream.writeInt(entry.getValue().intValue());
			}
			else {
				stream.writeInt(TYPE_FLOAT);
				stream.writeFloat(entry.getValue().floatValue());
			}
		}
		stream.writeInt(amTags.size());
		for (AMTag tag : amTags) {
			stream.writeInt(tag.tagGroup);
			stream.writeInt(tag.tagID);
			stream.writeFloat(tag.value);
		}
	}
	
	public ArgScriptWriter toArgScript() {
		ArgScriptWriter writer = new ArgScriptWriter();
		toArgScript(writer);
		return writer;
	}

	public String GetEnumString(Number value, String parameter) {
		switch (parameter) {
			case "feedID":		return ENUM_FEED_ID.get(value.intValue());
			case "contentSource":	return ENUM_CONTENTSOURCE.get(value.intValue());
			case "vehicleType":		return ENUM_VEHICLETYPES.get(value.intValue());
			case "published":
			case "hasgaprop":
			case "validation_playable":
			case "validation_pollinatable":
			case "validation_viewableLarge":
			case "validation_editable":
			case "isMyCaptain":
			case "completed":
			case "cannotEdit":
			case "hasMissingAsset":
			case "avatarLocked":
			case "invalidVersion":
			return ENUM_BOOLEANS.get(value.intValue());
			default:				return null;
		}
	}
	
	public Integer GetEnumValue(ArgScriptArguments args, int id) {
		if (ENUM_PARAMETERS.get(id) == null) return null;
		switch (ENUM_PARAMETERS.get(id)) {
			case "feedID":		return ENUM_FEED_ID.get(args.get(2));
			case "contentSource":	return ENUM_CONTENTSOURCE.get(args.get(2));
			case "vehicleType":		return ENUM_VEHICLETYPES.get(args.get(2));
			case "published":
			case "hasgaprop":
			case "validation_playable":
			case "validation_pollinatable":
			case "validation_viewableLarge":
			case "validation_editable":
			case "isMyCaptain":
			case "completed":
			case "cannotEdit":
			case "hasMissingAsset":
			case "avatarLocked":
			case "invalidVersion":
			return ENUM_BOOLEANS.get(args.get(2));
			default:  				return null;
		}
	}
	
	public void toArgScript(ArgScriptWriter writer) {
		if (version == 3)
			writer.command("resourcekey").arguments(key.toString());

		for (Map.Entry<Integer, Number> entry : parameters.entrySet()) {
			String parameterString = ENUM_PARAMETERS.get(entry.getKey());
			if (parameterString == null) {
				parameterString = HashManager.get().hexToString(entry.getKey());
			}

			String valueString = GetEnumString(entry.getValue(), parameterString);
			if (valueString == null) {
				if (entry.getValue() instanceof Integer) {
				valueString = HashManager.get().formatInt32(entry.getValue().intValue());
				}
				else {
					valueString = entry.getValue().toString();
				}
			}
			writer.command("parameter").arguments(
					ENUM_TYPE.get(entry.getValue() instanceof Integer ? TYPE_INT : TYPE_FLOAT),
					parameterString, 
					valueString);
		}
		writer.blankLine();
		for (AMTag tag : amTags) {
			String tagString = ENUM_AMTAGS.get(tag.tagGroup);
			if (tagString == null) {
				tagString = HashManager.get().getFileName(tag.tagGroup);
			}
			writer.command("amTag").arguments(
					tagString,
					HashManager.get().getFileName(tag.tagID),
					tag.value
					);
		}
	}
	
	public void clear() {
		parameters.clear();
		amTags.clear();
	}
	
	public ArgScriptStream<SummaryFile> generateStream() {
		ArgScriptStream<SummaryFile> stream = new ArgScriptStream<>();
		stream.setData(this);
		stream.addDefaultParsers();
		
		stream.addParser("resourcekey", ArgScriptParser.create((parser, line) -> {
			final ArgScriptArguments args = new ArgScriptArguments();
			if (line.getArguments(args, 1)) {
				String[] originals = new String[3];
				key.parse(args, 0, originals);
			}
		}));
		
		stream.addParser("parameter", ArgScriptParser.create((parser, line) -> {
			final ArgScriptArguments args = new ArgScriptArguments();
			if (line.getArguments(args, 3)) {
				
				int id;
				if (args.get(1).charAt(0) == '0') {
					id = Optional.ofNullable(stream.parseFileID(args, 1)).orElse(0);
				}
				else {
					id = ENUM_PARAMETERS.get(args, 1);
				}
				
				Number value;
				int type = ENUM_TYPE.get(args, 0);
				if (type == TYPE_INT) {
					Integer enumValue = GetEnumValue(args, id);
					value = enumValue != null ? enumValue : stream.parseInt(args, 2);
				}
				else {
					value = stream.parseFloat(args, 2);
				}
				
				parameters.put(id, value);
			}
		}));
		
		stream.addParser("amTag", ArgScriptParser.create((parser, line) -> {
			final ArgScriptArguments args = new ArgScriptArguments();
			if (line.getArguments(args, 3)) {
				AMTag tag = new AMTag();

				if (args.get(0).charAt(0) == '0') {
					tag.tagGroup = Optional.ofNullable(stream.parseFileID(args, 0)).orElse(0);
				}
				else {
					tag.tagGroup = ENUM_AMTAGS.get(args, 0);
				}
				
				tag.tagID = Optional.ofNullable(stream.parseFileID(args, 1)).orElse(0);
				tag.value = Optional.ofNullable(stream.parseFloat(args, 2)).orElse(0f);
				
				amTags.add(tag);
			}
		}));
		
		return stream;
	}
}
