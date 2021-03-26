package aureena.abstracts;

import aureena.cards.AbstractDefaultCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import aureena.fields.CardMinionTargetField;
import aureena.fields.MinionFields;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;


import java.util.ArrayList;

import static aureena.AureenaMod.makeID;

public abstract class AbstractMinionTargetCard extends AbstractDefaultCard {

    public AbstractMinionTargetCard(final String id,
                                    final String name,
                                    final String img,
                                    final int cost,
                                    final String rawDescription,
                                    final CardType type,
                                    final CardColor color,
                                    final CardRarity rarity,
                                    final CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (MinionFields.targettingMinions.get(p).isEmpty())
        {
            this.cantUseMessage = "MinionFields.targettingMinions.get(p).isEmpty";
            return false;
        }
        return super.canUse(p, m);
    }

    protected AbstractFriendlyMonster getTargetMinion()
    {
        AbstractFriendlyMonster m = CardMinionTargetField.targetMinion.get(this);
        ArrayList<AbstractFriendlyMonster> minions = MinionFields.targettingMinions.get(AbstractDungeon.player);

        if (m == null || !minions.contains(m))
        {
            if (minions.isEmpty())
            {
                System.out.println("wtf using minion target card with no minions?");
                return null;
            }
            else if (minions.size() == 1)
            {
                return minions.get(0);
            }
            return minions.get(AbstractDungeon.cardRandomRng.random(0, minions.size() - 1));
        }
        return m;
    }
    protected AbstractFriendlyMonster getDirectHoverMinion()
    {
        AbstractFriendlyMonster m = CardMinionTargetField.targetMinion.get(this);
        ArrayList<AbstractFriendlyMonster> minions = MinionFields.targettingMinions.get(AbstractDungeon.player);

        if (m == null || !minions.contains(m))
        {
            if (minions.isEmpty())
            {
                System.out.println("wtf using minion target card with no minions?");
            }
            return null;
        }
        return m;
    }
}
