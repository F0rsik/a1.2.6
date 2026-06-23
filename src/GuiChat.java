package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiChat extends GuiScreen {
	private String message = "";
	private int updateCounter = 0;
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		++this.updateCounter;
	}

	protected void keyTyped(char var1, int var2) {
		if(var2 == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
		} else if(var2 == 28) {
			String var3 = this.message.trim();
			if(var3.length() > 0) {
				if(!var3.startsWith(".")){
					this.mc.thePlayer.sendChatMessage(this.message.trim());
				}
				else if(var3.startsWith(".give")){
					try{
						String[] s = var3.split(" ");
						if(s.length < 3){
							this.mc.thePlayer.inventory.addItemsToInventory(Integer.parseInt(s[1]), 1);
						}
						else{
							this.mc.thePlayer.inventory.addItemsToInventory(Integer.parseInt(s[1]), Integer.parseInt(s[2]));
						}
					}
					catch(Exception e){
						this.mc.ingameGUI.addChatMessage(".give usage: .give <item or block id> <amount> or .give <item or block id>");
					}
				}
				else if(var3.startsWith(".tp")){
					try{
						String[] s = var3.split(" ");
						double x = Double.parseDouble(s[1]);
						double y = Double.parseDouble(s[2]);
						double z = Double.parseDouble(s[3]);
						((EntityClientPlayerMP)this.mc.thePlayer).field_797_bg.addToSendQueue(new Packet11PlayerPosition(x,y,y,z,true));
					}
					catch(Exception e){
						this.mc.ingameGUI.addChatMessage(".tp usage: .tp <x> <y> <z>");
					}
				}
				else if(var3.startsWith(".bind") && !var3.startsWith(".binds")){
					try{
						String[] hacks = {"antiknockback", "fullbright", "jumpboost", "nofall", "fly", "fastfly", "xray", "killaura", "jesus", "instamine", "coords"};
						int[] hack_ids = {10,11,12,13,14,15,16,17,18,19, 20};
						String[] s = var3.split(" ");
						String hack = s[1].toLowerCase();
						int i;
						for(i = 0; i < 10 && !hacks[i].startsWith(hack); ++i);
						if(hacks[i].startsWith(hack)){
							this.mc.gameSettings.setKeyBinding(hack_ids[i], Keyboard.getKeyIndex(s[2].toUpperCase()));
							this.mc.ingameGUI.addChatMessage(hack + " binded to " + s[2].toUpperCase());
						}
						else{
							this.mc.ingameGUI.addChatMessage(hack + ": hack not found");
						}
					}
					catch(Exception e){
						this.mc.ingameGUI.addChatMessage(".bind usage: .bind <hack name> <key name>");
					}
				}
				else if(var3.startsWith(".binds")){
					int[] hack_ids = {10,11,12,13,14,15,16,17,18,19, 20};
					for(int i = 0; i < 11; ++i){
						this.mc.ingameGUI.addChatMessage(this.mc.gameSettings.getKeyBinding(hack_ids[i]));
					}
				}
				else if(var3.startsWith(".help")){
					this.mc.ingameGUI.addChatMessage("List of commands:");
					this.mc.ingameGUI.addChatMessage(".bind - Bind a hack to a provided key. Usage: .bind <hack name> <key name>");
					this.mc.ingameGUI.addChatMessage(".binds - Display the list of hacks and their key bindings. Usage: .binds");
					this.mc.ingameGUI.addChatMessage(".tp - Teleports you to the provided coordinates. Usage: .tp <x> <y> <z>");
					this.mc.ingameGUI.addChatMessage(".give - Gives you a specified amount of items with the provided id. Usage: .give <id> <amount> or .give <id>");
					this.mc.ingameGUI.addChatMessage(".help - Display the list of commands.");
				}
			}

			this.mc.displayGuiScreen((GuiScreen)null);
		} else {
			if(var2 == 14 && this.message.length() > 0) {
				this.message = this.message.substring(0, this.message.length() - 1);
			}

			if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(var1) >= 0 && this.message.length() < 100) {
				this.message = this.message + var1;
			}

		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
		this.drawString(this.fontRenderer, "> " + this.message + (this.updateCounter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 14737632);
	}

	protected void mouseClicked(int var1, int var2, int var3) {
		if(var3 == 0 && this.mc.ingameGUI.field_933_a != null) {
			if(this.message.length() > 0 && !this.message.endsWith(" ")) {
				this.message = this.message + " ";
			}

			this.message = this.message + this.mc.ingameGUI.field_933_a;
			byte var4 = 100;
			if(this.message.length() > var4) {
				this.message = this.message.substring(0, var4);
			}
		}

	}
}
