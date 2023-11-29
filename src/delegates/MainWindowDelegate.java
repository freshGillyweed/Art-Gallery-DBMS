package delegates;

public interface MainWindowDelegate {
    public void databaseSetup();
    public void showProjectionResult();
    public void showAverageBudgetOverStatus(int threshold);
    public void showProjectSelectionInfo(String whereClause, boolean isEmpty);

    public void terminalTransactionsFinished();
}
