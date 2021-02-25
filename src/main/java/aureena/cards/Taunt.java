package aureena.cards;

import aureena.AureenaMod;
import aureena.characters.Aureena;
import aureena.powers.TauntPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static aureena.AureenaMod.makeCardPath;

public class Taunt extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = AureenaMod.makeID(Taunt.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = Aureena.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADE_REDUCED_COST = 1;
    private static final int AMOUNT = 1;


    public Taunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = AMOUNT;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(m, p, new TauntPower(m, p, magicNumber), magicNumber));
        }else{
            for(AbstractMonster aM : AbstractDungeon.getCurrRoom().monsters.monsters){
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(aM, p, new TauntPower(aM, p, magicNumber), magicNumber));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_REDUCED_COST);
            this.target = CardTarget.ALL_ENEMY;
            initializeDescription();
        }
    }
}
