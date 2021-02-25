package aureena.console;

import aureena.characters.AureenaMinion;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;


public class SetMinionMove extends ConsoleCommand {
    public SetMinionMove() {
        maxExtraTokens = 2; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 2; //How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = true; //if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
    }
    protected void execute(String[] tokens, int depth){
        AbstractPlayer p = AbstractDungeon.player;
        if (p instanceof AbstractPlayerWithMinions){
            AbstractPlayerWithMinions temp = (AbstractPlayerWithMinions) (p);
            AbstractFriendlyMonster m = temp.getMinion("aureena:Aureena");
            if(m instanceof AureenaMinion){
                AureenaMinion aureena = (AureenaMinion) (m);
                aureena.setTakenTurn(false);
                aureena.setMove(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]));
            }
        }
    }
}