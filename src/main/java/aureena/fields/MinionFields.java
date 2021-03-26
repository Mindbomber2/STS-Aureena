package aureena.fields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = SpirePatch.CLASS
)
public class MinionFields {
    public static SpireField<ArrayList<AbstractFriendlyMonster>> targettingMinions = new SpireField<>(ArrayList::new);
    public static SpireField<AbstractFriendlyMonster> targetMinion = new SpireField<>(() -> null);
}
