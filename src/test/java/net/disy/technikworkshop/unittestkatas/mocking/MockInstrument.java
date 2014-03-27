package net.disy.technikworkshop.unittestkatas.mocking;

public class MockInstrument implements IInstrument {

  ITaskListener taskListener;
  private final boolean successful;

  public MockInstrument(boolean successful) {
    this.successful = successful;
  }

  public void execute(String task) {
    if (successful) {
      taskListener.finished(task);
    }
    else {
      taskListener.error(task);
    }
  }

  public void addListener(ITaskListener taskListener) {
    this.taskListener = taskListener;
  }

}
