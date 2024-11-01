/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.spells;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HolyTome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

import java.util.ArrayList;

public abstract class ClericSpell {

	public abstract void onCast(HolyTome tome, Hero hero);

	public float chargeUse( Hero hero ){
		return 1;
	}

	public String name(){
		return Messages.get(this, "name");
	}

	public String shortDesc(){
		return Messages.get(this, "short_desc") + " " + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
	}

	public String desc(){
		return Messages.get(this, "desc") + "\n\n" + Messages.get(this, "charge_cost", (int)chargeUse(Dungeon.hero));
	}

	public int icon(){
		return HeroIcon.NONE;
	}

	public void onSpellCast(HolyTome tome, Hero hero){
		Invisibility.dispel();
		if (hero.hasTalent(Talent.SATIATED_SPELLS) && hero.buff(Talent.SatiatedSpellsTracker.class) != null){
			Buff.affect(hero, Barrier.class).setShield(1 + 2*hero.pointsInTalent(Talent.SATIATED_SPELLS));
			hero.buff(Talent.SatiatedSpellsTracker.class).detach();
		}
		tome.spendCharge(chargeUse(hero));
	}

	public static ArrayList<ClericSpell> getSpellList(Hero cleric, int tier){
		ArrayList<ClericSpell> spells = new ArrayList<>();

		if (tier == 1) {

			spells.add(GuidingLight.INSTANCE);
			spells.add(HolyWeapon.INSTANCE);
			spells.add(HolyWard.INSTANCE);

			if (cleric.hasTalent(Talent.SHIELD_OF_LIGHT)) {
				spells.add(ShieldOfLight.INSTANCE);
			}

			if (cleric.hasTalent(Talent.DETECT_CURSE)) {
				spells.add(DetectCurse.INSTANCE);
			}

		} else if (tier == 2) {

			if (cleric.hasTalent(Talent.SUNRAY)){
				spells.add(Sunray.INSTANCE);
			}

			if (cleric.hasTalent(Talent.DIVINE_SENSE)) {
				spells.add(DivineSense.INSTANCE);
			}

		}

		return spells;
	};

}
