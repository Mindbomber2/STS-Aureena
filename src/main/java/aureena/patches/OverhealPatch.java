package aureena.patches;

import aureena.characters.AureenaMinion;
import aureena.interfaces.OnCardUseListener;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

//My Stuff with some Allison
public class OverhealPatch {
    @SpirePatch(
            clz= HealAction.class,
            method ="update"
            )
    public static class OnUseCardPatch
    {
        @SpireInsertPatch(
                rloc=1
        )
        public static SpireReturn<?> updateReader(HealAction __instance)
        {
            if (__instance.target==AbstractDungeon.player){
                if(__instance.amount+__instance.target.currentHealth>__instance.target.maxHealth) {
                    int overheal =__instance.amount+__instance.target.currentHealth-__instance.target.maxHealth;
                    if (__instance.target instanceof AbstractPlayerWithMinions) {
                        AbstractPlayerWithMinions temp = (AbstractPlayerWithMinions) (__instance.target);
                        AbstractFriendlyMonster m = temp.getMinion("aureena:Aureena");
                        if (m instanceof AureenaMinion) {
                            AbstractDungeon.actionManager.addToBottom(new HealAction(m, __instance.source, overheal));
                            __instance.amount-=overheal;
                        }
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
