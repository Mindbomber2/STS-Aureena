package aureena.characters;

import aureena.interfaces.OnCardUseListener;
import aureena.util.TextureLoader;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class AureenaMinion extends AbstractFriendlyMonster implements OnCardUseListener {

    public static final Logger logger = LogManager.getLogger(AureenaMinion.class.getName());

    public static String NAME = "Aureena";
    public static String ID = "aureena:Aureena";
    private static final int[] damageValues = {4,2,6,3,9,3,13,7,6,12};
    private static final int[] damageTimesValues = {1,2,1,2,1,3,1,2,3,2};
    private static final int[] vigorBuffValues = {1,2,0,0,0,0,2,5,0,0};
    private static final int[] tempStrengthBuffValues = {0,0,1,2,0,0,0,0,0,0};
    private static final int[] strengthBuffValues = {0,0,0,0,1,2,2,2,2,3};
    private static final int[] doubleTapBuffValues = {0,0,0,0,0,0,0,0,1,1};
    private static final int[] thornsBuffValues = {1,2,0,0,0,0,2,4,4,4};
    private static final int[] tempDexterityBuffValues = {0,0,1,2,0,0,0,0,0,0};
    private static final int[] dexterityBuffValues = {0,0,0,0,1,2,2,2,2,3};
    private static final int[] artifactBuffValues = {0,0,0,0,0,0,0,0,1,1};
    private static final int[] blockValues = {3,2,6,4,11,7,18,11,13,16};
    private static final int[] blockTimesValues = {1,2,1,2,1,2,1,2,2,2};
    private static final int[] strengthDebuffValues = {1,1,2,0,0,0,2,3,4,5};
    private static final int[] frailDebuffValues = {0,1,1,0,1,2,2,3,4,5};
    private static final int[] weakenDebuffValues = {0,0,0,2,2,2,2,3,4,5};
    private static final int[] healValues = {2,3,5,7,9,11,13,16,19,23};
    private Intents intent;
    private final AbstractPlayer p = AbstractDungeon.player;
    private AbstractMonster lastTargeted = null;
    private static int numberOfElites = 0;
    private boolean isCharged=false;

    public AureenaMinion(int offsetX, int offsetY) {
        super(NAME, ID, 50, -8.0F, 10.0F, 230.0F, 240.0F, "aureenaResources/images/char/defaultCharacter/bladecraftOri.jpg", -850F, 300);
        //loadAnimation("images/monsters/theBottom/slimeS/skeleton.atlas", "images/monsters/theBottom/slimeS/skeleton.json", 1.0F);
        //AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //e.setTime(e.getEndTime() * MathUtils.random());
        //this.state.addListener(new SlimeAnimListener());
        addMoves();
        intent = Intents.Default;
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();

        setNewMove();
        this.intent= Intents.Default;
    }

    private void setNewMove() {
        switch (intent) {
            case Default:
                if(getLastTargeted()==null){
                    setMove(0);
                    break;
                }
                if(isAttackIntent(getLastTargeted().intent)){
                    setMove(0);
                } else {
                    setMove(1);
                }
                break;
            case Aggrevated:
                if (!isCharged) {
                    setMove(2);
                } else {
                    setMove(3);
                }
                break;
            case Passified:
                if(getLastTargeted()==null){
                    setMove(4);
                    break;
                }
                if(isAttackIntent(getLastTargeted().intent)){
                    setMove(4);
                } else {
                    setMove(5);
                }
                break;
        }
    }

    public void setAggrevated(){
        this.intent=Intents.Aggrevated;
    }

    public void setPassified(){
        this.intent=Intents.Passified;
    }

    public void setCharged(boolean v){
        this.isCharged=v;
    }

    public void onCardUsed(AbstractCard c, AbstractCreature target){
        if(c.target== AbstractCard.CardTarget.ENEMY){
            lastTargeted=(AbstractMonster) target;
        }
    }
    public AbstractMonster getLastTargeted(){
        if(lastTargeted==null) return null;
        if(lastTargeted.isDeadOrEscaped()){
            lastTargeted=null;
        }
        return lastTargeted;
    }

    public void setLastTargeted(AbstractMonster m){
        this.lastTargeted=m;
        setNewMove();
    }

    public static void elitesSlain(int number){
        numberOfElites+=number;
    }

    public void setMove(int type){
        this.setMove(type, numberOfElites);
    }

    public void setMove(int type, int level){
        clearMoves();
        switch (type) {
            case 0: //Defensive Buff
                int thornsValue = thornsBuffValues[level];
                int tempDexterityValue = tempDexterityBuffValues[level];
                int dexterityValue = dexterityBuffValues[level];
                int artifactValue = artifactBuffValues[level];
                StringBuilder defBuffDescription = new StringBuilder("Gain:");
                if (thornsValue > 0 ){
                    defBuffDescription.append(" NL ");
                    defBuffDescription.append(thornsValue);
                    defBuffDescription.append(" Thorns.");
                }
                if (tempDexterityValue > 0 ){
                    defBuffDescription.append(" NL ");
                    defBuffDescription.append(tempDexterityValue);
                    defBuffDescription.append(" temporary Dexterity.");
                }
                if (dexterityValue > 0 ){
                    defBuffDescription.append(" NL ");
                    defBuffDescription.append(dexterityValue);
                    defBuffDescription.append(" Dexterity.");
                }
                if (artifactValue > 0 ){
                    defBuffDescription.append(" NL ");
                defBuffDescription.append(artifactValue);
                defBuffDescription.append(" Artifact.");
            }

                this.moves.addMove(
                        new MinionMove(
                                "DefensiveBuff"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/defendBuff.png")
                                , defBuffDescription.toString()
                                , () -> {
                                    if(thornsValue>0){
                                        this.addToBot(new ApplyPowerAction(p, this, new ThornsPower(p, thornsValue), thornsValue));
                                    }
                                    if(tempDexterityValue>0){
                                        this.addToBot(new ApplyPowerAction(p, this, new DexterityPower(p, tempDexterityValue), tempDexterityValue));
                                        this.addToBot(new ApplyPowerAction(p, this, new LoseDexterityPower(p, tempDexterityValue), tempDexterityValue));
                                    }
                                    if(dexterityValue>0){
                                        this.addToBot(new ApplyPowerAction(p, this, new DexterityPower(p, dexterityValue), dexterityValue));
                                    }
                                    if(artifactValue>0){
                                        this.addToBot(new ApplyPowerAction(p, this, new ArtifactPower(p, artifactValue), artifactValue));
                                    }

                            this.setTakenTurn(false);
                        }
                        )
                );

                break;
            case 1: //Offensive Buff
                int vigorValue = vigorBuffValues[level];
                int tempStrengthValue = tempStrengthBuffValues[level];
                int strengthValue = strengthBuffValues[level];
                int doubleTapValue = doubleTapBuffValues[level];
                StringBuilder offBuffDescription = new StringBuilder("Gain:");
                if (vigorValue > 0 ){
                    offBuffDescription.append(" NL ");
                    offBuffDescription.append(vigorValue);
                    offBuffDescription.append(" Vigor.");
                }
                if (tempStrengthValue > 0 ){
                    offBuffDescription.append(" NL ");
                    offBuffDescription.append(tempStrengthValue);
                    offBuffDescription.append(" temporary Strength.");
                }
                if (strengthValue > 0 ){
                    offBuffDescription.append(" NL ");
                    offBuffDescription.append(strengthValue);
                    offBuffDescription.append(" Strength.");
                }
                if (doubleTapValue > 0 ) {
                    offBuffDescription.append(" NL ");
                    offBuffDescription.append("Your next Attack hits twice.");
                }
                this.moves.addMove(
                        new MinionMove(
                                "OffensiveBuff"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/attackBuff.png")
                                , offBuffDescription.toString()
                                , () -> {
                            if(vigorValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new VigorPower(p, vigorValue), vigorValue));
                            }
                            if(tempStrengthValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new StrengthPower(p, tempStrengthValue), tempStrengthValue));
                                this.addToBot(new ApplyPowerAction(p, this, new LoseStrengthPower(p, tempStrengthValue), tempStrengthValue));
                            }
                            if(strengthValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new StrengthPower(p, strengthValue), strengthValue));
                            }
                            if(doubleTapValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new DoubleTapPower(p, doubleTapValue), doubleTapValue));
                            }
                            this.setTakenTurn(false);
                        }
                        )
                );
                break;
            case 2: //Attack
                int damageValue = damageValues[level];
                int damageTimesValue = damageTimesValues[level];
                AbstractMonster m;
                if(getLastTargeted()==null){
                    m = AbstractDungeon.getRandomMonster();
                } else {
                    m = getLastTargeted();
                }
                StringBuilder attackDescription = new StringBuilder("Deal ");
                attackDescription.append(damageValue);
                attackDescription.append(" Damage to the last monster you targeted");
                if(damageTimesValue>1){
                    attackDescription.append(" ");
                    attackDescription.append(damageTimesValue);
                    attackDescription.append(" times");
                }
                attackDescription.append(".");
                this.moves.addMove(
                        new MinionMove(
                                "Attack"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/attack_intent_3.png")
                                , attackDescription.toString()
                                , () -> {
                            for(int i=0; i<damageTimesValue;i++) {
                                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(this, applyEnemyPowers(m, damageValue), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                            }
                            this.setTakenTurn(false);
                        }
                        )
                );
                break;
            case 3: //AOE Attack
                int aoeDamageValue = damageValues[level];
                int aoeDamageTimesValue = damageTimesValues[level];
                int[] damageArray = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
                for(int i=0; i< damageArray.length; i++){
                    damageArray[i]=applyEnemyPowers(AbstractDungeon.getCurrRoom().monsters.monsters.get(i), aoeDamageValue);
                }
                StringBuilder aoeAttackDescription = new StringBuilder("Deal ");
                aoeAttackDescription.append(aoeDamageValue);
                aoeAttackDescription.append(" Damage to all enemies");
                if(aoeDamageTimesValue>1){
                    aoeAttackDescription.append(" ");
                    aoeAttackDescription.append(aoeDamageTimesValue);
                    aoeAttackDescription.append(" times");
                }
                aoeAttackDescription.append(".");
                this.moves.addMove(
                        new MinionMove(
                                "Attack"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/attack_intent_3.png")
                                , aoeAttackDescription.toString()
                                , () -> {
                                    for(int i=0; i<aoeDamageTimesValue;i++) {
                                        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this, damageArray, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                                    }
                            this.setTakenTurn(false);
                        }
                        )
                );
                break;
            case 4: //Block
                int blockValue = blockValues[level];
                int blockTimesValue = blockTimesValues[level];
                int healValue = healValues[level];
                StringBuilder blockDescription = new StringBuilder("Block ");
                blockDescription.append(blockValue);
                if(blockTimesValue>1){
                    blockDescription.append(" ");
                    blockDescription.append(blockTimesValue);
                    blockDescription.append(" times");
                }
                blockDescription.append(".");
                if(isCharged){
                   blockDescription.append(" Heal NL ");
                   blockDescription.append(healValue);
                   blockDescription.append(".");
                }
                this.moves.addMove(
                        new MinionMove(
                                "Block"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/defend.png")
                                , blockDescription.toString()
                                , () -> {
                            for(int i=0; i<blockTimesValue;i++) {
                                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this, blockValue, false));
                            }
                            if(isCharged){
                                AbstractDungeon.actionManager.addToBottom(new HealAction(p, this, healValue));
                            }
                            this.setTakenTurn(false);
                        }
                        )
                );
                break;
            case 5: //Debuff
                int strengthDebuffValue = strengthDebuffValues[level];
                int frailValue = frailDebuffValues[level];
                int weakenValue = weakenDebuffValues[level];
                healValue = healValues[level];
                if(getLastTargeted()==null){
                    m = AbstractDungeon.getRandomMonster();
                } else {
                    m = getLastTargeted();
                }
                StringBuilder debuffDescription = new StringBuilder("Give the last monster you targeted:");
                if (strengthDebuffValue > 0 ){
                    debuffDescription.append(" NL -");
                    debuffDescription.append(strengthDebuffValue);
                    debuffDescription.append(" Strength.");
                }
                if (frailValue > 0 ){
                    debuffDescription.append(" NL ");
                    debuffDescription.append(frailValue);
                    debuffDescription.append(" Frail.");
                }
                if (weakenValue > 0 ){
                    debuffDescription.append(" NL ");
                    debuffDescription.append(weakenValue);
                    debuffDescription.append(" Weaken.");
                }
                if(isCharged){
                    debuffDescription.append(" NL Heal ");
                    debuffDescription.append(healValue);
                    debuffDescription.append(".");
                }
                this.moves.addMove(
                        new MinionMove(
                                "Debuff"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/debuff2.png")
                                , debuffDescription.toString()
                                , () -> {
                            if(strengthDebuffValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new StrengthPower(m, -strengthDebuffValue), strengthDebuffValue));
                            }
                            if(frailValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new FrailPower(m,frailValue,false), frailValue));
                            }
                            if(weakenValue>0){
                                this.addToBot(new ApplyPowerAction(p, this, new WeakPower(m, weakenValue, false), weakenValue));
                            }
                            if(isCharged){
                                AbstractDungeon.actionManager.addToBottom(new HealAction(p, this, healValue));
                            }
                            this.setTakenTurn(false);
                        }
                        )
                );
                break;
            case 6: //Heal
                healValue = healValues[level];
                this.moves.addMove(
                        new MinionMove(
                                "Heal"
                                , this
                                , TextureLoader.getTexture("aureenaResources/images/minionActions/debuff1.png")
                                , "Heal"
                                , () -> {
                            AbstractDungeon.actionManager.addToBottom(new HealAction(p, this, healValue));
                            this.setTakenTurn(false);
                        }
                        )
                );
                break;

        }
    }

    private void addMoves() {
        MOVES = new String[]{};
        DIALOG = new String[]{""};
        this.damage.add(new DamageInfo(this, 8));
        setMove(0);
    }

    public enum Intents {
        Default, Aggrevated, Passified
    }

    //Alch Stuff
    private int applyEnemyPowers(AbstractCreature target,int base){
        float tmp = (float) base;
        Iterator var3 = target.powers.iterator();

        AbstractPower p;
        while(var3.hasNext()) {
            p = (AbstractPower)var3.next();
            tmp = p.atDamageReceive((float)tmp, DamageInfo.DamageType.NORMAL);
            tmp = p.atDamageFinalReceive((float)tmp, DamageInfo.DamageType.NORMAL);
        }

        if(tmp < 0.0f) {
            tmp = 0.0f;
        }

        return MathUtils.floor(tmp);
    }

    public static boolean isAttackIntent(AbstractMonster.Intent intent) {
        return
                intent == AbstractMonster.Intent.ATTACK ||
        intent == AbstractMonster.Intent.ATTACK_BUFF ||
        intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                intent == AbstractMonster.Intent.ATTACK_DEFEND;
    }
}