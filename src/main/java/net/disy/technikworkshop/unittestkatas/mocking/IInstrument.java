package net.disy.technikworkshop.unittestkatas.mocking;

public interface IInstrument {
  /**
   * This method returns immediately, the task may not be finished yet. 
   * Task updates can be gotten over the ITaskListener interface.
   * 
   * @param task The task to execute, must not be null.
   * @throws IllegalArgumentException when task is null.
   */
  void execute(String task);

  void addListener(ITaskListener taskListener);
}
