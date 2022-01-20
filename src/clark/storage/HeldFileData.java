package clark.storage;

public class HeldFileData {

    private final int processId;
    private final String fileName;
    private final String user;
    private final String processName;

    public HeldFileData(int processId, String fileName, String user, String processName) {
        this.processId = processId;
        this.fileName = fileName;
        this.user = user;
        this.processName = processName;
    }

    public int getProcessId() {
        return processId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUser() {
        return user;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public String toString() {
        return fileName + ": (" + processId + ") " + processName + " [" + user + "]";
    }
}
