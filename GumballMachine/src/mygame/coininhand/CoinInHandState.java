package mygame.coininhand;

/**
 *
 * @author akshaybhasme
 */
public interface CoinInHandState {
    
    public void onQuarterSelected();
    public void onDimeSelected();
    public void onNickelSelected();
    public void onNothingSelected();
    public String getCoinInHand();
    public String insertCoin();
    
}
