package aureena.patches;

import aureena.interfaces.OnCardUseListener;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;

public class OnCardUsePatch {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="useCard"
    )
    public static class OnUseCardPatch
    {
        @SpirePrefixPatch
        public static SpireReturn<?> useReader(AbstractPlayer __instance, AbstractCard card, AbstractMonster monster, int energyOnUse)
        {
            if (__instance instanceof AbstractPlayerWithMinions) {
                AbstractPlayerWithMinions temp = (AbstractPlayerWithMinions)__instance;
                for (AbstractMonster aM : temp.getMinions().monsters) {
                    if (aM instanceof OnCardUseListener) {
                        ((OnCardUseListener) aM).onCardUsed(card, monster);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}