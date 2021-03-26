package aureena.fields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class CardMinionTargetField {
    public static SpireField<AbstractFriendlyMonster> targetMinion = new SpireField<>(() -> null);
}