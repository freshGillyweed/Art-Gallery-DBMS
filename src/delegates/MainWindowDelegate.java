package delegates;

public interface MainWindowDelegate {
    public void databaseSetup();
    public void showProjectionResult();
    public void showAverageBudgetOverStatus(int threshold);
    public void showProjectSelectionInfo(String whereClause, boolean isEmpty);

    public String[] getTables();
    public String[] getArtworkTitles();
    public void update(String title, String attr, String newAttr) throws Exception;
    public String[] getAllLoyalVisitors() throws Exception;
    public void terminalTransactionsFinished();
}
