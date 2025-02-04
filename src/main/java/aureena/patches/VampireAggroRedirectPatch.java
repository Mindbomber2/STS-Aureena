package aureena.patches;

import aureena.characters.AureenaMinion;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import java.util.ArrayList;

//Allison Stuff
public class VampireAggroRedirectPatch {
    @SpirePatch(
            clz = VampireDamageAction.class,
            method = "update"

    )
    public static class VampireDamageActionRetarget {
        @SpireInsertPatch(locator = VampireDamageActionRetarget.Locator.class, localvars = {"info"})
        public static void ChangeTarget(VampireDamageAction __instance, @ByRef DamageInfo[] info) {
            if (__instance.source != null) {
                AbstractPlayer p = AbstractDungeon.player;
                if (p instanceof AbstractPlayerWithMinions){
                    AbstractPlayerWithMinions temp = (AbstractPlayerWithMinions) (p);
                    AbstractFriendlyMonster m = temp.getMinion("aureena:Aureena");
                    if(m instanceof AureenaMinion){
                        AureenaMinion aureena = (AureenaMinion) (m);
                        if (aureena != __instance.source && p != __instance.source && !__instance.source.hasPower("aureena:TauntPower")) {
                            __instance.target = aureena;
                        }
                    }
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
