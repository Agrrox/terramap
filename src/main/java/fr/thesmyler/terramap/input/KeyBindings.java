package fr.thesmyler.terramap.input;

import org.lwjgl.input.Keyboard;

import fr.thesmyler.terramap.TerramapRemote;
import fr.thesmyler.terramap.gui.TerramapScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public abstract class KeyBindings {

	private static final String KEY_CATEGORY = "terramap.binding.category";
	
	public static final KeyBinding OPEN_MAP = new KeyBinding("terramap.binding.open_map", Keyboard.KEY_M, KeyBindings.KEY_CATEGORY);
	public static final KeyBinding TOGGLE_DEBUG = new KeyBinding("terramap.binding.toggle_debug", Keyboard.KEY_P, KeyBindings.KEY_CATEGORY);
	
	//TODO Localize
	public static final KeyBinding MAP_SHORTCUT = new KeyBinding("Map shortcuts", Keyboard.KEY_LCONTROL, KeyBindings.KEY_CATEGORY);
	
	private static final IKeyConflictContext TERRAMAP_SCREEN_CONTEXT = new IKeyConflictContext() {
		@Override
		public boolean isActive() {
			return  Minecraft.getMinecraft().currentScreen instanceof TerramapScreen;
		}
		@Override
		public boolean conflicts(IKeyConflictContext other) {
			return other.equals(this);
		}
	};
	
	public static void registerBindings() {
		ClientRegistry.registerKeyBinding(OPEN_MAP);
		ClientRegistry.registerKeyBinding(TOGGLE_DEBUG);
		MAP_SHORTCUT.setKeyConflictContext(TERRAMAP_SCREEN_CONTEXT);
		ClientRegistry.registerKeyBinding(MAP_SHORTCUT);
	}
	
	public static void checkBindings() {
		if(OPEN_MAP.isPressed()
				  && Minecraft.getMinecraft().world != null
				  && Minecraft.getMinecraft().player.dimension == 0) { //TODO Follow Sledgehammer preferences
        Minecraft.getMinecraft().displayGuiScreen(new TerramapScreen(Minecraft.getMinecraft().currentScreen, TerramapRemote.getRemote().getMapStyles()));
    }
	}

}
