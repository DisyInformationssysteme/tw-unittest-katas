package net.disy.technikworkshop.unittestkatas.mocking;

public interface ITaskDispatcher {
  String getTask();

  void finishedTask(String task);
}
