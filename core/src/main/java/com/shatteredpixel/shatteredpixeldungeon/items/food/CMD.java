/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.Key;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Game;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CMD extends Food {

	{
		image = ItemSpriteSheet.PHANTOM_MEAT;
		energy = Hunger.STARVING;
		defaultAction = AC_CMD;

	}

	@Override
	protected void satisfy(Hero hero) {
		super.satisfy(hero);
		effect(hero);
	}

	public int value() {
		return 30 * quantity;
	}

	private static void execCommand(String text){
		if(text.startsWith("/give ")) {
			try {
				String[] cmds = text.replace("/give ", "").split(" ");
				if (cmds.length == 3) {
					giveItem(cmds[0], Integer.parseInt(cmds[1]), Integer.parseInt(cmds[2]));
				} else if (cmds.length == 2) {
					giveItem(cmds[0], Integer.parseInt(cmds[1]));
				} else if (cmds.length == 1) {
					giveItem(cmds[0]);
				} else {
					GLog.i("(형식오류) /give 아이템경로 수량 강화수치");
				}
			} catch (NumberFormatException e) {
				GLog.i("(오류) 수량이랑 강화수치는 정수 짜식아, 정수!!!!!!");
			}
		}else if(text.startsWith("/key ")){
			try {
				String[] cmds = text.replace("/key ", "").split(" ");
				if (cmds.length > 0) {
					String type = cmds[0];
					int floor = Dungeon.depth;
					if (cmds.length==2){
						floor = Integer.parseInt(cmds[1]);
					}
					Key key = new IronKey(floor);
					if(type.equals("GoldenKey")||type.equals("황금열쇠")){
						key = new GoldenKey(floor);
					}else if(type.equals("CrystalKey")||type.equals("수정열쇠")){
						key = new CrystalKey(floor);
					}else if(type.equals("SkeletonKey")||type.equals("해골열쇠")){
						key = new SkeletonKey(floor);
					}
					if (key.doPickUp( Dungeon.hero )) {
						GLog.i( Messages.capitalize(Messages.get(Dungeon.hero, "you_now_have", key.name()) ));
					} else {
						Dungeon.level.drop( key, Dungeon.hero.pos ).sprite.drop();
					}
				} else {
					GLog.i("(형식오류) /key 열쇠종류 층");
				}
			} catch (NumberFormatException e) {
				GLog.i("(오류) 층수는 자연수 짜식아, 자연수!!!!!!");
			}
		}else if(text.startsWith("/floor ")){
			String floor = text.replace("/floor ","");
			int depth = Dungeon.depth;
			try{
				if(floor.contains("+")){
					depth += Integer.parseInt(floor);
				}else if(floor.contains("-")){
					depth -= Integer.parseInt(floor);
				}else{
					depth = Integer.parseInt(floor);
				}
				if(depth<=Dungeon.depth){
					Level.beforeTransition();
					InterlevelScene.mode = InterlevelScene.Mode.RETURN;
					InterlevelScene.returnDepth = depth;
					InterlevelScene.returnPos = -1;
					Game.switchScene( InterlevelScene.class );
				}else{
					for(int i=0;i<depth-Dungeon.depth;i++){
						Level.beforeTransition();
						InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
						Game.switchScene(InterlevelScene.class);
					}
				}
				GLog.i(depth+"층으로~~");
			} catch (NumberFormatException e) {
				GLog.i("(오류) 층수는 정수 짜식아, 정수!!!!!!");
			}
		}else if(text.startsWith("/summon ")){
			summonMob(text.replace("/summon ", ""));
		}else if(text.startsWith("/log ")){
			String[] cmds = text.replace("/log ", "").split(" ");
			if(cmds.length==1){
				GLog.i(cmds[0]);
			}else if(cmds.length==2){
				try {
					Method log = GLog.class.getMethod(cmds[0],String.class);
					log.invoke(new GLog(), cmds[1]);
				}catch (NoSuchMethodException e) {
					GLog.i("(오류) 그런건 업서요");
				} catch (InvocationTargetException e) {
					GLog.i("(오류) InvocationTargetException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
				} catch (IllegalAccessException e) {
					GLog.i("(오류) IllegalAccessException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
				}
			}else{

			}
		}
	}

	private static void summonMob(String mobName){
		try{
			Class cls = Class.forName("com.shatteredpixel.shatteredpixeldungeon.actors.mobs."+mobName);
			Mob mob = (Mob) cls.getDeclaredConstructor().newInstance();
			ArrayList<Integer> spawnPoints = new ArrayList<>();
			for (int c : PathFinder.NEIGHBOURS8) {
				int p = Dungeon.hero.pos + c;
				if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
					spawnPoints.add(p);
				}
			}
			if (!spawnPoints.isEmpty()){
				mob.pos = Random.element(spawnPoints);
				GameScene.add(mob);
			} else {
				GLog.w("주변 8칸에 소환할 곳이 업서요ㅠㅠ");
			}
		} catch (ClassNotFoundException e) {
			GLog.i("(오류) 그런 몹은 업서요");
		} catch (InvocationTargetException e) {
			GLog.i("(오류) InvocationTargetException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		} catch (InstantiationException e) {
			GLog.i("(오류) InstantiationException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		} catch (IllegalAccessException e) {
			GLog.i("(오류) IllegalAccessException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		} catch (NoSuchMethodException e) {
			GLog.i("(오류) NoSuchMethodException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		}
	}

	private static void giveItem(String itemName){
		giveItem(itemName, 1, 0);
	}
	private static void giveItem(String itemName, int amount){
		giveItem(itemName, amount, 0);
	}
	private static void giveItem(String itemName, int amount, int upgrade){

		try {
			Class cls;
			if(itemName.startsWith("plants")){
				cls = Class.forName("com.shatteredpixel.shatteredpixeldungeon."+itemName);
			}else{
				cls = Class.forName("com.shatteredpixel.shatteredpixeldungeon.items."+itemName);
			}
			Item.class.getClasses();
			Item item = (Item) cls.getDeclaredConstructor().newInstance();
			item.identify();
			item.quantity(amount);
			if(item.isUpgradable()){
				item.level(0);
				item.upgrade(upgrade);
			}
			if (item.doPickUp( Dungeon.hero )) {
				GLog.i( Messages.capitalize(Messages.get(Dungeon.hero, "you_now_have", item.name()) ));
			} else {
				Dungeon.level.drop( item, Dungeon.hero.pos ).sprite.drop();
			}
			CMD cmd = new CMD();
			if (cmd.doPickUp( Dungeon.hero )) {

			} else {
				Dungeon.level.drop( cmd, Dungeon.hero.pos ).sprite.drop();
			}
		} catch (ClassNotFoundException e) {
			GLog.i("(오류) 그런 아이템은 업서요");
		} catch (InvocationTargetException e) {
			GLog.i("(오류) InvocationTargetException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		} catch (InstantiationException e) {
			GLog.i("(오류) InstantiationException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		} catch (IllegalAccessException e) {
			GLog.i("(오류) IllegalAccessException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		} catch (NoSuchMethodException e) {
			GLog.i("(오류) NoSuchMethodException이라는데 나도 뭔지 모름. 뭔짓을 한거임.");
		}
	}

	public static void effect(Hero hero){
		ShatteredPixelDungeon.scene().addToFront(new WndTextInput("COMMAND",
				"명령어를 입력하세요!",
				"",
				1000,
				true,
				"EXECUTE!!!",
				"취소"){
			@Override
			public void onSelect(boolean positive, String text) {
				if (positive && text != "") {
					execCommand(text);
				}else{
					GLog.w("멍청한 자식..");
				}
			}
		});
		PotionOfHealing.cure(hero);

	}


}
