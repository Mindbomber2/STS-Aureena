package aureena.console;

import aureena.characters.AureenaMinion;
import aureena.fields.MinionFields;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;


public class SpawnMinion extends ConsoleCommand {
    public SpawnMinion() {
        maxExtraTokens = 0; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; //How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = true; //if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
    }
    protected void execute(String[] tokens, int depth){
        AbstractPlayer p = AbstractDungeon.player;
        if (p instanceof AbstractPlayerWithMinions){
            AbstractPlayerWithMinions temp = (AbstractPlayerWithMinions) (p);
            AureenaMinion aureena = new AureenaMinion(-100,-100);
            temp.addMinion(aureena);
            MinionFields.targettingMinions.get(AbstractDungeon.player).add(aureena);
        }
    }
}