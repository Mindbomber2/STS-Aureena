package aureena.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface OnCardUseListener {
    void onCardUsed(AbstractCard c, AbstractCreature target);
}